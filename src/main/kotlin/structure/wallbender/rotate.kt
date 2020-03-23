package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.rotate(l: List<SpookyWall>): List<SpookyWall> {
    this.rotation.rotateWalls(l)
    l.forEach{
        it.localRotation[0]= this.wallRotationX()
        it.localRotation[1]= this.wallRotationY()
        it.localRotation[2]= this.wallRotationZ()
    }
    return l
}
