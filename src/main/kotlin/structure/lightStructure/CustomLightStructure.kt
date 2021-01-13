package structure.lightStructure

import structure.CustomStructInterface
import structure.helperClasses.BwEvent

class CustomLightStructure(
    override val name: String,
    override val superStructure: LightStructure,
    override val structures: List<LightStructure>
) : LightStructure(), CustomStructInterface {
    override fun generate(): List<BwEvent> =
        superStructure.generate() + structures.flatMap { it.generate() }
}