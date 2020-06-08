package interpreter.property.specialProperties

import org.junit.Test

import org.junit.Assert.*

class GradientTest {

    @Test
    fun apply() {
    }

    @Test
    fun calc() {
        val ts = TestStruct(0.0)
        val g = Gradient(BaseColor.BLUE){ ts.value }
        var actual = g.calc(doubleArrayOf(1.0,0.0,0.0,2.0,0.0,0.0),0)
        assertEquals(1.0,actual,0.0)
        ts.value=1.0
        actual = g.calc(doubleArrayOf(1.0,0.0,0.0,2.0,0.0,0.0),0)
        assertEquals(2.0,actual,0.0)
    }

    data class TestStruct(var value: Double)
}