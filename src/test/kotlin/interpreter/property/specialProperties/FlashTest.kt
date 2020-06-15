package interpreter.property.specialProperties

import org.junit.Test

import org.junit.Assert.*

class FlashTest {
    data class TestStruct(var value: Double)

    @Test
    fun apply() {
        val t = TestStruct(10.0)
        val e = Flash(BaseColor.RED,2)
        assertEquals(10.0, e.apply(10.0,0.0,0.0,20.0,0.0,0.0),0.0)
        assertEquals(20.0, e.apply(10.0,0.0,0.0,20.0,0.0,0.0),0.0)
        assertEquals(10.0, e.apply(10.0,0.0,0.0,20.0,0.0,0.0),0.0)
        assertEquals(20.0, e.apply(10.0,0.0,0.0,20.0,0.0,0.0),0.0)
    }

    @Test
    fun calc() {
        // TODO()
    }
}