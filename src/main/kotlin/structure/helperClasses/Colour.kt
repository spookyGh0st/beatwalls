package structure.helperClasses

import java.io.Serializable
import kotlin.math.PI
import kotlin.math.sin


data class Color(val red: Double, val green: Double, val blue: Double, val alpha: Double = 1.0):Serializable{
    constructor(singleColor: java.awt.Color):this(singleColor.red/255.0, singleColor.green/255.0, singleColor.blue/255.0)
    constructor(red: Int, green: Int, blue: Int):this(red/255.0, green/255.0, blue/255.0)
}

internal val red = Color(java.awt.Color.RED)
internal val green = Color(java.awt.Color.GREEN)
internal val blue = Color(java.awt.Color.BLUE)
internal val cyan = Color(java.awt.Color.CYAN)
internal val black = Color(java.awt.Color.BLACK)
internal val white = Color(java.awt.Color.WHITE)



