package structure.bwElements

import java.io.Serializable


/**
 * Color in the values 0-255 for rgb, 0-1 for 1
 */
data class Color(val red: Double, val green: Double, val blue: Double, var alpha: Double = 255.0):Serializable{
    constructor(singleColor: java.awt.Color):this(singleColor.red.toDouble(), singleColor.green.toDouble(), singleColor.blue.toDouble())
    operator fun plus(c: Color): Color {
        return Color(red + c.red, green + c.green, blue + c.blue, alpha + c.alpha)
    }
    operator fun minus(c: Color): Color{
        return Color(red - c.red,  green - c.green, blue - c.blue, alpha - c.alpha)
    }
    operator fun times(fac: Double): Color {
        return Color(red * fac, green * fac, blue * fac, alpha*fac)
    }

    fun toNeList(): List<Double> = listOf(red/255,green/255,blue/255,alpha/255)
    fun toList(): List<Double> = listOf(red,green,blue,alpha)
}
