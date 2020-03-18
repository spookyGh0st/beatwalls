package structure.helperClasses

import java.io.Serializable


interface RotationMode:Serializable{
    fun rotateWalls(walls: Collection<SpookyWall>)
}

data class StaticRotation(val rotation: Double): RotationMode {
    override fun rotateWalls(walls: Collection<SpookyWall>) {
        walls.forEach{
            it.rotation = rotation
        }
    }
}

data class EaseRotation(val startRotation: Double, val endRotation: Double, val easing: Easing): RotationMode {
    override fun rotateWalls(walls: Collection<SpookyWall>) {
        val amount= walls.size
        for((index, w) in walls.withIndex()){
            w.rotation=easing(index.toDouble()/amount)
        }
    }
}

data class CirclesRotation(private val repetitions: Double = 1.0): RotationMode{
    override fun rotateWalls(walls: Collection<SpookyWall>) {
        val amount = walls.size
        for ((index,w) in walls.withIndex()){
            w.rotation = index.toDouble()/amount*360*repetitions
        }
    }
}

data class SwitchRotation(val rotations: List<Double>): RotationMode{
    override fun rotateWalls(walls: Collection<SpookyWall>) {
        for ((index,w) in walls.withIndex()){
            w.rotation = rotations[index % rotations.size]
            if(rotations.size == 1 && index %2 == 1)
                w.rotation=null
        }
    }
}

object NoRotation: RotationMode {
    override fun rotateWalls(walls: Collection<SpookyWall>) {
    }
}

