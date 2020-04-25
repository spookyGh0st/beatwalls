package compiler.parser

import org.junit.Test

import org.junit.Assert.*

class LineKtTest {

    @Test
    fun sBeforeEmpty() {
        val s = "foobar "
        val expected = ""
        val actual = s.sBefore(" ")
        assertEquals(expected,actual)
    }
    @Test
    fun sBefore() {
        val s = " foo bar "
        val expected = "foo"
        val actual = s.sBefore(" ")
        assertEquals(expected,actual)
    }

    @Test
    fun sAfter() {
    }

    @Test
    fun sBetween() {
        val s = " foo bar : "
        val expected = "bar"
        val actual = s.sBetween(" ",":")
        assertEquals(expected,actual)
    }

    @Test
    fun sBetweenStruct() {
        val s = " struct bar"
        val expected = "bar"
        val actual = s.sBetween("struct",":")
        assertEquals(expected,actual)
    }

    @Test
    fun sBetweenConst() {
        val s = " const bar = 10"
        val expected = "bar"
        val actual = s.sBetween("const","=")
        assertEquals(expected,actual)
    }

    @Test
    fun sBetweenNoEnd() {
        val s = " 10 foo "
        val expected = "foo"
        val actual = s.sBetween(" ",":")
        assertEquals(expected,actual)
    }


    @Test
    fun substringBetween() {
    }
}