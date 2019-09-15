package structures

import org.junit.Test

import org.junit.Assert.*

class WallTest {

    @Test
    fun to_obstacle() {
    }

    @Test
    fun mirror() {
        val  actual  = Wall(1.5, 2.0, 0.5, 1.0, 1.0, 0.2).mirror()
        val expected = Wall(-1.5, 2.0, -0.5, 1.0, 1.0, 0.2)
        assertEquals(actual.toString(),expected.toString())
    }



}