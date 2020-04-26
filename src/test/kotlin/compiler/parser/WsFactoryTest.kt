package compiler.parser

import org.junit.Test

import org.junit.Assert.*
import structure.Wall
import structure.WallStructure

class WsFactoryTest {

    private val testWsFact
    get(): WsFactory = WsFactory({Wall().also { it.beat = 0.0 }})

    @Test
    fun `create different instances`() {
        val wsf = testWsFact
        assertNotSame(wsf.create(), wsf.create())
    }

    @Test
    fun `test change beat`() {
        val wsf = testWsFact
        val expected = 20.0
        wsf.operations.add { it.beat = expected }
        assertNotSame(wsf.create(), wsf.create())
        assertEquals(expected, wsf.create().beat,0.0)
    }

    @Test
    fun `test change BwProperty`() {
        val wsf = testWsFact
        val expected = "20.0"
        val name = WallStructure::a.name.toLowerCase()
        assertNotSame(wsf.create().a, wsf.create().a)
        assertEquals(expected.toDouble(), wsf.create().a,0.0)
        TODO()
    }
}




