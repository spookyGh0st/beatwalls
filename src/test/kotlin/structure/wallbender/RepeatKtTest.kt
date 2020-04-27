package structure.wallbender

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import structure.RandomNoise
import structure.Wall
import structure.helperClasses.SpookyWall
import kotlin.random.Random

class RepeatKtTest {

    @Test
    fun repeatStaticWalls() {
        val ws = Wall()
        ws.repeat = 2
        ws.repeatWalls = 2
        ws.repeatAddStartTime = 1.0
        ws.repeatAddDuration = 1.0
        ws.repeatAddStartRow = 1.0
        ws.repeatAddWidth = 1.0
        ws.repeatAddStartHeight = 1.0
        ws.repeatAddHeight = 1.0
        ws.repeatAddZ = 0.0
        ws.repeatAddY = 0.0
        ws.repeatAddX = 0.0
        //ws.startRow = 0.0
        //ws.startHeight = 0.0
        //ws.duration = 0.0
        //ws.startTime = 0.0
        //ws.height = 0.0
        //ws.width = 0.0
        val wl = listOf(SpookyWall())
        val l1 = ws.repeatWalls(wl)
        val l2 = ws.repeatStruct(wl)
        val expected = listOf(SpookyWall(),
            SpookyWall(1,1,1,1,1,1))
        //todo fix after changed wall to p1
        //assertEquals(expected,l1)
        //assertEquals(expected,l2)
    }

    @Test
    fun repeatRandomWS() {
        val w = RandomNoise()
        w.repeatWalls = 2
        w.repeat = 2
        val wl = w.generateWalls()
        val l1 = w.repeatWalls(wl)
        val l2 = w.repeatStruct(wl)
        assertNotEquals(l1,l2)
        assertEquals(l1.size,l2.size)
        assertEquals(l1.subList(0,l1.size/2),l2.subList(0,l2.size/2))

    }
    @Test
    fun repeatRandomAddWS() {
        val w = Wall()
        w.repeatWalls = 2
        w.repeat = 2
        w.addDuration = { Random.nextDouble()}
        val wl = w.generateWalls()
        val l1 = w.repeatWalls(wl)
        val l2 = w.repeatStruct(wl)
        assertNotEquals(l1,l2)
        assertEquals(l1.size,l2.size)
        assertEquals(l1.subList(0,l1.size/2),l2.subList(0,l2.size/2))

    }
}