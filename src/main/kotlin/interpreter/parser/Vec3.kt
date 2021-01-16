package interpreter.parser

import structure.StructureState
import structure.math.Vec2
import structure.math.Vec3
import types.bwDouble

internal fun Parser.parseVec2(s: String, ss: StructureState): Vec2? {
    val l = splitExpression(s).filter { it.isNotBlank() }
    val x = l.getOrNull(0)
    val y = l.getOrNull(1)
    if (x == null || y == null){
        errorTP("Not enough values provides. Format: x,y")
        return null
    }

    val vec2X = bwDouble(x,ss)
    val vec2Y = bwDouble(y,ss)

    if (vec2X == null || vec2Y == null){
        errorTP("Not enough values provides. Format: x,y")
        return null
    }

    return Vec2(
        vec2X.invoke(),
        vec2Y.invoke(),
    )
}
internal fun Parser.parseVec3(s: String, ss: StructureState): Vec3 {
    val l = splitExpression(s).filter { it.isNotBlank() }
    val x = l.getOrNull(0)?: "0"
    val y = l.getOrNull(1)?: "0"
    val z = l.getOrNull(2)?: "0"

    return Vec3(
        bwDouble(x, ss)!!.invoke(), // todo
        bwDouble(y, ss)!!.invoke(),
        bwDouble(z, ss)!!.invoke(),
    )
}

private fun splitExpression(s: String): List<String> {
    var start = 0
    var pCount = 0
    val l = mutableListOf<String>()

    for ((i, c) in s.withIndex()){
        when(c){
            ',' -> if (0 == pCount) {
                l.add(s.substring(start,i))
                start = i+1
            }
            '(' -> pCount++
            ')' -> pCount--
        }
    }
    l.add(s.substring(start).trim())
    return l.toList()
}

