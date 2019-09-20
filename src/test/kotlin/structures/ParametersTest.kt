package structures

import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class ParametersTest {

    private val defaultName = "test"
    private val secondRP = Parameters(
        name = defaultName,
        customParameters = arrayListOf("c","d","e"),
        scale = 11.0,
        repeatCount = 12,
        repeatGap = 13.0,
        startRow = 14.0,
        duration = 15.0,
        width = 16.0,
        wallHeight = 17.0,
        wallStartHeight = 18.0,
        startTime = 19.0
    )
    private val expected = Parameters(
        name = defaultName,
        customParameters = arrayListOf("a","b"),
        scale = 1.0,
        repeatCount = 2,
        repeatGap = 3.0,
        startRow = 4.0,
        duration = 5.0,
        width = 6.0,
        wallHeight = 7.0,
        wallStartHeight = 8.0,
        startTime = 9.0,
        innerParameter = secondRP
    )
    private val defaultParameters = Parameters(name=defaultName)
    @Test
    fun testEmptyParameters(){
        val string = defaultName
        val a = Parameters(commandText=string)
        assertEquals(a,defaultParameters)
    }
    @Test
    fun testInnerParameters(){
        val string = expected.toString()
        val actual = Parameters(commandText = string)
        assertEquals(actual, expected )
    }

    @Test
    fun testCustomParameters(){
        val string = "$defaultName -- foo bar --"
        val a = Parameters(string)
        assertEquals(arrayListOf("foo","bar"),a.customParameters)
    }

    @Test
    fun testRandomParameters(){
        val actual = Parameters(commandText = "$expected")
        assertEquals(expected.toString(),actual.toString())
    }
    @Test
    fun testGibberish(){
        val string = "$defaultName fsjdka lks li l2 j432"
        val a = Parameters(string)
        assertEquals(defaultParameters,a)
    }
    @Test
    fun testNameException(){
        try {
            val string = ""
            Parameters(string)
            fail()
        }catch (e:Exception){}
    }
    @Test
    fun testSimple(){
        val string = "test 1 5 0.2"
        val a = Parameters(string)
        assertTrue(a.customParameters.isEmpty())
        assertEquals(1.0, a.scale,0.01)
        assertEquals(5, a.repeatCount)
        assertEquals(0.2,a.repeatGap,0.01)
        assertEquals(0.0, a.duration,0.01)
        assertEquals(0.0, a.wallHeight,0.01)
        assertEquals(0.0, a.wallStartHeight,0.01)
        assertEquals(0.0, a.startRow,0.01)
        assertEquals(0.0, a.width,0.01)
    }
}