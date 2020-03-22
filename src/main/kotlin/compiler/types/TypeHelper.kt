package compiler.types

import compiler.Compiler
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import org.mariuszgromada.math.mxparser.PrimitiveElement
import structure.WallStructure
import java.lang.NumberFormatException
import kotlin.reflect.full.memberProperties

fun Compiler.createType(key: String, value: String) {
    val types = TypeEnum::class.sealedSubclasses.mapNotNull { it.objectInstance }
    types.forEach {
        it.createTypeFor(this, key, value)
    }
}

val wallStructureNames =
    WallStructure::class.sealedSubclasses.map { it.simpleName?.toLowerCase() ?: "" }

val wallStructurePropertyNames =
    WallStructure::class.sealedSubclasses.flatMap { kClass -> kClass.memberProperties.map { it.name.toLowerCase() } }

val protectedStrings =
    listOf("val", "fun", "interface") + wallStructureNames + wallStructurePropertyNames

internal fun Compiler.isValidWallStructure(s: String) =
    (storedStructures[s] != null || defaultStructures[s] != null)

internal fun Compiler.isInterface(s: String) =
    (storedInterfaces[s] != null)

internal fun isDouble(s: String) =
    try {
        (s.toDouble()); true
    } catch (e: NumberFormatException) {
        false
    }

internal fun String.isInt() =
    try {
        (this.toInt()); true
    } catch (e: NumberFormatException) {
        false
    }

fun Compiler.addArg(element: PrimitiveElement) {
    args[element.hashCode()] = element
    when (element) {
        is Constant -> valList[element.constantName] = element.hashCode()
        is Function -> funList[element.functionName] = element.hashCode()
    }
}
