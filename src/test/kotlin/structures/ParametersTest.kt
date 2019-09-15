package structures

import org.junit.Assert.*
import org.junit.Test
import structures.Parameters
import kotlin.random.Random

class ParametersTest {

    private val defaultName = "test"
    private val rP = Parameters(
        name = defaultName,
        customParameters = arrayListOf("a","b"),
        scale = Random.nextDouble(-50.0, 50.0),
        repeatCount = Random.nextInt(),
        repeatGap = Random.nextDouble(-50.0, 50.0),
        startRow = Random.nextDouble(-50.0, 50.0),
        duration = Random.nextDouble(-50.0, 50.0),
        width = Random.nextDouble(-50.0, 50.0),
        wallHeight = Random.nextDouble(-50.0, 50.0),
        wallStartHeight = Random.nextDouble(-50.0, 50.0),
        startTime = Random.nextDouble(-50.0, 50.0)
    )
    private val defaultParameters = Parameters(name=defaultName)
    @Test
    fun testEmptyParameters(){
        val string = defaultName
        val a = Parameters(commandText=string)
        assertEquals(a,defaultParameters)
    }

    @Test
    fun testCustomParameters(){
        val string = "$defaultName -- foo bar --"
        val a = Parameters(string)
        assertEquals(arrayListOf("foo","bar"),a.customParameters)
    }

    @Test
    fun testRandomParameters(){
        val actual = Parameters(commandText = "$rP")
        assertEquals(rP,actual)
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