package types

import beatwalls.logError
import structure.StructureState
import kotlin.math.PI
import kotlin.math.sin

data class BwColor(val r: BwDouble, val g: BwDouble, val b: BwDouble, val a: BwDouble)

fun bwColor(r:Number = 0.0, g: Number = 0.0, b: Number = 0.0, a: Number = 1.0) =
    BwColor({r.toDouble()}, {g.toDouble()}, {b.toDouble()}, {a.toDouble()})
fun bwColor(color: java.awt.Color) =
    bwColor(color.red/255.0, color.green /255.0, color.blue/255,1.0 )


class GradientBuilder(val ss: StructureState, val colors: List<BwColor>){
    fun build(): BwColor? {
        if (colors.size < 2){
            logError("Gradient can only use 2 or more Colors")
            return null
        }
        val r = { gradientColor{ it.r() } }
        val g = { gradientColor{ it.g() } }
        val b = { gradientColor{ it.b() } }
        val a = { gradientColor{ it.a() } }
        return BwColor(r, g, b, a)
    }
    fun gradientColor(p: (BwColor) -> Double): Double{
        val index = (ss.progress * colors.size).toInt()
        val progress = ss.progress
        val start = colors[index]
        val end = colors.getOrElse(index+1){colors.last()}
        val change = p(end) - p(start)
        return p(start) + change * progress
    }
}


internal fun rainbow(ss: StructureState, repetitions: Double, alpha: BwDouble): BwColor {
    val r = { sin(ss.progress*2* PI * repetitions + 0.0/3.0 * PI) /2 + 0.5 }
    val g = { sin(ss.progress*2* PI * repetitions + 2.0/3.0 * PI) /2 + 0.5 }
    val b = { sin(ss.progress*2* PI * repetitions + 4.0/3.0 * PI) /2 + 0.5 }
    return BwColor(r,g,b,alpha)
}

internal fun random(colors: List<BwColor>): BwColor {
    // TODO FIND A DECENT WAY FOR THIS
    val r = { colors.random().r() }
    val g = { colors.random().a() }
    val b = { colors.random().b() }
    val a = { colors.random().a() }
    return BwColor(r,g,b,a)
}

internal val black      = bwColor(java.awt.Color.BLACK)
internal val blue       = bwColor(java.awt.Color.blue)
internal val cyan       = bwColor(java.awt.Color.cyan)
internal val darkgray   = bwColor(java.awt.Color.darkGray)
internal val gray       = bwColor(java.awt.Color.gray)
internal val green      = bwColor(java.awt.Color.green)
internal val lightgray  = bwColor(java.awt.Color.lightGray)
internal val magenta    = bwColor(java.awt.Color.magenta)
internal val orange     = bwColor(java.awt.Color.orange)
internal val pink       = bwColor(java.awt.Color.pink)
internal val red        = bwColor(java.awt.Color.red)
internal val white      = bwColor(java.awt.Color.white)
internal val yellow     = bwColor(java.awt.Color.yellow )

fun baseColors() = mutableMapOf(
    "black"      to black    ,
    "blue"       to blue     ,
    "cyan"       to cyan     ,
    "darkgray"   to darkgray ,
    "gray"       to gray     ,
    "green"      to green    ,
    "lightgray"  to lightgray,
    "magenta"    to magenta  ,
    "orange"     to orange   ,
    "pink"       to pink     ,
    "red"        to red      ,
    "white"      to white    ,
    "yellow"     to yellow   ,
)