package structure

sealed class NoteStructure:Structure()

class CustomNoteStructure(
    override val name: String,
    override val superStructure: NoteStructure,
    override val structures: List<NoteStructure>
) : NoteStructure(), CustomStructInterface{
    override fun generate(): List<*> {
        return superStructure.generate() + structures.flatMap { it.generate() }
    }
}
