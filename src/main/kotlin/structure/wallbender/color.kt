package structure.wallbender

import structure.WallStructure
import structure.helperClasses.Color
import structure.helperClasses.SpookyWall

fun WallStructure.color(l: List<SpookyWall>): List<SpookyWall> {
    val sl = l.sortedBy { it.startTime }
    if (this.color == null) return sl
    sl.forEach {
        it.color = Color(this.color!!.r(),this.color!!.g(), this.color!!.b())
    }
    return sl
}
