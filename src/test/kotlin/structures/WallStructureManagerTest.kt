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
    }
}