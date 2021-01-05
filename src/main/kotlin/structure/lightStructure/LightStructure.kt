package structure.lightStructure

import structure.BwLight
import structure.Structure
import structure.helperClasses.SpookyWall

abstract class LightStructure: Structure(){
    override fun createElements(): List<SpookyWall> {
        TODO("Not yet implemented")
    }
    abstract fun generate(): List<BwLight>
}

