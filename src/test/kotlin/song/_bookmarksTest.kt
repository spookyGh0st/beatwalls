package song

import org.junit.Test

import org.junit.Assert.*
import java.lang.reflect.Parameter

class _bookmarksTest {

    @Test
    fun getEmptyCommandListTest() {
        val string = "fsdalk bw"
        val book = _bookmarks(1.0,string)
        val actual = book.getCommandList("/bw")
        assertTrue(actual.isEmpty())
    }
}