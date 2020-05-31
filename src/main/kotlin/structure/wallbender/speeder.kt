package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall
import kotlin.math.pow

fun WallStructure.speeder(l: List<SpookyWall>) {
    if(speeder != null){
        val maxZ = l.maxBy { it.trueMaxPoint.z }?.trueMaxPoint?.z ?: 0.0
        for ((i, wall) in l.withIndex()) {
            this.activeWall = wall
            this.i = i.toDouble() / l.size
            wall.z = wall.z.pow(speeder!!)
            if (wall.duration > 0)
                wall.duration = wall.duration.pow(speeder!!)
        }

        val newMaxZ =l.maxBy { it.trueMaxPoint.z }?.trueMaxPoint?.z ?: 0.0
        val mult = 1/(newMaxZ)*maxZ
        for ((i, wall) in l.withIndex()) {
            this.activeWall = wall
            this.i = i.toDouble() / l.size
            wall.z *= mult
            if(wall.duration > 0)
                wall.duration *= mult
        }
    }
}
