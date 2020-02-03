package structure.helperClasses

import kotlin.math.max
import kotlin.math.min

data class CuboidConstrains(val p1: Point, val p2: Point) {
    val sx = min(p1.x, p2.x)
    val ex = max(p1.x, p2.x).coerceAtLeast(sx + 0.0000001)
    val sy = min(p1.y, p2.y)
    val ey = max(p1.y, p2.y).coerceAtLeast(sy + 0.0000001)
    val sz = min(p1.z, p2.z)
    val ez = max(p1.z, p2.z).coerceAtLeast(sz + 0.0000001)
    val duration = ez - sz
    val height = ey - sy
    val width = ex - sx
}