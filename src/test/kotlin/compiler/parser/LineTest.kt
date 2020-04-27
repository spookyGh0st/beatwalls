package compiler.parser

import beatwalls.GlobalConfig
import org.junit.Test

import org.junit.Assert.*

class LineTest {

    @Test
    fun toLowercase() {
        val t= "Foo Bar"
        val actual = Line(t).toLowercase()
        val expected = Line(t.toLowerCase())
        assertEquals(expected,actual)
    }

    @Test
    fun replaceWithIncludes() {
    }

    @Test
    fun includes() {
        GlobalConfig.reload()
        @Suppress("UNUSED_VARIABLE") val l = Line("""
            include: testInclude.bw
        """.trimIndent())
        //only testable locally
        // todo find a better way
        //val ls  =l.replaceWithIncludes()
        //println(ls)
    }
}