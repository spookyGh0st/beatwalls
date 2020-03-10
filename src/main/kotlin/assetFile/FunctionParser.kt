package assetFile

import beatwalls.errorExit
import structure.Define
import structure.EmptyWallStructure
import structure.WallStructure
import structure.helperClasses.*
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

internal fun String.toRotationMode(): RotationMode{
    val f = this.toBwFunction()
    return when{
        f.name.isDouble() -> StaticRotation(f.name.toDouble())
        f.name == "ease" -> EaseRotation(
            startRotation = f.args[0].toDouble(),
            endRotation = f.args[1].toDouble(),
            easing = f.args.getOrNull(2)?.toEasingOrNull() ?: Easing.Linear
        )
        f.name == "switch" -> SwitchRotation(f.args.map { it.toDouble() })
        f.name == "circle" -> CirclesRotation(f.args.getOrNull(0)?.toDoubleOrNull()?: 1.0)
        else -> throw NoSuchElementException()

    }
}

internal fun String.toEasingOrNull() =
    Easing.values().find { it.name.toLowerCase() == this.toLowerCase() }

internal fun String.toColorMode(): ColorMode {
    val f = this.toBwFunction()
    return when{
        f.name.isColor() -> SingleColor(f.name.toConstColor())
        f.name == "gradient" -> {
            val colorList = f.args.toColorList()
            Gradient(colorList[0],colorList[1])
        }
        f.name == "rainbow" -> {
            when {
                f.args.isEmpty()-> Rainbow()
                f.args.size == 1 && f.args[0].isDouble() -> Rainbow(f.args[0].toDouble())
                else -> errorExit { "wrong syntax for rainbow" }
            }
        }
        f.name == "flash" -> {
            val colorList = f.args.toColorList()
            if (colorList.isEmpty()) errorExit { "No colors submitted for flash" }
            else Flash(colorList)
        }
        (f.name == "nocolor" || f.name == "null") -> {
            NoColor
        }
        else -> errorExit { "$this is invalid, please check the documentation." }
    }
}

private fun  List<String>.toColorList(): List<Color> {
    val l = mutableListOf<Color>()
    var i=0
    while (i in this.indices){
        when {
            this[i].isColor() -> {
                l.add(this[i].toConstColor())
                i++
            }
            this[i].isInt() && this[i + 1].isInt() && this[i + 2].isInt() -> {
                l.add(Color(this[i].toInt(), this[i + 1].toInt(), this[i + 2].toInt()))
                i += 3
            }
            else -> errorExit { "$this is not a valid color list. Make sure you dont have any float values or other weird stuff. check the documentation for details" }
        }
    }
    return l
}

private fun String.isColor(): Boolean{
    val s= this.split(",")
        .mapNotNull { it.toIntOrNull() }
    return when {
        this in listOf("black","blue","cyan","dark gray","gray","green","light gray","magenta","orange","pink","red","white","yellow") -> true
        s.size == 3 && s.all { it in 0..255 } -> true
        else -> false
    }
}

internal fun String.toConstColor():Color =
    when(this){
        "black" -> Color(java.awt.Color.BLACK)
        "blue" -> Color(java.awt.Color.blue)
        "cyan" -> Color(java.awt.Color.cyan)
        "dark gray" -> Color(java.awt.Color.darkGray)
        "gray" -> Color(java.awt.Color.gray)
        "green" -> Color(java.awt.Color.green)
        "light gray" -> Color(java.awt.Color.lightGray)
        "magenta" -> Color(java.awt.Color.magenta)
        "orange" -> Color(java.awt.Color.orange)
        "pink" -> Color(java.awt.Color.pink)
        "red" -> Color(java.awt.Color.red)
        "white" -> Color(java.awt.Color.white)
        "yellow"   -> Color(java.awt.Color.yellow )
        else -> {
            val l = this.split(",").mapNotNull { it.toIntOrNull() }
            if (l.size == 3)
                Color(l[0],l[1],l[2])
            else
                throw NoSuchElementException("No color named $this")
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
        .map { val a = findStructure(it, definedStructure); if(a is WallStructure) a else EmptyWallStructure }
}
