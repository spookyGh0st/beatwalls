package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.rotate(l: List<SpookyWall>): List<SpookyWall> {
    this.rotation.rotateWalls(l)
    l.forEach{
        it.localRotation[0]= this.localRotX()
        it.localRotation[1]= this.localRotY()
        it.localRotation[2]= this.localRotZ()
    }
    return l
}
