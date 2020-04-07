package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.rotate(l: List<SpookyWall>): List<SpookyWall> {
    this.rotation.rotation(l)
    this.localRotX.localRotX(l)
    this.localRotY.localRotY(l)
    this.localRotZ.localRotZ(l)
    return l
}
