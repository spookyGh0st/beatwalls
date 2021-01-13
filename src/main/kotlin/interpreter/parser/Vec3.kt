package interpreter.parser

import structure.StructureState
import structure.math.Vec3
import types.bwDouble

internal fun Parser.parseVec3(s: String, ss: StructureState): Vec3 {
    val l = splitExpression(s).filter { it.isNotBlank() }
    val x = l.getOrNull(0)?: "0"
    val y = l.getOrNull(1)?: "0"
    val z = l.getOrNull(2)?: "0"

    return Vec3(
        bwDouble(x, ss).invoke(),
        bwDouble(y, ss).invoke(),
        bwDouble(z, ss).invoke(),
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

