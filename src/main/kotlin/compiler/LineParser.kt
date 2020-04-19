package compiler

import compiler.property.BwProperty
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.*
import kotlin.Exception
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.*
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
10: wallstruct
    height=f(a)
interface myInterface: interface1, inteface2
    height = 10
20 wallstruct: myInterface
 */
class WallStructFactory {

    lateinit var lastStructure: Any

    val structList = mutableListOf<WallStructure>()

    val defaultStructures = getDefaultStructures()
    val storedStructures = hashMapOf<String, Define>()
    val storedInterfaces = hashMapOf<String, Interface>()

    val contList = hashMapOf<String, Constant>()
    val funList = hashMapOf<String, Function>()

    val wallStructurePropertyNames =
        WallStructure::class.sealedSubclasses.flatMap { kClass -> kClass.memberProperties.map { it.name.toLowerCase() } }

    fun parseLine(l:Line){
        when{
            isFunction(l) -> defineFunction(l)
            isConstant(l) -> defineConstant(l)
            isInterface(l) -> defineInterface(l)
            l.key in wallStructurePropertyNames -> addProperty(lastStructure, l)
            l.key.isDouble() && l.value in defaultStructures.keys -> addDefaultStruct(l)
            l.key.isDouble() && l.value in storedStructures.keys -> addStoredStruct(l)
            l.value.split(",").all { it in storedStructures.keys + defaultStructures.keys } -> defineStoredStruct(l)
            else -> throw Exception("invalid key value Pair")
        }
    }
    fun addLastStruct(){
        if(lastStructure is WallStructure) structList.add(lastStructure as WallStructure)
    }

    fun isFunction(l: Line) = l.key.startsWith("fun ")

    fun defineFunction(l: Line){
        val name = l.key.substringAfter("fun ")
        funList[name] = Function(l.value)
    }

    fun isConstant(l: Line) = l.key.startsWith("const ")

    fun defineConstant(l: Line){
        val name = l.key.substringAfter("const ")
        contList[name] = Constant(l.value)
    }

    fun isInterface(l:Line)= l.key.startsWith("interface ")

    fun defineInterface(l: Line) {
        addLastStruct()
        val name = l.key.substringAfter("interface ")
        val interfaceNames = l.value.split(",").map { it.trim().toLowerCase() }
        val interfaces = interfaceNames.mapNotNull { storedInterfaces[it] }
        val i = Interface(name,interfaces)
        storedInterfaces[name] = i
        lastStructure =  i
    }

    fun defineStoredStruct(l: Line){
        addLastStruct()
        val i = Define()
        storedStructures[l.key]=i
        val baseClasses = l.value.split(",")
        for(s in baseClasses){
            val structure = when(s){
                in defaultStructures.keys -> defaultStructures[s]!!.createInstance().also { extendInterface(it,it::class.simpleName!!) }
                in storedStructures.keys -> storedStructures[s]!!
                else -> throw NoSuchElementException("$l")
            }
            i.structures+=structure
        }
    }

    fun addProperty(ws: WallStructure, l: Line) {
        val props = bwPropertiesOfElement(ws)
        if (ws is Define && props[l.key] == null){
            addProperty(ws.structures.first(),l)
        }else{
            //todo refactor
            //props[l.key]?.initialize(l.value,valList.values.toList(),funList.values.toList())?:
            //throw Exception("property ${l.key} does not exist")
        }
    }

    fun addDefaultStruct(l: Line){
        lastStructure = defaultStructures[l.value]!!.createInstance()
        extendInterface(lastStructure, lastStructure::class.simpleName!!)
    }

    fun addStoredStruct(l: Line){
        structList.add(lastStructure)
        lastStructure = storedStructures[l.value]!!
    }

    fun extendInterface(ws: WallStructure, interfaceList: String){
        val interfacesString = interfaceList.split(",")
        val interfaces = interfacesString.map { storedInterfaces[it]!! }
        for(i in interfaces){
            val interfaceProps = bwPropertiesOfElement(i)
            val wsProps = bwPropertiesOfElement(ws)

            for (s in interfaceProps){
                val changedProperty = wsProps[s.key] ?: throw Exception(interfaceList)
                //changedProperty.expressionString=s.value.expressionString
                //todo better way for interfaces (just store strings of stuff
            }
        }
    }


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
}

data class Interface(val name: String, val superInterfaces: List<Interface>) {
    val hm : HashMap<String,String> = hashMapOf()
}


private fun String.isDouble() = this.toDoubleOrNull()?.run { true }?: false

fun getDefaultStructures(): HashMap<String, KClass<WallStructure>> {
    val l = WallStructure::class.sealedSubclasses
    val hm = hashMapOf<String, KClass<WallStructure>>()
    l.forEach { hm[it.simpleName?.toLowerCase()!!] = it as KClass<WallStructure>}
    return hm
}

