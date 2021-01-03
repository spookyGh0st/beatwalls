package structure.wallbender

import assetFile.bwDouble
import assetFile.keyRepeatCount
import org.junit.Test

import org.junit.Assert.*
import structure.RandomNoise
import structure.Wall
import structure.bwInt
import structure.helperClasses.SpookyWall
import kotlin.random.Random

class RepeatKtTest {

    @Test
    fun repeatStaticWalls() {
        val ws = Wall()
        ws.duration = 0.0
        ws.repeat = bwInt(2)
        ws.addX = bwDouble(keyRepeatCount,ws.structureState)
        ws.addY = bwDouble(keyRepeatCount, ws.structureState)
        ws.addDuration = bwDouble(keyRepeatCount, ws.structureState)
        ws.addZ = bwDouble(keyRepeatCount, ws.structureState)
        ws.addHeight = bwDouble(keyRepeatCount, ws.structureState)
        ws.addWidth = bwDouble(keyRepeatCount, ws.structureState)
        val wl = listOf(SpookyWall())
        val l2 = ws.repeatStruct(wl)
        val expected = listOf(SpookyWall(),
            SpookyWall(1,1,1,1,1,1))
        assertEquals(expected,l2)
    }

    @Test
    fun repeatRandomWS() {
        val w = RandomNoise()
        w.repeat = bwInt(2)
        val wl = w.generate()
        val l2 = w.repeatStruct(wl)
        //todo
    }
    @Test
    fun repeatRandomAddWS() {
        val w = Wall()
        w.repeat = bwInt(2)
        w.addDuration = { Random.nextDouble()}
        val wl = w.generate()
        val l2 = w.repeatStruct(wl)
        //todo

    }
}