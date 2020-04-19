package structure.specialStrucures

import beatwalls.errorExit
import structure.RandomStructures
import structure.helperClasses.CuboidConstrains
import structure.helperClasses.SpookyWall
import structure.wallbender.generateBendAndRepeatWalls
import kotlin.random.Random

fun RandomStructures.run(): MutableList<SpookyWall> {
    when {
        structures.isEmpty() -> errorExit { "The structureList of this at beat $beat is empty, check if you have a typo " }
        structures.contains(this) -> errorExit { "NO RECURSION, BAD BOY" }
    }
    val l= mutableListOf<SpookyWall>()

    val cc = CuboidConstrains(p1,p2,seed?: Random.nextInt())
    for(i in 0 until amount){
        for(w in structures){
            val t = w.generateBendAndRepeatWalls()
            t.forEach { it.z += (w.beat) }
            val rp = cc.random(avoidCenter)
            t.forEach { it.x += rp.x }
            t.forEach { it.y += rp.y }
            t.forEach { it.z += i.toDouble()/amount * cc.duration}
            l.addAll(t)
        }
    }
    return l
}