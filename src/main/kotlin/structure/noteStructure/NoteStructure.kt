package structure.noteStructure

import structure.Structure
import structure.helperClasses.SpookyWall

abstract class NoteStructure: Structure(){
    override fun createElements(): List<SpookyWall> {
        TODO("Not yet implemented")
    }
    abstract fun generate(): List<BwNote>
}

