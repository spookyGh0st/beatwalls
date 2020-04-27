package compiler.parser

import compiler.parser.types.delOfPropName
import compiler.parser.types.propOfName
import compiler.property.BwProperty
import org.junit.Test

import org.junit.Assert.*
import structure.Wall
import structure.WallStructure
import kotlin.math.roundToInt

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
        wsf.operations.add{ it.delOfPropName(name)?.setExpr(expected) }
        assertNotSame(wsf.create(), wsf.create())
        assertEquals(expected.toDouble().roundToInt(), wsf.create().a)

    }
}




