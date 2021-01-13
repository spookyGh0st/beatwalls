package structure.helperClasses

import org.junit.Test

import structure.math.CubicSpline
import structure.math.Vec3

class CubicSplineTest {

    @Test
    fun splineAtTime() {
        val testPoints = listOf<Vec3>(
            Vec3(0,0,0),
            Vec3(0,10,1),
            Vec3(10,0,2),
            Vec3(0,0,3),
        )
        val s = CubicSpline(testPoints)
        val count = 30
        for (i in 0..count)
        println("$i: ${s.splineAtTime(i/count.toDouble())}")
    }

    @Test
    fun constVelocitySplineAtTime() {
    }
}