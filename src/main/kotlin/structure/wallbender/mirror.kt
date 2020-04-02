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
        startRow = 2*mirrorX-wall.startRow,
        width = -wall.width,
        rotation = if(mirrorRotation) -wall.rotation else wall.rotation,
        localRotation = if(mirrorRotation)
            wall.localRotation.clone().also {  it[1] = -it[1] }
        else
            wall.localRotation

    ) }

internal fun WallStructure.mirrorY(list: List<SpookyWall>) =
    list.map { it.copy(
        startHeight = 2*mirrorY-it.startHeight,
        height = -it.height
    ) }
