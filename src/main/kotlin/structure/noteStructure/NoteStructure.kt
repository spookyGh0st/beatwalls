package structure.noteStructure

import structure.Structure
import structure.helperClasses.BwNote

abstract class NoteStructure: Structure(){
    override fun createElements(): List<BwNote> {
        TODO("Not yet implemented")
    }
    abstract fun generate(): List<BwNote>
}

