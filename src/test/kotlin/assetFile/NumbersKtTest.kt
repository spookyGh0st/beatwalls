package assetFile

import org.junit.Test

import org.junit.Assert.*
import structure.StructureState

class NumbersKtTest {

    @Test
    fun `Test numbers only Expression`() {
        val ss = StructureState()
        val s = "5"
        val e = bwNumber(s,ss)
        assertEquals(5,e.invoke().toInt())
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
            val e = bwInt(it.second, ss)
            assertEquals("Expression: ${it.second}",it.first,e.invoke())
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
            val e = bwDouble(it.second, ss)
            assertEquals(it.first,e.invoke(),0.0)
        }
    }

    @Test
    fun `Test linear easing`() {
        val ss = StructureState()
        val s = "linear(-5, 5)"
        val e = bwInt(s,ss)
        for (i in 0 until 10){
            ss.progress = i*0.1
            assertEquals(i-5,e.invoke().toInt())
        }
    }

    @Test
    fun `Test nullable Number`() {
        val ss = StructureState()
        val s = "null"
        val nullI = bwIntOrNull(s,ss)
        val nullD = bwDoubleOrNull(s,ss)
        assertEquals(null, nullI)
        assertEquals(null, nullD)
    }
}