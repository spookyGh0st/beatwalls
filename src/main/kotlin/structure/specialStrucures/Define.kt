package structure.specialStrucures

import beatwalls.errorExit
import structure.Define
import structure.helperClasses.SpookyWall
import structure.wallbender.generateBendAndRepeatWalls

fun Define.run(): MutableList<SpookyWall> {
    when {
        structures.isEmpty() -> errorExit { "The structureList of $name is empty, check if you have a typo " }
        structures.contains(this) -> errorExit { "NO RECURSION, BAD BOY" }
    }
    val l= mutableListOf<SpookyWall>()

    for(w in structures){
        val t = w.generateBendAndRepeatWalls()
        t.forEach { it.z += (w.beat) }
        l.addAll(t)
    }
    return l
}