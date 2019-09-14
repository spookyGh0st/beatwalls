package structures

import org.junit.Test

import org.junit.Assert.*

class WallTest {

    @Test
    fun to_obstacle() {
    }

    @Test
    fun mirror() {
        val  actual  = Wall(2.0,1.0,1.0,1.5,0.5,0.2).mirror()
        val expected = Wall(2.0,1.0,1.0,-1.5,-0.5,0.2)
        assertEquals(actual.toString(),expected.toString())
    }



}