package structure.specialStrucures

import structure.Define
import structure.add
import structure.walls

fun Define.run(){
    if (structures.isEmpty()){
        //todo structure.logger.error { "The structureList of $name is empty, check if you have a typo " }
    }
    for(w in structures){
        val l = w.walls()
        l.forEach { it.startTime+=(w.beat) }
        add(l)
    }
}