package assetFile

import junit.framework.TestCase
import structure.Define
import structure.Wall
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
            "10" to "Wall"

        )
        val list = parseStructures(mutList)
        assertTrue(list.first() is Define)
        list.first() as Define
        assertEquals((list.first() as Define).structures.first().beat, 4.5)
        assertTrue(list[1] is Wall)
        list[1] as Wall
        assertEquals(list[1].changeDuration, -3.0)
    }

    fun testFindStructure() {}

    fun testReadWallStructOptions() {}

    fun testFindProperty() {}

    fun testWallFillProperty() {
        val wall = Wall()
        wall.startTime = 0.0
        var property = findProperty(wall,"startTime")
        fillProperty(property!!, "1.0", listOf(),wall)
        assertEquals(wall.startTime,1.0)
        assertNotEquals(wall.duration, 0.0)
        property = findProperty(wall, "amount")
        try {
            fillProperty(property!!, "randomStuff", listOf(),wall)
        } catch (e:Exception ){
            return
        }
        throw Exception()
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

