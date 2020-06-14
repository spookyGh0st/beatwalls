package structure.helperClasses

import interpreter.property.functions.getOrZero
import java.io.Serializable
import kotlin.math.PI
import kotlin.math.sin


data class Color(val red: Double, val green: Double, val blue: Double):Serializable{
    constructor(singleColor: java.awt.Color):this(singleColor.red/255.0, singleColor.green/255.0, singleColor.blue/255.0)
    constructor(red: Int, green: Int, blue: Int):this(red/255.0, green/255.0, blue/255.0)
    constructor(l: List<Double>):this(l.getOrZero(0), l.getOrZero(1) , l.getOrZero(2))
}

internal val red = Color(java.awt.Color.RED)
internal val green = Color(java.awt.Color.GREEN)
internal val blue = Color(java.awt.Color.BLUE)
internal val cyan = Color(java.awt.Color.CYAN)
internal val black = Color(java.awt.Color.BLACK)
internal val white = Color(java.awt.Color.WHITE)


interface ColorMode:Serializable{
    fun colorWalls(walls: Collection<SpookyWall>)
}

data class SingleColor(val c: Color): ColorMode {
    override fun colorWalls(walls: Collection<SpookyWall>) {
        walls.forEach{
            it.color = c
        }
    }
}

data class Gradient(val startColor: Color, val endColor: Color): ColorMode {
    override fun colorWalls(walls: Collection<SpookyWall>) {
        val cr = endColor.red - startColor.red
        val cg = endColor.green - startColor.green
        val cb = endColor.blue - startColor.blue
        val amount= walls.size
        for((index, w) in walls.withIndex()){
            val red = startColor.red + cr*index/(amount - 1)
            val green = startColor.green + cg*index/(amount - 1)
            val blue = startColor.blue + cb*index/(amount - 1)
            w.color=Color(red,green,blue)
        }
    }
}

data class Rainbow(private val repetitions: Double = 1.0): ColorMode{
    override fun colorWalls(walls: Collection<SpookyWall>) {
        for ((index,w) in walls.withIndex()){
            val i = index.toDouble()/walls.size *2* PI * repetitions
            val r = sin(i+0.0/3.0 * PI) /2 + 0.5
            val g = sin(i+2.0/3.0 * PI) /2 + 0.5
            val b = sin(i+4.0/3.0 * PI) /2 + 0.5
            w.color=Color(r,g,b)
        }
    }
}

data class Flash(val colors: List<Color>): ColorMode{
    override fun colorWalls(walls: Collection<SpookyWall>) {
        for ((index,w) in walls.withIndex()){
            w.color = colors[index % colors.size]
            if(colors.size == 1 && index %2 == 1)
                w.color=null
        }
    }
}

object NoColor: ColorMode {
    override fun colorWalls(walls: Collection<SpookyWall>) {
    }
}

