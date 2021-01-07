package structure.wallStructures

import structure.CustomStructInterface
import structure.helperClasses.SpookyWall

/**
 * Cannot be directly called, but instead must be called using the Define: Parameter
 */
class CustomWallStructure(
    override val name: String,
    override val superStructure: WallStructure,
    override val structures: List<WallStructure>
) : WallStructure(), CustomStructInterface {
        override fun create(): List<SpookyWall> =
            superStructure.create() + structures.flatMap { it.create() }
}