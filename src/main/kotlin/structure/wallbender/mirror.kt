package structure.wallbender

import beatwalls.errorExit
import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.mirror(l: List<SpookyWall>) =
    when(mirror){
        0-> l
        1-> mirrorX(l)
        2-> l + mirrorX(l)
        3-> mirrorY(l)
        4-> l + mirrorY(l)
        5-> mirrorY(mirrorX(l))
        6-> l + mirrorY(mirrorX(l))
        7-> mirrorX(l) + mirrorY(l)
        8-> l + mirrorX(l) + mirrorY(l) + mirrorY(mirrorX(l))
        else -> errorExit { "Mirror can only be 0-8, check the documentation" }
    }

internal fun WallStructure.mirrorX(list: List<SpookyWall>) =
    list.map { wall ->
        wall.copy(
        x = 2*mirrorX-wall.x,
        width = -wall.width,
        rotation = if(mirrorRotation) -wall.rotation else wall.rotation,
        localRotY = if(mirrorRotation) -wall.localRotY else wall.localRotY,
            localRotZ = if(mirrorRotation) -wall.localRotZ else wall.localRotZ
    ) }

internal fun WallStructure.mirrorY(list: List<SpookyWall>) =
    list.map { it.copy(
        y = 2*mirrorY-it.y,
        height = -it.height,
        localRotX = if(mirrorRotation) it.localRotX else it.localRotX,
        localRotZ = if(mirrorRotation) it.localRotZ else it.localRotZ
    ) }
