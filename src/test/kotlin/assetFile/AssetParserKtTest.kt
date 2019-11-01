package assetFile

import junit.framework.TestCase
import org.junit.Test
import structure.Define
import structure.Wall
import kotlin.test.assertNotEquals
import java.util.concurrent.TimeoutException
import org.junit.internal.runners.statements.FailOnTimeout
import org.junit.rules.Timeout
import org.junit.Rule



class AssetParserKtTest : TestCase() {

    fun testParseAsset() {}

    fun testParseStructures() {
        val mutList = mutableListOf(
            "Define" to "hallo",
            "beat" to "4.5",
            "10" to "Wall"

        )
        val list = parseStructures(mutList)
        assertTrue(list.first() is Define)
        list.first() as Define
        assertEquals(list.first().beat, 4.5)
        assertTrue(list[1] is Wall)
    }

    fun testFindStructure() {}

    fun testReadWallStructOptions() {}

    fun testFindProperty() {}

    fun testWallFillProperty() {
        val wall = Wall()
        wall.startTime = 0.0
        var property = findProperty(wall,"startTime")
        fillProperty(wall,property!! ,"1.0", listOf() )
        assertEquals(wall.startTime,1.0)
        assertNotEquals(wall.duration, 0.0)
        property = findProperty(wall, "amount")
        try {
            fillProperty(wall,property!!,"randomStuff", listOf())
        } catch (e:Exception ){
            return
        }
        throw Exception()
    }
    fun testDefineWallFillProperty() {
        val wall = Define()
        wall.structures = listOf()
        val property = findProperty(wall, "structures")
        fillProperty(wall,property!!, "wall", listOf())
        val nameProperty = findProperty(wall, "name")
        fillProperty(wall,nameProperty!!, "fineWall", listOf())
        assertFalse(wall.structures.isEmpty())
        assertEquals(wall.name, "fineWall")
        val propList =listOf(wall)
        val newWall = Define()
        val newProperty = findProperty(newWall, "structures")
        fillProperty(newWall, newProperty!!,"finewall,wall",propList)
        assertTrue(newWall.structures.first() is Define)
        val w = newWall.structures.first() as Define
        assertTrue(w.structures.first() is Wall)
        assertTrue(w.structures.first() == newWall.structures[1])
        assertFalse(w.structures.first() === newWall.structures[1])
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

