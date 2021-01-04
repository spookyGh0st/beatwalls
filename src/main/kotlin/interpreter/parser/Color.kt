package interpreter.parser

import structure.StructureState
import types.*
import types.gradient
import types.rainbow
import types.random

fun Parser.parseColor(){
    var r = 0.0
    var g = 0.0
    var b = 0.0
    var a = 1.0

    val errorMsg = { d:Double -> errorTP("Only direct Values are allowed. Sorry").let { d }}
    for (tp in currentBlock.properties){
        currentTP = tp
        when (tp.k.toLowerCase()){
            "r"     -> r = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "red"   -> r = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "g"     -> g = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "green" -> g = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "b"     -> b = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "blue"  -> b = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "a"     -> a = tp.v.toDoubleOrNull()?: errorMsg(1.0)
            "alpha" -> a = tp.v.toDoubleOrNull()?: errorMsg(1.0)
        }
    }

    val c = bwColor(r,g,b,a)
    colors[currentBlock.name.toLowerCase()] = c

}


/**
 * Translation from a string to the color
 * This is a bit more difficult, since we can't use our cool framework directly.
 * Instead we will do a bit of manual Parsing of the few functions we have
 */
fun Parser.bwColor(s: String, ss: StructureState): BwColor? {
    val sanitizedString = s.toLowerCase().replace(" ", "")
    return colors[sanitizedString]?: colorFunc(s, ss)
}

fun Parser.colorFunc(s: String, ss: StructureState) : BwColor? {
    val head = s.substringBefore("(")
    val args = s
        .substringAfter(head)
        .removeSurrounding("(", ")")
        .replace("(","")
        .replace(")","")
        .split(",")
        .filter { it.isNotEmpty() }

    when (head){
        "gradient" -> {
            val colorList: List<BwColor> = colorList(args)?: return null
            if (colorList.size != 2) { errorTP("Only 2 values are allowed for the gradient"); return null }
            return gradient(ss,colorList[0],colorList[1])
        }
        "rainbow" -> {
            val r = args.getOrNull(0)?.toDoubleOrNull()?: 1.0
            val a = args.getOrNull(1)?.toDoubleOrNull()?: 1.0
            return rainbow(ss,r,a)
        }
        "random" -> {
            val colorList = colorList(args)?: return null
            return random(colorList)
        }
        else -> {
            errorTP("Color function $head is invalid. if you were looking for switch, it is now random()")
            return null
        }
    }
}

fun Parser.colorList(args: List<String>): List<BwColor>? {
    val l = mutableListOf<BwColor>()
    args.forEach {
        val c = colors[it.toLowerCase().trim()]
        if (c != null)
            l.add(c)
        else{
            errorTP("The Color $it does not exist. Available Colors:\n ${colors.keys}")
            return null
        }
    }
    return  l.toList()
}
