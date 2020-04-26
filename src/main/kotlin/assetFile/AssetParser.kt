package assetFile

import beatwalls.errorExit
import mu.KotlinLogging
import structure.*
import kotlin.random.Random
import kotlin.reflect.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability

//   ____  ____   __   ____  ____  ____
//  (  _ \(  __) / _\ (    \(  __)(  _ \
//   )   / ) _) /    \ ) D ( ) _)  )   /
//  (__\_)(____)\_/\_/(____/(____)(__\_)
//
// I basically wrote a new script language.
// Below is what needed to go automatically set all the values for all the parameters.

private val logger = KotlinLogging.logger {}

//global variables
var RandomSeed = Random(Random.nextInt())

/**
 * reads in a String and returns the corresponding SongAssetFile
 */
fun parseAsset(s: String): ArrayList<WallStructure> {
    // creates the list of key-Value
    val lines = parseAssetString(s)
    val structures = parseStructures(lines)
    logger.info { "Read DifficultyAsset Succesfull with ${structures.size} structures" }
    return structures
}

/**
 * reads the Structures from the list
 */
fun parseStructures(mutableList: MutableList<Pair<String, String>>): ArrayList<WallStructure>{
    val list = arrayListOf<WallStructure>()

    var lastStruct: Any = EmptyWallStructure()
    val definedStructures = mutableListOf<Define>()

    for (i in 0 until mutableList.size) {
        val key = mutableList[i].key().toLowerCase()
        val value = mutableList[i].value()
        //hacky
        when {
            key == "define" -> {
                val struct = Define()
                struct.name = value.toLowerCase()
                definedStructures.add(struct)
                logger.info { "defined Structure ${struct.name}" }
                lastStruct = struct
            }
            key == "randomseed" -> {
                val v = value.toIntOrNull()
                if (v != null)
                    RandomSeed = Random(v)
            }
            key.toDoubleOrNull() != null -> {
                if (value.toLowerCase() == "define")
                    errorExit { "Old Defined Structure detected. New one is define: \$name" }
                val structName = mutableList[i].value().toLowerCase()
                val beat = mutableList[i].key().toDouble()
                val struct: Any = findStructure(structName, definedStructures)

                //hacky
                if (struct is Define) {
                    struct.isTopLevel = true
                }

                if (struct is WallStructure) {
                    struct.beat = beat
                    list.add(struct)
                }

                logger.info { "adding $structName" }
                lastStruct = struct
            }
            else -> {
                readWallStructOptions(lastStruct, mutableList[i], definedStructures)
            }
        }
    }
    val l =list.filterNot { it is EmptyWallStructure }
    return ArrayList(l)
}

fun findStructure(name: String, definedStructure: List<Define>): Any {
    val structName = name.toLowerCase()
    val specialStructs = WallStructure::class.sealedSubclasses
    val specialStructsNames = specialStructs.map { it.simpleName?.toLowerCase()?:""}

    val definedStructureNames = definedStructure.map { it.name.toLowerCase() }
    val struct: Any

    // sets the struct
    struct = when (structName) {
        in definedStructureNames -> findDefinedStruct(structName,definedStructure)
        in specialStructsNames -> findSpecialStruct(structName,specialStructs)
        "default" -> WallStructure.Default
        else -> {
            errorExit { "structure $structName not found" }
        }
    }
    return struct
}

fun findDefinedStruct(structName: String, definedStructure: List<Define>): Define {
    val found = definedStructure.findLast { it.name.toLowerCase() == structName }!!.deepCopy()
    val def = Define()
    def.structures = listOf(found)
    return def
}

fun findSpecialStruct(structName: String,specialStructs: List<KClass<out WallStructure>>): WallStructure {
    return specialStructs.find { it.simpleName!!.toLowerCase() == structName }!!.createInstance()
}

/**
 * fills the given structure with the option, if it exist
 */
fun readWallStructOptions(lastObject: Any, option: Pair<String, String>, definedStructure: List<Define>){
    if (lastObject is EmptyWallStructure){
        return
    }

    val property = propOfName(lastObject,option.key())

    if (property != null) {
        fillProperty(
            lastObject = lastObject,
            property = property,
            value = option.value(),
            definedStructure = definedStructure
        )
    }else{
        if( lastObject is Define) {
            if(lastObject.structures.firstOrNull() != null){
                readWallStructOptions(lastObject.structures.first(), option, definedStructure)
            }
        }else{
            errorExit { "The WallStructure $lastObject does not have the property ${option.key()}" }
        }
    }
}
fun propOfName(lastObject: Any, key:String): KProperty1<out Any, Any?>? {
    val properties = lastObject::class.memberProperties.toMutableList()
    return  properties.find { it.name.toLowerCase() ==key.toLowerCase() }

}

fun fillProperty(
    property: KProperty1<out Any, Any?>,
    value: String,
    definedStructure: List<Define>,
    lastObject: Any
){
    val valueType:Any?
    val type = property.returnType.withNullability(false).toString()


    valueType = when (type) {
        "kotlin.Boolean" -> value.toBoolean()
        "kotlin.Int"-> value.toIntOrNull()
        "kotlin.Double"-> value.toDoubleOrNull()
        "() -> kotlin.Double" -> value.toDoubleFunc()
        "kotlin.String" -> value
        "structure.helperClasses.Point" -> value.toPoint()
        "structure.helperClasses.ColorMode" -> value.toColorMode()
        "structure.helperClasses.RotationMode" -> value.toRotationMode()
        "structure.WallStructure" -> value.toWallStructure(definedStructure)
        "kotlin.collections.List<structure.WallStructure>" -> value.toWallStructureList(definedStructure)
        else -> null
    }

    try {
        lastObject.writeProperty(property, valueType)
    } catch (e: Exception) {
        val beat = if (lastObject is WallStructure) lastObject.beat.toString() else "COULDNOTFIND"
        errorExit(e) { "Failed to parse value $value of property ${property.name} with type $type of wallstructure $lastObject at beat $beat . Perhaps in your syntax you have some typos?" }
    }

}

fun<E> E.writeProperty(property: KProperty1<out E, Any?>?, value : Any?){
    if (property is KMutableProperty<*>){
        return property.setter.call(this,value)
    }
    throw(Exception())
}

fun<E> E.readProperty(property: KProperty1<out E, Any?>?): Any? {
    if (property is KMutableProperty<*>){
        return property.getter.call(this)
    }
    throw(Exception())
}

fun getWallListType(): KType {
    val l = WallStructure::class.createType()
    val k = KTypeProjection(type = l, variance = KVariance.INVARIANT)
    return List::class.createType(listOf(k))
}

/**
 * creates the list of key-value Pairs
 */
fun parseAssetString(s:String): MutableList<Pair<String, String>> =
    s
        .lines()
        .asSequence()
        .map { it.trim() }
        .filterNot { it.startsWith("#") || it.isEmpty() }
        .map { it.replace("\\t".toRegex(), "") }
        .map { it.replaceAfter("#", "") }
        .map { it.replace("#", "") }
        .map { it.split(":") }
        .map { it.map { l -> l.trim() } }
        .map { it[0].toLowerCase() to it.minus(it[0]).joinToString(":") }
        .filterNot{ it.first.isEmpty() || it.second.isEmpty()}
        .toMutableList()

/**
 * different name for componen1/component2
 */
fun Pair<String,String>.value(): String {
    return this.component2()
}
fun Pair<String,String>.key(): String {
    return this.component1()
}

