package structure.helperClasses

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class SingleColorTest {
    private val l = mutableListOf<SpookyWall>()
    @Before
    fun createWalls() {
        repeat(5) {
            l.add(SpookyWall(0.0,0.0,0.0,0.0,0.0,0.0))
        }
    }

    @Test
    fun colorWalls() {
        val expected = cyan
        SingleColor(expected).colorWalls(l)
        l.map { it.color }.forEach{ actual ->
            assertEquals(expected,actual)
        }
    }
}