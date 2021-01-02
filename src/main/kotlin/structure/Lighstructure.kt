package structure

sealed class Lighstructure: Structure() {

}

class CustomLsStructure(
    override val name: String,
    override val superStructure: Lighstructure,
    override val structures: List<Lighstructure>
) : Lighstructure(), CustomStructInterface{
    override fun generate(): List<*> {
        return superStructure.generate() + structures.flatMap { it.generate() }
    }
}
