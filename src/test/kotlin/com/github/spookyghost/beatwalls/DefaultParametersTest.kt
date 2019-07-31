package com.github.spookyghost.beatwalls


import org.junit.Assert.*
import org.junit.Test

class DefaultParametersTest {

    @Test
    fun testEmptyParameters(){
        val string = "floor"
        val a = DefaultParameters(string)
        assertTrue(a.customParameters.isEmpty())
        assertEquals(1.0, a.scale,0.01)
        assertEquals(0.0, a.repeat,0.01)
        assertEquals(0.0, a.duration,0.01)
        assertEquals(0.0, a.wallHeight,0.01)
        assertEquals(0.0, a.wallStartHeight,0.01)
        assertEquals(0.0, a.startRow,0.01)
        assertEquals(0.0, a.width,0.01)
    }
    @Test
    fun testCustomParameters(){
        val string = "floor -- foo bar --"
        val a = DefaultParameters(string)
        assertEquals(arrayListOf("foo","bar"),a.customParameters)
        assertEquals(1.0, a.scale,0.01)
        assertEquals(0.0, a.repeat,0.01)
        assertEquals(0.0, a.duration,0.01)
        assertEquals(0.0, a.wallHeight,0.01)
        assertEquals(0.0, a.wallStartHeight,0.01)
        assertEquals(0.0, a.startRow,0.01)
        assertEquals(0.0, a.width,0.01)
    }

    @Test
    fun testRandomParameters(){
        val string = "floor -- foo bar -- 1.5 2.124 -13 521 -99.9 0"
        val a = DefaultParameters(string)
        assertEquals(arrayListOf("foo","bar"),a.customParameters)
        assertEquals(1.5, a.scale,0.01)
        assertEquals(2.124, a.repeat,0.01)
        assertEquals(-13.0, a.duration,0.01)
        assertEquals(521.0, a.wallHeight,0.01)
        assertEquals(-99.9, a.wallStartHeight,0.01)
        assertEquals(0.0, a.startRow,0.01)
        assertEquals(0.0, a.width,0.01)
    }
    @Test
    fun testGibberish(){
        val string = "floor fsjdka lks li l2 j432"
        val a = DefaultParameters(string)
        assertTrue(a.customParameters.isEmpty())
        assertEquals(1.0, a.scale,0.01)
        assertEquals(0.0, a.repeat,0.01)
        assertEquals(0.0, a.duration,0.01)
        assertEquals(0.0, a.wallHeight,0.01)
        assertEquals(0.0, a.wallStartHeight,0.01)
        assertEquals(0.0, a.startRow,0.01)
        assertEquals(0.0, a.width,0.01)
    }
    @Test
    fun testMemException(){
        try {
            val string = "floor -- fsjdka lks li l2 j432"
             DefaultParameters(string)
            fail()
        }catch (e:Exception){}
    }
    @Test
    fun testNameException(){
        try {
            val string = ""
            DefaultParameters(string)
            fail()
        }catch (e:Exception){}
    }
}