package structure.lightStructure

import structure.Structure
import structure.bwElements.BwEvent

abstract class LightStructure: Structure(){
    override fun createElements(): List<BwEvent> {
        TODO("Not yet implemented")
    }
    abstract fun generate(): List<BwEvent>
}

