package structure.wallbender

import beatwalls.errorExit
import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.mirror(origin: List<SpookyWall>): MutableList<SpookyWall> {
    val list = mutableListOf<SpookyWall>()
    for ((index, w) in origin.withIndex()) {
        activeWall = w
        i = index.toDouble()/origin.size
        // just cause it looks a bit nicer
        val t = listOf(w)
        list += when (mirror) {
            0 -> t
            1 -> mirrorX(t)
            2 -> t + mirrorX(t)
            3 -> mirrorY(t)
            4 -> t + mirrorY(t)
            5 -> mirrorY(mirrorX(t))
            6 -> t + mirrorY(mirrorX(t))
            7 -> mirrorX(t) + mirrorY(t)
            8 -> t + mirrorX(t) + mirrorY(t) + mirrorY(mirrorX(t))
            else -> errorExit { "Mirror can only be 0-8, check the documentation" }
        }
    }
    return list
}
fun WallStructure.mirrorX(w: List<SpookyWall>) =
    w.map { it.mirrorX(this.mirrorX,this.mirrorRotation) }
fun WallStructure.mirrorY(w: List<SpookyWall>) =
    w.map { it.mirrorY(this.mirrorY,this.mirrorRotation) }

internal fun SpookyWall.mirrorX(mirrorX: Double, mirrorRotation: Boolean) =
    this.copy(
        x = 2*mirrorX-this.x,
        width = -width,
        rotationY = if(mirrorRotation) -this.rotationY else this.rotationY,
        rotationZ = if(mirrorRotation) -this.rotationZ else this.rotationZ,
        localRotY = if(mirrorRotation) -this.localRotY else this.localRotY,
        localRotZ = if(mirrorRotation) -this.localRotZ else this.localRotZ
    )

internal fun SpookyWall.mirrorY(mirrorY: Double, mirrorRotation: Boolean) =
    this.copy(
        y = 2*mirrorY-this.y,
        height = -this.height,
        rotationX = if(mirrorRotation) -this.rotationX else this.rotationX,
        rotationZ = if(mirrorRotation) -this.rotationZ else this.rotationZ,
        localRotX = if(mirrorRotation) this.localRotX else this.localRotX,
        localRotZ = if(mirrorRotation) this.localRotZ else this.localRotZ
    )
