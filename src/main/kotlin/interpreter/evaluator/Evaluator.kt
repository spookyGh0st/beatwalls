package interpreter.evaluator

import structure.Structure
import structure.helperClasses.BwElement

class Evaluator(val structs: List<Structure>) {
    val elements = mutableListOf<BwElement>()

    fun evaluate(): List<BwElement>{
        return elements.toList()
    }
}