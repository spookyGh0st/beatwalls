package structure.wallStructures

import structure.helperClasses.Point
import structure.helperClasses.SpookyWall
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * random curves in a given cubic. Always starts at p1 and ends at p2.
 */
class RandomCurve: WallStructure(){

    /**
     * dont touch
     */
    private var randomSideChooser = Random.nextBoolean()

    /**
     * first Point that controls the cubic, in which section walls are created. defaults to a random side
     */
    var p1: Point = if (randomSideChooser) Point(
        1,
        0,
        0
    ) else Point(-1, 0, 0)

    /**
     * second Point that crontrols in which section walls are created. z must be higher than p1
     */
    var p2: Point = if (randomSideChooser) Point(
        4,
        0,
        1
    ) else Point(-4, 4, 1)

    /**
     * the amount of Walls per beat
     */
    var amount: Int = 8

    /**
     * generating the Walls
     */
     override fun create(): List<SpookyWall> {
        val l= mutableListOf<SpookyWall>()
        val r = Random(seed?.invoke() ?: Random.nextInt())
        val mult: Double
        if((p2.z-p1.z) < 1){
            mult = 1 / (p2.z-p1.z)
            p2  = p2.copy(z=p1.z+1)
        }else{
            mult = 1.0
        }
        var tp3 = randomTimedPoint(r, -0.33 * mult)
        var tp4 = p1
        for(i in p1.z.toInt() until p2.z.toInt()) {
            val tp1 = tp4.copy()
            val tp2 = tp4.mirrored(tp3)
            tp3 = randomTimedPoint(r, i + 0.66 * mult)
            tp4 = if (i + 1 == p2.z.toInt())
                p2.copy()
            else
                randomTimedPoint(r, i + 1.0 * mult)
            l.addAll(curve(tp1, tp2, tp3, tp4, amount))
        }
        return l.toList()
     }
}


private fun RandomCurve.randomTimedPoint(r: Random, z: Double): Point {
    val minx = min(p1.x, p2.x)
    val maxX = max(p1.x, p2.x).coerceAtLeast(minx + 0.1)
    val minY = min(p1.y, p2.y)
    val maxY = max(p1.y, p2.y).coerceAtLeast(minY + 0.1)
    val x = r.nextDouble(minx, maxX)
    val y = r.nextDouble(minY, maxY)
    return Point(x, y, z)
}