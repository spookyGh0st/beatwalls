package interpreter

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import structure.Structure
import structure.bwElements.BwElement

class Evaluator(val structs: List<Structure>, val bw: Beatwalls) {
    val elements = mutableListOf<BwElement>()
    fun evaluate(): List<BwElement> {
        runBlocking {
            for (s in structs) {
                launch { elements.addAll(s.createElements()) }
            }
        }
        return elements.toList()
    }
}