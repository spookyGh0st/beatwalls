package types

import interpreter.Beatwalls
import interpreter.parser.Parser
import interpreter.parser.TypeBuilder
import org.junit.Test

import org.junit.Assert.*
import structure.StructureState
import java.io.File

class NumbersKtTest {

    @Test
    fun `Test numbers only Expression`() {
        val ss = StructureState()
        val s = "5"
        val p = TypeBuilder(s, ss, mapOf())
        val e = p.buildBwInt()
        assertEquals(5, e!!.invoke())
    }

    @Test
    fun `Test Integer arithmetic Expression`() {
        val ss = StructureState()
        val table = listOf(
            Pair(10,"5+5"),
            Pair(5,"5+0.25"),
            Pair(0,"5-5"),
            Pair(25,"5*5"),
            Pair(3, "5/2"),
            Pair(3125, "5^5")
        )
        table.forEach{
            val p = TypeBuilder(it.second, ss, mapOf())
            val e = p.buildBwInt()
            assertEquals("Expression: ${it.second}",it.first,e!!.invoke())
        }
    }

    @Test
    fun `Test Double arithmetic Expression`() {
        val ss = StructureState()
        val table = listOf(
            Pair(10.0,"5+5"),
            Pair(5.25,"5+0.25"),
            Pair(0.0,"5-5"),
            Pair(25.0,"5*5"),
            Pair(2.5, "5/2"),
            Pair(3125.0, "5^5")
        )
        table.forEach{
            val p = TypeBuilder(it.second, ss, mapOf())
            val e = p.buildBwDouble()
            assertEquals(it.first,e!!.invoke(),0.0)
        }
    }

    @Test
    fun `Test linear easing`() {
        val ss = StructureState()
        val s = "linear(-5, 5)"
        val p = TypeBuilder(s, ss, mapOf())
        val e = p.buildBwInt()
        for (i in 0 until 10){
            ss.progress = i*0.1
            assertEquals(i-5, e!!.invoke())
        }
    }
}