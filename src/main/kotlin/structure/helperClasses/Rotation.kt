package structure.helperClasses

import interpreter.property.functions.BwEasing
import java.io.Serializable
import java.lang.Double.max
import java.lang.Double.min
import kotlin.random.Random


// only here for now until i implemented everything
interface RotationMode:Serializable{
    fun getValue(index: Int,amount: Int) : Double
}

data class StaticRotation(val rotation: Double): RotationMode {
    override fun getValue(index: Int, amount: Int): Double = rotation
}

data class EaseRotation(val startRotation: Double, val endRotation: Double, val easing: BwEasing): RotationMode {
    override fun getValue(index: Int, amount: Int): Double {
            val diff = endRotation - startRotation
            //val mul =easing(index.toDouble()/amount)
            //return  startRotation + diff*mul
        return 0.0
    }
}

data class CirclesRotation(private val repetitions: Double = 1.0): RotationMode{
    override fun getValue(index: Int, amount: Int): Double {
            return index.toDouble()/amount*360*repetitions
    }
}
data class RandomRotation(private val min: Double ,private val max: Double , private val r:Random): RotationMode{
    override fun getValue(index: Int, amount: Int): Double {
        val n = min(min,max)
        val m = max(min,max).coerceAtLeast(n+0.00000000001)
        return r.nextDouble(n,m)
    }
}

data class SwitchRotation(val rotations: List<Double>): RotationMode{
    override fun getValue(index: Int, amount: Int): Double {
        return rotations[index % rotations.size]
    }
}

object NoRotation: RotationMode {
    override fun getValue(index: Int, amount: Int): Double =0.0
}

