package structure.specialStrucures

import beatwalls.errorExit
import structure.Define
import structure.add
import structure.walls

fun Define.run(){
    when {
        structures.isEmpty() -> errorExit { "The structureList of $name is empty, check if you have a typo " }
        structures.contains(this) -> errorExit { "NO RECURSION, BAD BOY" }
    }

    for(w in structures){
        val l = w.walls()
        l.forEach { it.startTime+=(w.beat) }
        add(l)
    }
}