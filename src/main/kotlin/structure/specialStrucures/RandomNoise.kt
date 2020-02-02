package structure.specialStrucures

import structure.CuboidConstrains
import structure.RandomNoise
import structure.SpookyWall
import structure.add
import kotlin.math.roundToInt
import kotlin.random.Random

fun RandomNoise.run(){
    val c = CuboidConstrains(p1, p2)
    val r = Random(seed)
    amount = amount ?: (8 * (c.ez - c.sz)).roundToInt()
    repeat(amount!!) {

        val w = SpookyWall(
            startRow = r.nextDouble(c.sx, c.ex),
            duration = 0.0,
            width = 0.0,
            height = 0.0,
            startHeight = r.nextDouble(c.sy, c.ey),
            startTime = c.sz + (it.toDouble() / amount!! * (c.ez - c.sz))
        )
        add(w)
    }
}
