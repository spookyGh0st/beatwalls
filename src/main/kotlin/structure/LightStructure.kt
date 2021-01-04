package structure

sealed class LightStructure: Structure()

class CustomLightStructure(
    override val name: String,
    override val superStructure: LightStructure,
    override val structures: List<LightStructure>
) : LightStructure(), CustomStructInterface{
    override fun generate(): List<*> {
        return superStructure.generate() + structures.flatMap { it.generate() }
    }
}
