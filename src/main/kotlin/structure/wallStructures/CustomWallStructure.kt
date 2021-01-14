package structure.wallStructures

import structure.CustomStructInterface
import structure.bwElements.BwObstacle

/**
 * Create a custom Wallstructure by building on other Structures
 *
 * @sample
 * ```yaml
 * Define: MyLine
 *   structures: Line
 *   p0: 2,0,0
 *   p1: 4,4,2
 */
class CustomWallStructure(
    override val name: String,
    override val superStructure: WallStructure,
    override val structures: List<WallStructure>
) : WallStructure(), CustomStructInterface {
        override fun createWalls(): List<BwObstacle> =
            superStructure.createWalls() + structures.flatMap { it.createWalls() }
}