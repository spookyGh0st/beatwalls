package structure.wallbender

import beatwalls.errorExit
import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.mirror(l: List<SpookyWall>) =
    when(mirror){
        0-> l
        1-> l.mirrorX()
        2-> l + l.mirrorX()
        3-> l.mirrorY()
        4-> l + l.mirrorY()
        5-> l.mirrorX().mirrorY()
        6-> l + l.mirrorX().mirrorY()
        7-> l.mirrorX() + l.mirrorY()
        8-> l + l.mirrorX() + l.mirrorY() + l.mirrorX().mirrorY()
        else -> errorExit { "Mirror can only be 0-8, check the documentation" }
    }

internal fun List<SpookyWall>.mirrorX()=
    this.map { it.copy(startRow = -it.startRow, width = -it.width ) }

internal fun List<SpookyWall>.mirrorY()=
    this.map { it.copy(startHeight = 2+(2-it.startHeight),height = -it.height) }

