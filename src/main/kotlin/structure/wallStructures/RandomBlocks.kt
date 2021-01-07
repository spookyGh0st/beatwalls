package structure.wallStructures

import structure.helperClasses.SpookyWall
import kotlin.random.Random

/**
 * place random blocks around the player
 */
class RandomBlocks: WallStructure(){

    var duration = 4

    var amount= 8

    var wallDuration = 1.0

    /**
     * generating the Walls
     */
     override fun create(): List<SpookyWall> {
        val l= mutableListOf<SpookyWall>()
        repeat(amount){
            l.add(createBlock(it.toDouble()/amount*duration,wallDuration))
        }
        return l.toList()
     }
}

private fun RandomBlocks.createBlock(st:Double, d:Double): SpookyWall {
    val r = Random(seed?.invoke() ?: Random.nextInt())
    val sr = r.nextDouble(-20.0, 20.0)
    val w = sr * r.nextDouble()
    val sh = r.nextDouble(5.0)
    val h = sr * r.nextDouble(0.2)
    return SpookyWall(sr, d, w, h, sh, st)
}