package structures

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallStructureManagerTest {

    lateinit var myManager:WallStructureManager
    lateinit var list: ArrayList<CustomWallStructure>
    @Before
    fun setup(){
        myManager = WallStructureManager
        list = arrayListOf(
            CustomWallStructure("floor",false,
                arrayListOf(MyObstacle(1.0,0.0,0.0,0.0,2.0,0.0))
            ))
        myManager.loadManager(list)
    }

    @Test
    fun getTest1() {
        val actual = myManager.get(Parameters("floor"))
        val expected = list.first().getObstacleList(Parameters("test"))
        assertEquals(actual,expected)
    }

    @Test
    fun getTest2() {
        val actual = myManager.get(Parameters("floor"))
        val expected = list.first().getObstacleList(Parameters("test 2"))
        assertNotEquals(actual,expected)
    }

    @Test
    fun getTest3() {
        //I promise you wont find a worse way to test this class
        val actual = myManager.get(Parameters("floor 1 2 1"))
        assertEquals(actual.toString(), "[_obstacles(_time=0.0, _lineIndex=3000, _type=4001, _duration=1.0, _width=3000), _obstacles(_time=1.0, _lineIndex=3000, _type=4001, _duration=1.0, _width=3000), _obstacles(_time=3.0, _lineIndex=3000, _type=4001, _duration=1.0, _width=3000)]")
    }
}