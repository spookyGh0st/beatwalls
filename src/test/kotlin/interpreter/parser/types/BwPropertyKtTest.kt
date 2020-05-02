package compiler.parser.types

import interpreter.parser.Line
import interpreter.parser.WsFactory
import interpreter.parser.types.BwPropFactory
import interpreter.property.BwProperty
import org.junit.Test

import org.junit.Assert.*
import structure.Wall
import structure.WallStructure
import kotlin.reflect.jvm.isAccessible

class BwPropertyKtTest {

    private val n = WallStructure::a.name
    private val v  = 10
    private val l = Line(" $n = $v")
    private val bwPropFactory: BwPropFactory = {
        val a = it::a
        a.isAccessible = true
        println(a.isAccessible)
        a.getDelegate() as BwProperty }

    //small integration test, this function cant really be unit-tested
    @Test
    fun parseProperty() {
        val oh = WsFactory({ Wall() })
        interpreter.parser.types.parseProperty(l, oh)
        val actual =oh.create().a
        assertEquals(v,actual)
    }
}