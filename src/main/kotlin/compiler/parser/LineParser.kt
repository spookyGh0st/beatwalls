package compiler.parser

import compiler.parser.types.*
import compiler.property.BwProperty
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.Define
import structure.WallStructure

/*
EXAMPLE SYNTAX
10 wallstruct
 height=10
 height+=10
 height -= 10
struct myStruct: wallstruct1, wallstruct2
  height +=10
const a = 10
fun f(x) = 2*10
10 mystruct
    height=f(a)
interface myInterface: interface1, inteface2
    height = 10
20 wallstruct: myInterface
 */


class LineParser {

    // the final structlist of the Factory
    val structList = mutableListOf<WallStructure>()

    // this always Points to the last structure
    var lastStructure: OperationsHolder = WsFactory(TODO())

    val dataSet = DataSet()


//        _      _
//  _ __ (_) ___| | _____ _ __
// | '_ \| |/ __| |/ / _ \ '__|
// | |_) | | (__|   <  __/ |
// | .__/|_|\___|_|\_\___|_|
// |_|

    fun parseLines(l: List<Line>){
        l.forEach { parseLine(it) }
        addLastStruct()
    }

    fun parseLine(l: Line){
        when{
            isConstant(l) -> defineConstant(l)
            isFunction(l) -> defineFunction(l)
            isDefineCustomStruct(l) -> defineCustomStruct(l)
            isBwInterface(l) -> defineInterface(l)
            isProperty(l) -> parseProperty(l)
            isDefaultStructure(l) -> addDefaultStruct(l)
            isCustomStructure(l) -> addStoredStruct(l)
            else -> throw InvalidLineExpression(l)
        }
    }

//                  _          __                  _   _
//  _ __ ___   __ _(_)_ __    / _|_   _ _ __   ___| |_(_) ___  _ __  ___
// | '_ ` _ \ / _` | | '_ \  | |_| | | | '_ \ / __| __| |/ _ \| '_ \/ __|
// | | | | | | (_| | | | | | |  _| |_| | | | | (__| |_| | (_) | | | \__ \
// |_| |_| |_|\__,_|_|_| |_| |_|  \__,_|_| |_|\___|\__|_|\___/|_| |_|___/

    fun parseProperty(l:Line){
        val a = Assign(l.s)
        val ws = TODO() //lastStructure.s
        when (ws) {
            is WallStructure -> {
                val propFunction: (WallStructure) -> BwProperty = dataSet.wsProperties[a.name]!!
                val prop = propFunction(ws)
                a.parseProperty(prop)
            }
            //is BwInterface -> ws.propertySetters.add(a.parseProperty)
            else -> throw Exception("tried to set Property to $ws, please report this error")
        }
    }

    fun isConstant(l: Line) = l.sBefore(" ")  == "const"

    fun defineConstant(l: Line){
        val value = l.sAfter("const")
        val name = l.sBetween("const","(")
        val args = (dataSet.constantList.values  + dataSet.functionList.values).toTypedArray()
        val c = Constant(value,*args)
        if(!c.syntaxStatus || dataSet.inKeys(name)) throw InvalidLineExpression(
            l,
            "Constant is invalid"
        )
        dataSet.constantList[name] = c
    }

    fun isFunction(l: Line) = l.sBefore(" ") == "fun"

    fun defineFunction(l: Line){
        val value = l.sAfter("fun")
        val name = l.sBetween("fun","(")
        val args = (dataSet.constantList.values  + dataSet.functionList.values).toTypedArray()
        val f = Function(value,*args)
        if(!f.syntaxStatus || dataSet.inKeys(name)) throw InvalidLineExpression(
            l,
            "Function is invalid"
        )
        dataSet.functionList[name] = f
    }

    fun isDefineCustomStruct(l: Line) = l.sBefore(" ") == "struct"

    fun defineCustomStruct(l: Line){
        addLastStruct()
        val n = l.sBefore(":")?.substringAfter("struct ") ?: throw InvalidLineExpression(l)
        val b = l.sAfter(":")?.split(",")?: emptyList()
        TODO()
        //dataSet.customStructures[n]= customStructBuilder(n,b)
    }

    fun customStructBuilder(name: String,baseStructs: List<String>): Define {
            val i = Define()
            for (s in baseStructs) {
                val structure = when (s) {
                    in dataSet.wsFactories.keys -> dataSet.wsFactories[s]!!.create()
                    in dataSet.customStructures.keys -> dataSet.customStructures[s]!!.invoke()
                    else -> throw Exception("WallStructure $name does not exist in $baseStructs")
                }
                i.structures += structure
            }
            return i
    }

    fun defineInterface(l: Line) {
        addLastStruct()
        val i = l.parseBwInterface(dataSet.interfaces)
        dataSet.interfaces[i.first] = i.second
        TODO()
       // lastStructure = LastStructure(i.second)
    }

    fun isProperty(l: Line): Boolean= TODO() //propNameOfLine(l) in dataSet.wallStructurePropertyNames


    fun isDefaultStructure(l: Line): Boolean = isWallstructInHashmap(l,dataSet.wsFactories)

    fun addDefaultStruct(l: Line){
        addLastStruct()
        TODO()
        //val ws = wallStructFromLine(l,dataSet) { dataSet.baseStructures[it]!!.createInstance() }
        //ws.applyInterfaces(dataSet.interfaces["default"]!!)
        //lastStructure = ws

    }

    fun isCustomStructure(l: Line): Boolean = isWallstructInHashmap(l,dataSet.customStructures)

    fun addStoredStruct(l: Line){
        addLastStruct()
        val ws = wallStructFromLine(l,dataSet){ dataSet.customStructures[it]!!.invoke() }
        TODO()
        //ws.applyInterfaces(dataSet.interfaces["default"]!!)
        lastStructure = ws
    }


//  _          _                    __                  _   _
// | |__   ___| |_ __   ___ _ __   / _|_   _ _ __   ___| |_(_) ___  _ __  ___
// | '_ \ / _ \ | '_ \ / _ \ '__| | |_| | | | '_ \ / __| __| |/ _ \| '_ \/ __|
// | | | |  __/ | |_) |  __/ |    |  _| |_| | | | | (__| |_| | (_) | | | \__ \
// |_| |_|\___|_| .__/ \___|_|    |_|  \__,_|_| |_|\___|\__|_|\___/|_| |_|___/
//              |_|

    fun addLastStruct(){
        //      if(lastStructure.initialize) {
        //          if(lastStructure.s !is WallStructure) throw Exception("$lastStructure is not an Wallstructure and cannot be added, please report this error")
        //          structList.add(lastStructure.s as WallStructure)
        //          TODO()

        //          //lastStructure = LastStructure(Unit)
        //      }
    }


    fun isWallstructInHashmap(l: Line, keys: HashMap<String, *>): Boolean {
        val b = wsBeatOfLine(l)
        val s = wsNameOfLine(l)
        return isDouble(b) && s in keys
    }
}

fun wallStructFromLine(l: Line, dataSet: DataSet, f: (String)-> WallStructure): WsFactory {
    val b = wsBeatOfLine(l)?: throw InvalidLineExpression(l,"failed to parse beat of WallStructure (syntax: 10 Line: \$interfaces)")
    val s = wsNameOfLine(l)?: throw InvalidLineExpression(l, "failed to parse name of WallStructure (syntax: 10 Line: \$interfaces")
    val bwInterfaceNames =  l.s.substringAfter(":","")
    val il = bwInterfaceNames.toInterfaceList(dataSet.interfaces) + dataSet
    val ws = f(s)
    ws.constantController.customConstants.addAll(dataSet.constantList.values)
    ws.constantController.customFunctions.addAll(dataSet.functionList.values)
    ws.beat=b.toDouble()

    TODO()
//    for(i in il + additionalBwInterfaces.toList())
//        for(p in r.propertySetters){
//            when (s) {
//                is WallStructure ->
//                is BwInterface -> ws.lines.add(l)
//                else -> throw InvalidLineExpression(l)
//            }
//        }
//
//    return WallStructurews, i, true)
}


fun wsNameOfLine(l: Line): String? {
    val struct = l.sBefore(":")
    return struct?.split(" ")?.getOrNull(1)
}

fun wsBeatOfLine(l: Line): String? {
    val struct = l.sBefore(":")
    return struct?.split(" ")?.getOrNull(0)
}

fun isDouble(charSequence: CharSequence?) = charSequence?.toString()?.toDoubleOrNull()?.run { true }?: false

val Function.syntaxStatus: Boolean get() = this.checkSyntax()

