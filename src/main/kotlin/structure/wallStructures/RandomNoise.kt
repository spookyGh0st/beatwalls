package structure.wallStructures

import structure.helperClasses.CuboidConstrains
import structure.helperClasses.Point
import structure.helperClasses.SpookyWall
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * Random Noise (small mini cubes)
 */
class RandomNoise: WallStructure(){
    /**
     * the amount of the created Walls, if no value is given creates 8x the beatcount
     */
    var amount: Int? = null

    /**
     * controls one corner of the Area
     */
    var p1 = Point(-6, 0, 0)

    /**
     * controls the other corner of the Area
     */
    var p2 = Point(6, 5, 1)

    /**
     * avoids spawning structures in the playspace. default: true
     */
    var avoidCenter: Boolean = true
    /**
     * generating the Walls
     */
    override fun create(): List<SpookyWall> {
        val l = mutableListOf<SpookyWall>()
        val c = CuboidConstrains(p1, p2, seed?.invoke()?: Random.nextInt())
        amount = amount ?: (8 * (c.ez - c.sz)).roundToInt()
        repeat(amount!!) {
            val p = c.random(true)
            val w = SpookyWall(
                startRow = p.x,
                duration = 0.0,
                width = 0.0,
                height = 0.0,
                startHeight = p.y,
                startTime = c.sz + (it.toDouble() / amount!! * (c.ez - c.sz))
            )
            l.add(w)
        }
        return l.toList()
    }
}