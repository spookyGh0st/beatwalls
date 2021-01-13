package structure.helperClasses

import java.io.Serializable


data class Color(val red: Double, val green: Double, val blue: Double, val alpha: Double = 1.0):Serializable{
    constructor(singleColor: java.awt.Color):this(singleColor.red/255.0, singleColor.green/255.0, singleColor.blue/255.0)
    constructor(red: Int, green: Int, blue: Int):this(red/255.0, green/255.0, blue/255.0)

    fun toList() =
        listOf(red,green,blue,alpha)
}
