package structure.wallStructures

import structure.helperClasses.SpookyWall

/**
 * Creates no Elements at all.
 * Why should you use it? idk, i need it for some internal stuff.
 */
class EmptyWallStructure: WallStructure() {
    override fun generate() = emptyList<SpookyWall>()
}



