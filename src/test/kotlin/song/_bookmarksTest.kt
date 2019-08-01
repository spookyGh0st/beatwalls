package song

import org.junit.Test

import org.junit.Assert.*
import structures.Parameters
import java.lang.reflect.Parameter

class _bookmarksTest {

    @Test
    fun getEmptyCommandListTest() {
        val string = "fsdalk bw"
        val book = _bookmarks(1.0,string)
        val actual = book.getCommandList("/bw")
        assertTrue(actual.isEmpty())
    }
    @Test
    fun getEmptyFullCommandListTest() {
        val string = "fsda /bw test 2 -3.5 1/bw hallo 2 -3.5 1"
        val book = _bookmarks(1.0,string)
        val actual = book.getCommandList("/bw")
        val expected = arrayListOf(Parameters("test 2 -3.5 1"),Parameters("hallo 2 -3.5 1"))
        for(i in actual.indices){
            assertEquals(actual[i].name,expected[i].name)
            assertEquals(actual[i].hashCode(),expected[i].hashCode())
        }
    }
}


