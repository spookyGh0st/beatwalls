package structure.helperClasses

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class CuboidConstrains(val p1: Point, val p2: Point,var seed: Int = Random.nextInt()) {
    val sx = min(p1.x, p2.x)
    val ex = max(p1.x, p2.x).coerceAtLeast(sx + 0.0000001)
    val sy = min(p1.y, p2.y)
    val ey = max(p1.y, p2.y).coerceAtLeast(sy + 0.0000001)
    val sz = min(p1.z, p2.z)
    val ez = max(p1.z, p2.z).coerceAtLeast(sz + 0.0000001)
    val duration = ez - sz
    val height = ey - sy
    val width = ex - sx
    private val r = Random(seed)
    fun random(avoidCenter: Boolean): Point {
        val p = Point(r.nextDouble(sx,ex),r.nextDouble(sy,ey), r.nextDouble(sz,ez))
        if(avoidCenter && p.x in -2.0..2.0 && p.y in 0.0..2.0)
            return random(avoidCenter)
        return p
    }
}