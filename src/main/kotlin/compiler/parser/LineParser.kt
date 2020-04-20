package compiler.parser

import compiler.property.BwProperty
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.Define
import structure.WallStructure
import structure.deepCopy
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

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
class LineParser {

    // the final structlist of the Factory
    val structList = mutableListOf<WallStructure>()

    // this always Points to the last structure
    var lastStructure: LastStructure =LastStructure(Unit)

    // stores all baseStructures as KClass
    val baseStructures = baseStructures()
    // stores all user-defined Structures
    val customStructures = hashMapOf<String, Define>()
    // stores all user-defined Interfaces (and default interface)
    val interfaces = hashMapOf("default" to Interface(emptyList()))

    // stores all user-defined Constants
    val constantList = mutableSetOf<Constant>()
    // stores all user-defined Functions
    val functionList = mutableSetOf<Function>()

    // stores all Property names of all WallStructures (subclasses as well)
    val wallStructurePropertyNames =
        WallStructure::class.sealedSubclasses.flatMap { kClass -> kClass.memberProperties.map { it.name.toLowerCase() } }

//        _      _
//  _ __ (_) ___| | _____ _ __
// | '_ \| |/ __| |/ / _ \ '__|
// | |_) | | (__|   <  __/ |
// | .__/|_|\___|_|\_\___|_|
// |_|

    fun parseLine(l: Line){
        when{
            isConstant(l) -> defineConstant(l)
            isFunction(l) -> defineFunction(l)
            isDefineCustomStruct(l) -> defineCustomStruct(l)
            isInterface(l) -> defineInterface(l)
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
        val value = l.trimValue(" ")
        val args = (constantList  + functionList).toTypedArray()
        val c = Constant(value,*args)
        if(!c.syntaxStatus) throw InvalidLineExpression(
            l,
            "Constant is invalid"
        )
        constantList.add(c)
    }

    fun isFunction(l: Line) = l.trimKey(" ") == "fun"

    fun defineFunction(l: Line){
        val value = l.trimValue(" ")
        val args = (constantList  + functionList).toTypedArray()
        val f = Function(value,*args)
        if(!f.syntaxStatus) throw InvalidLineExpression(
            l,
            "Function is invalid"
        )
        functionList.add(f)
    }

    fun isDefineCustomStruct(l: Line) = l.trimKey(" ") == "struct"

    fun defineCustomStruct(l: Line){
        addLastStruct()
        val i = Define()
        val n = l.trimKey(":").substringAfter("struct ")
        customStructures[n]=i
        val baseClasses = l.trimValue(":").split(",")
        for(s in baseClasses){
            val structure = when(s){
                in baseStructures.keys -> baseStructures[s]!!.createInstance()
                in customStructures.keys -> customStructures[s]!!
                else -> throw InvalidLineExpression(
                    l,
                    "WallStructure $s does not exist"
                )
            }
            i.structures+=structure
        }
    }

    fun isInterface(l: Line)= l.s.startsWith("interface ")

    fun defineInterface(l: Line) {
        addLastStruct()
        val name = l.trimKey(":").substringAfter("interface ")
        val interfaces = interfacesOfLine(l)
        val i = Interface(interfaces)
        this.interfaces[name] = i
        lastStructure = LastStructure(i)
    }

    fun isProperty(l: Line)= propNameOfLine(l) in wallStructurePropertyNames

    fun addPropertytoWs(ws: WallStructure, l: Line){
        val props = bwPropertiesOfElement(ws)
        val name = propNameOfLine(l)
        val assign = assignOfLine(l)
        val value = propValueOfLine(l)
        val prop = props[name]
        when {
            ws is Define && prop== null -> addProperty(ws.structures.first(),l)
            prop == null -> throw  InvalidLineExpression(l)
            else -> assignProperty(prop, assign, value)
        }
    }

    fun isDefaultStructure(l: Line): Boolean = isWallstructInHashmap(l,baseStructures)

    fun addDefaultStruct(l: Line){
        addWallStructFromLine(l) { baseStructures[it]!!.createInstance() }
    }

    fun isCustomStructure(l: Line): Boolean = isWallstructInHashmap(l,customStructures)

    fun addStoredStruct(l: Line){
        addWallStructFromLine(l){ customStructures[it]!!.deepCopy() }
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
            for(i in lastStructure.interfaces + (interfaces["default"]?: throw Exception("default interface does not exist, please report this error"))){
                for(l in i.lines){
                    addProperty(lastStructure.s,l)
                }
            }
            structList.add(lastStructure.s as WallStructure)
            lastStructure = LastStructure(Unit)
        }
    }

    fun isWallstructInHashmap(l: Line, keys: HashMap<String, *>): Boolean {
        val b = wsBeatOfLine(l)
        val s = wsNameOfLine(l)
        return isDouble(b) && s in keys
    }

    fun wsNameOfLine(l: Line): String? {
        val struct = l.trimKey(":")
        return struct.split(" ").getOrNull(1)
    }

    fun wsBeatOfLine(l: Line): String? {
        val struct = l.trimKey(":")
        return struct.split(" ").getOrNull(0)
    }

    fun interfacesOfLine(l: Line): List<Interface> {
        val interfaceNames =l.trimValue(":").split(",").map { it.trim() }
        return interfaceNames.map { interfaces.getOrElse(it) {throw InvalidLineExpression(
            l,
            "Interface $it does not exist"
        )
        } }

    }

    fun propNameOfLine(l: Line)= l.trimKey(*Assigns.values().map { "${it.identifier}=" }.toTypedArray())

    fun propValueOfLine(l: Line)= l.trimValue(*Assigns.values().map { "${it.identifier}=" }.toTypedArray())

    fun assignOfLine(l: Line): Assigns {
        val name = propNameOfLine(l)
        val identifierString =  l.trimKey("=").substringAfter(name)
        return try {
            Assigns.valueOf(identifierString) } catch (e:Exception) { throw InvalidLineExpression(
            l,
            "failed to parse Assignment of property"
        )
        }
    }

    fun assignProperty(prop: BwProperty, assign: Assigns, value: String) = when(assign){
            Assigns.Equals -> prop.setExpr(value)
            Assigns.PlusAssign -> prop.plusExpr(value)
            Assigns.TimesAssign -> prop.timesExpr(value)
            Assigns.PowAssign -> prop.powExpr(value)
        }

    fun addProperty(ws: Any, l: Line) {
        when (ws) {
            is WallStructure -> {
                addPropertytoWs(ws,l)
            }
            is Interface -> {
                ws.lines.add(l)
            }
            else -> throw InvalidLineExpression(l)
        }
    }

    fun addWallStructFromLine(l: Line, f: (String)-> WallStructure){
        addLastStruct()
        val b = wsBeatOfLine(l)?: throw InvalidLineExpression(
            l,
            "failed to parse beat of WallStructure"
        )
        val s = wsNameOfLine(l)?: throw InvalidLineExpression(
            l,
            "failed to parse line of WallStructure"
        )
        val i =  interfacesOfLine(l)
        val ws = f(s)
        ws.beat=b.toDouble()
        lastStructure = LastStructure(ws, i, true)
    }

    fun isDouble(charSequence: CharSequence?) = charSequence?.toString()?.toDoubleOrNull()?.run { true }?: false

    inline fun <reified E : Any> bwPropertiesOfElement(element: E): Map<String, BwProperty> {
        val s = memberPropertiesOfElement(element)
        @Suppress("UNCHECKED_CAST") // we can ignore this, since we check for it.
        return s.filterValues { it is BwProperty } as Map<String, BwProperty>
    }

    inline fun <reified E : Any> memberPropertiesOfElement(element: E): Map<String, Any?> {
        val m = E::class.memberProperties
        m.forEach { it.isAccessible = true }
        return m.associate<KProperty1<E, *>, String, Any?> { it.name to it.getDelegate(element) }
    }

    fun baseStructures(): HashMap<String, KClass<WallStructure>> {
        val l = WallStructure::class.sealedSubclasses
        val hm = hashMapOf<String, KClass<WallStructure>>()
        @Suppress("UNCHECKED_CAST")
        l.forEach { hm[it.simpleName?.toLowerCase()!!] = it as KClass<WallStructure> }
        return hm
    }

    val Function.syntaxStatus: Boolean get() = this.checkSyntax()
}
