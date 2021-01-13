package assetFile

import beatwalls.errorExit
import structure.wallStructures.Define
import structure.wallStructures.EmptyWallStructure
import structure.wallStructures.WallStructure
import structure.math.Point
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.lang.NumberFormatException
import kotlin.random.Random

/**
 * String to Point
 */
internal fun String.toPoint(): Point {
    val values = this.split(",")
        .map { it.trim() }
        .map { it.toDoubleOrNull() }
    return Point(
        x = values.getOrNull(0) ?: 0.0,
        y = values.getOrNull(1) ?: 0.0,
        z = values.getOrNull(2) ?: 0.0
    )
}

/**
 * String to WallStructure
 */
internal fun String.toWallStructure(definedStructure: List<Define>): WallStructure {
    val a =  findStructure(this, definedStructure)
    return if (a is WallStructure)
        a
    else {
        errorExit { "The Wallstructure $this does not exist" }
    }
}

internal fun String.toBwFunction(): BwFunction {
    val s = this.replace(" ","").toLowerCase()
    val head = s.substringBefore("(").ifEmpty { throw NullPointerException("Function head cant be empty") }
    val args = s
        .substringAfter(head)
        .removeSurrounding("(", ")")
        .replace("(","")
        .replace(")","")
        .split(",")
        .filter { it.isNotEmpty() }
    return BwFunction(head,args)
}
data class BwFunction(val name: String,val args: List<String> = emptyList())



internal fun String.toDoubleFunc(): Function<Double>? {
    val s = this.toLowerCase()
    when {
        s.toDoubleOrNull() != null -> return { this.toDouble() }
        s == "null" -> return null
        s.startsWith("random") -> {
            // gets the numbers random(12,23)
            val constrains = s
                .substringAfter("random")
                .removeSurrounding("(", ")")
                .split(",")
                .mapNotNull { it.toDoubleOrNull() }
            when {
                constrains.isEmpty() -> return { Random.nextDouble() }
                constrains.size == 1 -> {
                    if (constrains[0] > 0.0)
                        return { RandomSeed.nextDouble(constrains[0]) }
                    errorExit { "Failed to parse the random values fo $s syntax is random(min, max), random(max) or random()" }
                }
                constrains.size == 2 -> {
                    if (constrains[1] > constrains[0] && constrains[0] != constrains[1])
                        return { RandomSeed.nextDouble(constrains[0], constrains[1]) }
                    if (constrains[0] > constrains[1] && constrains[0] != constrains[1])
                        return { RandomSeed.nextDouble(constrains[1], constrains[0]) }
                    errorExit { "Failed to parse the random values fo $s syntax is random(min, max), random(max) or random()" }
                }
                else -> {
                    errorExit { "Failed to parse the random values fo $s syntax is random(min,max,seed), random(min, max), random(max) or random()" }
                }
            }
        }
        else -> {
            errorExit { "Failed to parse the value $s" }
        }
    }
}




private fun String.isDouble() =
    try { parseDouble(this); true
} catch (e: NumberFormatException){ false }
private fun String.isInt() =
    try { parseInt(this); true
    } catch (e: NumberFormatException){ false }

internal fun String.toWallStructureList(definedStructure: List<Define>): List<WallStructure>{
    return this
        .split(",")
        .map { it.trim() }
        .map { val a = findStructure(it, definedStructure); if(a is WallStructure) a else EmptyWallStructure() }
}
