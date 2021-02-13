package math

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.PI
import kotlin.random.Random

class QuaternionTest{
    private val delta = 0.00001

    @Test
    fun `from axis to euler`(){
        data class C(val axis: Vec3, val angle: Double, val euler: Vec3)
        val table = listOf(
            C(Vec3(1.0, 0.0, 0.0),    0.0,    Vec3(0.0, 0.0, 0.0)),
            C(Vec3(1.0, 0.0, 0.0),    0.5*PI, Vec3(0.5*PI,  0.0, 0.0)),
            C(Vec3(0.0, 1.0, 0.0),    0.5*PI, Vec3(0.0,     0.5*PI,  0.0)),
            C(Vec3(0.0, 0.0, 1.0),    0.5*PI, Vec3(0.0, 0.0, 0.5*PI )),
        )
        for ((index, t) in table.withIndex()){
            val q = Quaternion(t.axis,t.angle)
            val e = q.toEuler()
            val msg = {s: String -> "Failed on $index on variable $s run\n expected: ${t.euler}, actual: $e" }
            assertEquals(msg("x"),t.euler.x, e.x, delta)
            assertEquals(msg("y"),t.euler.y, e.y, delta)
            assertEquals(msg("z"),t.euler.z, e.z, delta)
        }
    }

    @Test
    fun `from euler to euler`(){
        val rand = Random(0)
        val r = { rand.nextDouble(0.0,0.5* PI) }
        for (i in 0..10){
            val exp = Vec3(r(),r(),r())
            val q = Quaternion(exp)
            val act= q.toEuler()
            val msg = {s: String -> "Failed on $i on variable $s run\n expected: $exp, actual: $act" }
            assertEquals(msg("x"),exp.x, act.x, delta)
            assertEquals(msg("y"),exp.y, act.y, delta)
            assertEquals(msg("z"),exp.z, act.z, delta)
        }
    }
}