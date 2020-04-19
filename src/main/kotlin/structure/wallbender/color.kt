package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.color(l: List<SpookyWall>): List<SpookyWall> {
    val sl = l.sortedBy { it.z }
    this.color.colorWalls(sl)
    return sl
}
