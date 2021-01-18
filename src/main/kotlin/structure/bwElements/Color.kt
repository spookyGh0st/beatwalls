package structure.bwElements

import java.io.Serializable


data class Color(val red: Double, val green: Double, val blue: Double, val alpha: Double = 1.0):Serializable{
    constructor(singleColor: java.awt.Color):this(singleColor.red/255.0, singleColor.green/255.0, singleColor.blue/255.0)
    constructor(red: Int, green: Int, blue: Int):this(red/255.0, green/255.0, blue/255.0)
    operator fun plus(c: Color): Color {
        return Color(red + c.red, green + c.green, blue + c.blue, alpha + c.alpha)
    }
    operator fun minus(c: Color): Color{
        return Color(red - c.red,  green - c.green, blue - c.blue, alpha - c.alpha)
    }
    operator fun times(fac: Double): Color {
        return Color(red * fac, green * fac, blue * fac, alpha*fac)
    }

    fun toList(): List<Double> = listOf(red,green,blue,alpha)
}
