package structure.helperClasses

import org.junit.Test

import org.junit.Assert.*
import structure.math.Point

class PointTest {

    @Test
    fun testMirrored() {
        val expected = Point(1.0,2.5,0.0)
        val actual = Point(2.0,2.0,0.0)
            .mirrored(Point(3.0,1.5,0.0))

        assertEquals(expected, actual)
    }

    @Test
    fun testMirroredNoZ() {
        val expected = Point(1.0,2.5,-20.0)
        val actual = Point(2.0,2.0,5.0)
            .mirroredNoZ(Point(3.0,1.5,-20.0))

        assertEquals(expected, actual)
    }
    @Test
    fun testToString() {
        val expected = "Point(x=2.0, y=2.0, z=5.0)"
        val actual = Point(2.0,2.0,5.0)
            .toString()
        assertEquals(expected, actual)
    }

    @Test
    fun testHashCode(){
        var expected = 2.0.hashCode()
        expected = 31 * expected + 2.0.hashCode()
        expected = 31 * expected + (-20.0).hashCode()
        val actual = Point(2,2,-20).hashCode()
        assertEquals(expected, actual)
    }

}