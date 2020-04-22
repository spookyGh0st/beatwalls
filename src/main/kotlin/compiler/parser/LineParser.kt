package compiler.parser

import compiler.parser.types.*
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.Define
import structure.WallStructure
import structure.deepCopy
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

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

// Line Parser parses each line and build wallstructures from them
// it assumes everything is already lowercase
data class DataSet(
    // stores all baseStructures as KClass
    val baseStructures: HashMap<String, KClass<WallStructure>> = baseStructures(),

    // stores all user-defined Structures
    val customStructures: HashMap<String, Define> = hashMapOf<String, Define>(),

    // stores all user-defined Interfaces (and default interface)
    val interfaces: HashMap<String, Interface> = hashMapOf("default" to Interface(emptyList())),

    // stores all user-defined Constants
    val constantList: MutableSet<Constant> = mutableSetOf<Constant>(),

    // stores all user-defined Functions
    val functionList: MutableSet<Function> = mutableSetOf<Function>(),

    // stores all Property names of all WallStructures (subclasses as well)
    val wallStructurePropertyNames: List<String> =
        WallStructure::class.sealedSubclasses.flatMap { kClass -> kClass.memberProperties.map { it.name.toLowerCase() } }
)


class LineParser {

    // the final structlist of the Factory
    val structList = mutableListOf<WallStructure>()

    // this always Points to the last structure
    var lastStructure: LastStructure =LastStructure(Unit)

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
            isProperty(l) -> addProperty(lastStructure.s, l)
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

    fun isConstant(l: Line) = l.trimKey(" ")  == "const"

    fun defineConstant(l: Line){
        val value = l.s.substringAfter("const ")
        val args = (dataSet.constantList  + dataSet.functionList).toTypedArray()
        val c = Constant(value,*args)
        if(!c.syntaxStatus) throw InvalidLineExpression(
            l,
            "Constant is invalid"
        )
        dataSet.constantList.add(c)
    }

    fun isFunction(l: Line) = l.trimKey(" ") == "fun"

    fun defineFunction(l: Line){
        val value = l.s.substringAfter("fun ")
        val args = (dataSet.constantList  + dataSet.functionList).toTypedArray()
        val f = Function(value,*args)
        if(!f.syntaxStatus) throw InvalidLineExpression(
            l,
            "Function is invalid"
        )
        dataSet.functionList.add(f)
    }

    fun isDefineCustomStruct(l: Line) = l.trimKey(" ") == "struct"

    fun defineCustomStruct(l: Line){
        addLastStruct()
        val i = Define()
        val n = l.trimKey(":")?.substringAfter("struct ") ?: throw InvalidLineExpression(l)
        dataSet.customStructures[n]=i
        val baseClasses = l.trimValue(":")?.split(",")?: emptyList()
        for(s in baseClasses){
            val structure = when(s){
                in dataSet.baseStructures.keys -> dataSet.baseStructures[s]!!.createInstance()
                in dataSet.customStructures.keys -> dataSet.customStructures[s]!!
                else -> throw InvalidLineExpression(
                    l,
                    "WallStructure $s does not exist"
                )
            }
            i.structures+=structure
        }
    }


    fun defineInterface(l: Line) {
        addLastStruct()
        val i = l.parseBwInterface(dataSet.interfaces)
        dataSet.interfaces[i.first] = i.second
        lastStructure = LastStructure(i.second)
    }

    fun isProperty(l: Line)= propNameOfLine(l) in dataSet.wallStructurePropertyNames


    fun isDefaultStructure(l: Line): Boolean = isWallstructInHashmap(l,dataSet.baseStructures)

    fun addDefaultStruct(l: Line){
        addLastStruct()
        val ws = wallStructFromLine(l,dataSet) { dataSet.baseStructures[it]!!.createInstance() }
        ws.addInterfaces(dataSet.interfaces["default"]!!)
        lastStructure = ws

    }

    fun isCustomStructure(l: Line): Boolean = isWallstructInHashmap(l,dataSet.customStructures)

    fun addStoredStruct(l: Line){
        addLastStruct()
        val ws = wallStructFromLine(l,dataSet){ dataSet.customStructures[it]!!.deepCopy() }
        ws.addInterfaces(dataSet.interfaces["default"]!!)
        lastStructure = ws
    }

//  _          _                    __                  _   _
// | |__   ___| |_ __   ___ _ __   / _|_   _ _ __   ___| |_(_) ___  _ __  ___
// | '_ \ / _ \ | '_ \ / _ \ '__| | |_| | | | '_ \ / __| __| |/ _ \| '_ \/ __|
// | | | |  __/ | |_) |  __/ |    |  _| |_| | | | | (__| |_| | (_) | | | \__ \
// |_| |_|\___|_| .__/ \___|_|    |_|  \__,_|_| |_|\___|\__|_|\___/|_| |_|___/
//              |_|

    fun addLastStruct(){
        if(lastStructure.initialize) {
            if(lastStructure.s !is WallStructure) throw Exception("$lastStructure is not an Wallstructure and cannot be added, please report this error")
            structList.add(lastStructure.s as WallStructure)
            lastStructure = LastStructure(Unit)
        }
    }


    fun isWallstructInHashmap(l: Line, keys: HashMap<String, *>): Boolean {
        val b = wsBeatOfLine(l)
        val s = wsNameOfLine(l)
        return isDouble(b) && s in keys
    }
}

fun wallStructFromLine(l: Line, dataSet: DataSet, f: (String)-> WallStructure): LastStructure {
    val b = wsBeatOfLine(l)?: throw InvalidLineExpression(l,"failed to parse beat of WallStructure (syntax: 10 Line: \$interfaces)")
    val s = wsNameOfLine(l)?: throw InvalidLineExpression(l, "failed to parse name of WallStructure (syntax: 10 Line: \$interfaces")
    val bwInterfaceNames =  l.s.substringAfter(":")
    val i = bwInterfaceNames.toInterfaceList(dataSet.interfaces)
    val ws = f(s)
    ws.constantController.customConstants.addAll(dataSet.constantList)
    ws.constantController.customFunctions.addAll(dataSet.functionList)
    ws.beat=b.toDouble()
    return LastStructure(ws, i, true)
}

fun String.substringBetween(start: String = "", end: String = "") =
    this.substringAfter(start).substringBefore(end).trim()

fun wsNameOfLine(l: Line): String? {
    val struct = l.trimKey(":")
    return struct?.split(" ")?.getOrNull(1)
}

fun wsBeatOfLine(l: Line): String? {
    val struct = l.trimKey(":")
    return struct?.split(" ")?.getOrNull(0)
}

fun isDouble(charSequence: CharSequence?) = charSequence?.toString()?.toDoubleOrNull()?.run { true }?: false

val Function.syntaxStatus: Boolean get() = this.checkSyntax()

/**
 * returns a list of all base-structures
 */
fun baseStructures(): HashMap<String, KClass<WallStructure>> {
    val l = WallStructure::class.sealedSubclasses
    val hm = hashMapOf<String, KClass<WallStructure>>()
    @Suppress("UNCHECKED_CAST")
    l.forEach { hm[it.simpleName?.toLowerCase()!!] = it as KClass<WallStructure> }
    return hm
}
