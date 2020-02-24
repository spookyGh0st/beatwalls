package structure.wallbender

import org.junit.Test

import org.junit.Assert.*
import structure.EmptyWallStructure
import structure.helperClasses.*

class MirrorKtTest {
    private val l =listOf(SpookyWall(1.0,1.0,1.0,1.0,1.0,1.0, red))

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
        for(t in tables){
            EmptyWallStructure.mirror=t.code
            assertEquals(t.expected, EmptyWallStructure.mirror(l))
        }
    }

    @Test
    fun mirrorX() {
        val actual = l.mirrorX()
        val expected = listOf(SpookyWall(-1.0,1.0,-1.0,1.0,1.0,1.0,red))
        assertEquals(expected, actual)
    }

    @Test
    fun mirrorY() {
        val actual = l.mirrorY()
        val expected = listOf(SpookyWall(1.0,1.0,1.0,-1.0,3.0,1.0,red))
        assertEquals(expected, actual)
    }
}
