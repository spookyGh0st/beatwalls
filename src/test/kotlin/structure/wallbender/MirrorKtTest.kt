package structure.wallbender

import org.junit.Test

import org.junit.Assert.*
import structure.Interface
import structure.helperClasses.*
import structure.helperFunctions.reset

class MirrorKtTest {
    val e = Interface()
    private val l = listOf(SpookyWall(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, red))

    private fun w(startRow: Int =1, duration: Int =1, width: Int =1, height: Int =1, startHeight: Int =1, startTime: Int =1, color: Color =red) =
        SpookyWall(startRow,duration, width, height, startHeight, startTime,color)
    @Test
    fun mirror0() {
        class T(
            val code: Int,
            val expected: List<SpookyWall>
        )
        val tables = listOf(
            T(0, l),
            T(1, listOf(w(startRow = -1,width = -1))),
            T(2, l +listOf(w(startRow = -1,width = -1))),
            T(3, listOf(w(startHeight = 3,height = -1))),
            T(4, l +listOf(w(startHeight = 3,height = -1))),
            T(5, listOf(w(startRow = -1,width = -1,startHeight = 3,height = -1))),
            T(6, l +listOf(w(startRow = -1,width = -1,startHeight = 3,height = -1))),
            T(7, listOf(
                w(startRow = -1,width = -1),
                w(startHeight = 3,height = -1)
            )),
            T(8, l + listOf(
                w(startRow = -1,width = -1),
                w(startHeight = 3,height = -1),
                w(startRow = -1,width = -1,startHeight = 3,height = -1)
            )))
        for(t in tables) {
            e.mirror = t.code
            assertEquals(t.expected, e.mirror(l))
        }
        e.reset()
    }

    @Test
    fun mirrorX() {
        val actual = e.mirrorX(l)
        val expected = listOf(SpookyWall(-1.0, 1.0, -1.0, 1.0, 1.0, 1.0, red))
        assertEquals(expected, actual)
        e.reset()
    }

    @Test
    fun mirrorXWithMirrorX() {
        var actual = e.also { it.mirrorX = 1.0 }.mirrorX(l)
        var expected = listOf(SpookyWall(1.0, 1.0, -1.0, 1.0, 1.0, 1.0, red))
        assertEquals(expected, actual)
        actual = e.also { it.mirrorX = -1.0 }.mirrorX(l)
        expected = listOf(SpookyWall(-3.0, 1.0, -1.0, 1.0, 1.0, 1.0, red))
        assertEquals(expected, actual)
        e.reset()
    }
    @Test
    fun mirrorYWithMirrorY() {
        var actual = e.also { it.mirrorY = 1.0 }.mirrorY(l)
        var expected = listOf(SpookyWall(1.0, 1.0, 1.0, -1.0, 1.0, 1.0, red))
        assertEquals(expected, actual)
        actual = e.also { it.mirrorY = -1.0 }.mirrorY(l)
        expected = listOf(SpookyWall(1.0, 1.0, 1.0, -1.0, -3.0, 1.0, red))
        assertEquals(expected, actual)
        e.reset()
    }

    @Test
    fun mirrorY() {
        val actual = e.mirrorY(l)
        val expected = listOf(SpookyWall(1.0, 1.0, 1.0, -1.0, 3.0, 1.0, red))
        assertEquals(expected, actual)
        e.reset()
    }
}
