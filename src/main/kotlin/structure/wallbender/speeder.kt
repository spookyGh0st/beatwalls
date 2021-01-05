package structure.wallbender

import structure.wallStructures.WallStructure
import structure.helperClasses.SpookyWall
import kotlin.math.pow

fun WallStructure.speeder(l: List<SpookyWall>): List<SpookyWall> {
    if(speeder != null){
        val maxZ = l.maxBy { it.trueMaxPoint.z }?.trueMaxPoint?.z ?: 0.0
        l.forEach { wall ->
            wall.startTime = wall.startTime.pow(speeder!!())
            if (wall.duration > 0)
                wall.duration = wall.duration.pow(speeder!!())
        }

        val newMaxZ = l.maxByOrNull { it.trueMaxPoint.z }?.trueMaxPoint?.z ?: 0.0
        val mult = 1/(newMaxZ)*maxZ
        l.forEach {
            it.startTime *= mult
            if(it.duration > 0)
                it.duration *= mult
        }
    }
    return l
}
