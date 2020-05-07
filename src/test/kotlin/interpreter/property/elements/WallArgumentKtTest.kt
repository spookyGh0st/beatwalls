package interpreter.property.elements

import junit.framework.TestCase
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Expression
import structure.helperClasses.SpookyWall
import java.util.*

class WallArgumentKtTest : TestCase() {

    fun testWallArguments() {
        var w = SpookyWall()
        val hm = wallArguments(w)
        val a = Expression("1 + wallx - 1")
        val tokens = a.copyOfInitialTokens
        tokens.filter { it.looksLike == "argument" }.forEach {
            a.addDefinitions(hm[it.tokenStr]!!)
        }
        println("start")
        for (i in 0..50000) {
            w = SpookyWall()
            wallArguments(w)
        }
        println("end")
    }

    fun testToDoubleOrZero() {
        assertEquals(10.0, 10.toDoubleOrZero())
        val m: Double? = 20.0
        assertEquals(20.0, m.toDoubleOrZero())
        assertEquals(0.0, "MyLittlePhony".toDoubleOrZero())

    }
}
