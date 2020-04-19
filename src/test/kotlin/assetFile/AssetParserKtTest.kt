package assetFile

import junit.framework.TestCase
import structure.*
import structure.helperClasses.Color
import structure.helperClasses.Point
import structure.helperClasses.SingleColor
import kotlin.random.Random
import kotlin.test.assertNotEquals


class AssetParserKtTest : TestCase() {

    fun testParseAsset() {}

    fun testParseStructures() {
        val mutList = mutableListOf(
            "0.0" to "default",
            "changeDuration" to "-3",
            "Define" to "hallo",
            "beat" to "4.5",
            "0.0" to "hallo",
            "10" to "Wall",
            "randomSeed" to "10",
            "0.0" to "default",
            "changeDuration" to "null"
        )
        val list = parseStructures(mutList)
        assertTrue(list.first() is Define)
        list.first() as Define
        assertEquals((list.first() as Define).structures.first().beat, 4.5)
        assertTrue(list[1] is Wall)
        list[1] as Wall
        val r = Random(10)
        repeat(10) {
            assertEquals(RandomSeed.nextDouble(), r.nextDouble())
        }
    }

    fun testFindStructure() {}

    fun testReadWallStructOptions() {}

    fun testFindProperty() {}

    fun testWallFillProperty() {
//        val wall = Wall()
//        wall. = 0.0
//        var property = findProperty(wall,"startTime")
//        fillProperty(property!!, "1.0", listOf(),wall)
//        assertEquals(wall.startTime,1.0)
//        assertNotEquals(wall.duration, 0.0)
//        property = findProperty(wall, "amount")
//        try {
//            fillProperty(property!!, "randomStuff", listOf(),wall)
//        } catch (e:Exception ){
//            return
//        }
//        throw Exception()
    }

    fun testRandomProperty() {
        val w = Wall()
        val p = findProperty(w,"changeDuration")

        fillProperty(p!!, "1.0", listOf(),w)
        val actual = w.changeDuration!!.invoke()
        assertEquals(actual,1.0)
        assertNotEquals(actual, 0.0)

        fillProperty(p, "random(0,1)", listOf(), w)
        assertNotEquals(w.changeDuration!!(), w.changeDuration!!())
        repeat(100) {
            assert(w.changeDuration!!.invoke() in 0.0..1.0)
        }
        fillProperty(p, "random(-2,-1)", listOf(), w)
        assertNotEquals(w.changeDuration!!.invoke(), w.changeDuration!!.invoke())
        repeat(100) {
            assert(w.changeDuration!!.invoke() in -2.0..-1.0)
        }
        RandomSeed = Random(10)
        val r = Random(10)
        fillProperty(p, "random(0,1)", listOf(), w)
        repeat(100) {
            assertEquals(w.changeDuration!!(), r.nextDouble(0.0, 1.0))
        }
    }

    fun testPointProperty() {
        val w = Curve()
        val p = findProperty(w,"p1")
        fillProperty(p!!, "1,2,3", listOf(),w)
        val actual = w.p1
        val expected = Point(1, 2, 3)
        assertEquals(actual, expected)
        assertNotEquals(actual, Point(0, 2, 3))
    }
    fun testColorProperty() {
        val w = Curve()
        val p = findProperty(w,"color")
        fillProperty(p!!, "Red", listOf(),w)
        val actual = w.color
        val expected = SingleColor(Color(java.awt.Color.RED))
        assertEquals(actual, expected)
        assertNotEquals(actual, SingleColor(Color(java.awt.Color.GREEN)))
    }

    fun testDefineWallFillProperty() {
        val wall = Define()
        wall.structures = listOf()
        val property = findProperty(wall, "structures")
        fillProperty(property!!, "wall", listOf(),wall)
        val nameProperty = findProperty(wall, "name")
        fillProperty(nameProperty!!, "fineWall", listOf(),wall)
        assertFalse(wall.structures.isEmpty())
        assertEquals(wall.name, "fineWall")
        val propList =listOf(wall)
        val newWall = Define()
        val newProperty = findProperty(newWall, "structures")
        fillProperty(newProperty!!, "finewall,wall", propList,newWall)
        assertTrue(newWall.structures.first() is Define)
        val w = newWall.structures.first() as Define
        val first = w.structures.first() as Define
        assertTrue(first.structures.first() is Wall)
    }

    fun testGetWallListType() {
       getWallListType()
        //just works (no idea how to test this)
    }

    fun testParseAssetString() {
        val str = """
            #  
            
            foo: bar
            bar:  foo  
        """.trimIndent()
        val expected = listOf("foo" to "bar", "bar" to "foo")
        val expected2 = listOf("bar" to "foo", "foo" to "bar")

        val actual = parseAssetString(str)
        assertEquals(expected, actual)
        assertNotEquals(expected2,actual)
    }

    fun testValue() {
        val expected = "test"
        val actual = Pair("hallo","test").value()
        assertEquals(expected, actual)
    }
    fun testKey() {
        val expected = "hallo"
        val actual = Pair("hallo","test").key()
        assertEquals(expected, actual)
    }
}

