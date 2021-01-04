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
    fun toColorMode() {
        val red = Color(java.awt.Color.RED)
        val green = Color(java.awt.Color.GREEN)
        val blue = Color(java.awt.Color.BLUE)
        val cyan = Color(java.awt.Color.CYAN)
        val tests: List<Pair<ColorMode, String>> = listOf(
            SingleColor(red) to "red",
            SingleColor(green) to "green",
            SingleColor(red) to " R eD ",
            SingleColor(red) to "255,0,0",
            SingleColor(Color(15,12,10)) to "15,12,10",
            Gradient(cyan,blue) to "gradient(cyan,Blue)",
            Gradient(Color(12,10,10),red) to "gradient((12,10,10),red)",
            Gradient(Color(12,10,10),red) to "gradient(12,10,10,red)",
            Gradient(red,Color(12,10,10)) to "gradient(red,(12,10,10))",
            Gradient(red,Color(12,10,10)) to "gradient(red,12,10,10)",
            Rainbow() to "rainBow",
            Rainbow(1.5) to "rainbow(1.5)",
            Rainbow(2.0) to "rainbow(2)",
            Flash(listOf(red)) to "flash(red)",
            Flash(listOf(red,green)) to "flash(red,green)",
            Flash(listOf(red,Color(15,12,2))) to "flash(red,15,12,2)",
            Flash(listOf(Color(120,12,13),Color(15,12,2))) to "flash(120,12,13,15,12,2)",
            NoColor to "NoColor",
            NoColor to "null")


        tests.forEach{
        }
    }

    @Test
    fun toWallStructureList() {
    }
}