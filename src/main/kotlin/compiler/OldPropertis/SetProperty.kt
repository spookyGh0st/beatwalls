package compiler.OldPropertis

import assetFile.toColorMode
import assetFile.toDoubleFunc
import assetFile.toPoint
import assetFile.toRotationMode
import beatwalls.errorExit
import compiler.Compiler
import structure.WallStructure
import kotlin.reflect.*
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability


internal fun Compiler.setProperty(item: WallStructure, property: KProperty1<out WallStructure, Any?>?, value: Any?) {
    if (property is KMutableProperty<*>) {
        return property.setter.call(item, value)
    }
    throw(Exception())
}

internal fun <E> readProperty(item: WallStructure, property: KProperty1<out WallStructure, Any?>?): Any? {
    if (property is KMutableProperty<*>) {
        return property.getter.call(item)
    }
    throw(Exception())
}

fun Compiler.getWallListType(): KType {
    val l = WallStructure::class.createType()
    val k = KTypeProjection(type = l, variance = KVariance.INVARIANT)
    return List::class.createType(listOf(k))
}

fun Compiler.fillProperty(
    wallStructure: WallStructure,
    key: String,
    value: String
) {

    val properties = lastStructure::class.memberProperties.toMutableList()
    val property = properties.find { it.name.toLowerCase() == key } ?: throw NoSuchElementException()

    val type = property.returnType.withNullability(false).toString()


    val valueType: Any?
    valueType = when (type) {
        "kotlin.Boolean" -> value.toBoolean()
        "kotlin.Int" -> value.toIntOrNull()
        "kotlin.Double" -> value.toDoubleOrNull()
        "() -> kotlin.Double" -> value.toDoubleFunc()
        "kotlin.String" -> value
        "structure.helperClasses.Point" -> value.toPoint()
        "structure.helperClasses.ColorMode" -> value.toColorMode()
        "structure.helperClasses.RotationMode" -> value.toRotationMode()
        "structure.WallStructure" -> stringToWallStructure(value)
        "kotlin.collections.List<structure.WallStructure>" -> stringToWallStructureList(value)
        else -> null
    }

    try {
        setProperty(wallStructure, property, valueType)
    } catch (e: Exception) {
        errorExit(e) { "Failed to parse value $value of property ${property.name} with type $type of wallstructure $wallStructure at beat ${lastStructure.beat} . Perhaps in your syntax you have some typos?" }
    }

}