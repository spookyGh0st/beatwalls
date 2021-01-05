package structure.lightStructure

import structure.BwLight
import structure.CustomStructInterface

class CustomLightStructure(
    override val name: String,
    override val superStructure: LightStructure,
    override val structures: List<LightStructure>
) : LightStructure(), CustomStructInterface {
    override fun generate(): List<BwLight> =
        superStructure.generate() + structures.flatMap { it.generate() }
}