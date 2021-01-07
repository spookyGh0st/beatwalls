package assetFile

import org.junit.Test

import org.junit.Assert.*
import structure.helperClasses.*
import java.lang.NullPointerException
import kotlin.test.assertFailsWith

class FunctionParserKtTest {

    @Test
    fun toPoint() {
        assertEquals("1,2,0".toPoint(),Point(1,2,0))
        assertEquals("1,2".toPoint(),Point(1,2,0))
    }

    @Test
    fun toWallStructure() {
    }

    @Test
    fun toFunction() {
        assertEquals(BwFunction("random"),                      "random".toBwFunction())
        assertEquals(BwFunction("random"),                      " R a NDom".toBwFunction())
        assertEquals(BwFunction("r", listOf("foo","bar")),      "r (foo, bar)".toBwFunction() )
        assertEquals(BwFunction("r", listOf("foo","bar")),   "r(foo,bar".toBwFunction())
        assertFailsWith<NullPointerException> {  "".toBwFunction()}
    }

    @Test
    fun toDoubleFunc() {
        //tested in AssetParser
    }

    @Test
    fun toWallStructureList() {
    }
}