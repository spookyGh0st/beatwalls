package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.rotate(l: List<SpookyWall>): List<SpookyWall> {
    this.rotation.rotateWalls(l)
    return l
}
