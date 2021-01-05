package structure.wallbender

import structure.wallStructures.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.rotate(l: List<SpookyWall>): List<SpookyWall> {
    this.rotation.rotation(l)
    this.rotationX.rotationX(l)
    this.rotationY.rotationY(l)
    this.rotationZ.rotationZ(l)
    this.localRotX.localRotX(l)
    this.localRotY.localRotY(l)
    this.localRotZ.localRotZ(l)
    return l
}
