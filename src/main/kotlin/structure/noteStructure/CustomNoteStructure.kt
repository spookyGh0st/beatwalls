package structure.noteStructure

import structure.CustomStructInterface
import structure.bwElements.BwNote

class CustomNoteStructure(
    override val name: String,
    override val superStructure: NoteStructure,
    override val structures: List<NoteStructure>
) : NoteStructure(), CustomStructInterface {
    override fun generate(): List<BwNote> =
        superStructure.generate() + structures.flatMap { it.generate() }
}