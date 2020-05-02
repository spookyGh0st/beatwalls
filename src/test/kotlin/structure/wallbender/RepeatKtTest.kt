package structure.wallbender

import interpreter.property.specialProperties.BwDouble
import interpreter.property.specialProperties.Repeat
import org.junit.Test

import org.junit.Assert.*
import structure.RandomNoise
import structure.TestStructure
import structure.Wall
import kotlin.reflect.jvm.isAccessible

class RepeatKtTest {

    @Test
    fun repeatSing() {
        val ws = TestStructure()
        val r = Repeat("r",2)
        ws.repeatNeu.add(r)
        val tmp = ws::x
        tmp.isAccessible = true
        val t  = tmp.getDelegate() as BwDouble
        t.setExpr("r")
        val l = ws.generateBendAndRepeatWalls()
        val expected = listOf(0.0,1.0)
        assertEquals(expected,l.map { it.x })
    }
    @Test
    fun repeatMult() {
        val ws = TestStructure()
        val r = Repeat("r",2)
        val o = Repeat("o",2)
        val p = Repeat("p",2)
        ws.repeatNeu.add(r)
        ws.repeatNeu.add(o)
        ws.repeatNeu.add(p)
        val tmp = ws::x
        tmp.isAccessible = true
        val t  = tmp.getDelegate() as BwDouble
        t.setExpr("r+10*o+100*p")
        val l = ws.repeat { it.bendWalls(it.generateWalls()) }
        val expected = listOf(0.0,1.0,10.0,11.0,100.0,101.0,110.0,111.0)
        assertEquals(expected,l.map { it.x })
    }
    @Test
    fun repeatNot() {
        val ws = TestStructure()
        val tmp = ws::x
        tmp.isAccessible = true
        val t  = tmp.getDelegate() as BwDouble
        t.setExpr("10")
        val l = ws.repeat { it.bendWalls(it.generateWalls()) }
        val expected = listOf(10.0)
        assertEquals(expected,l.map { it.x })
    }
}