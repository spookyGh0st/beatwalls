package structure.specialStrucures

import structure.RandomBlocks
import structure.helperClasses.SpookyWall
import kotlin.random.Random


fun RandomBlocks.run(): List<SpookyWall> {
    val l= mutableListOf<SpookyWall>()
    repeat(amount){
        l.add(createBlock(it.toDouble()/amount*duration,wallDuration))
    }
    return l.toList()
}
private fun RandomBlocks.createBlock(st:Double ,d:Double): SpookyWall {
    val r = Random(seed?: Random.nextInt())
    val sr = r.nextDouble(-20.0, 20.0)
    val w = sr * r.nextDouble()
    val sh = r.nextDouble(5.0)
    val h = sr * r.nextDouble(0.2)
    return SpookyWall(sr, d, w, h, sh, st)
}
