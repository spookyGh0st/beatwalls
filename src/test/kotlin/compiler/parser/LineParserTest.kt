package compiler.parser

import compiler.parser.types.BwInterface
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.mariuszgromada.math.mxparser.Function
import structure.Line
import structure.SteadyCurve
import structure.Wall
import structure.WallStructure
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class LineParserTest {

    @Test
    fun getStructList() {
    }

    @Test
    fun getLastStructure() {
    }

    @Test
    fun setLastStructure() {
    }

    @Test
    fun getBaseStructures() {
    }

    @Test
    fun getCustomStructures() {
    }

    @Test
    fun getInterfaces() {
    }

    @Test
    fun getConstantList() {
    }

    @Test
    fun getFunctionList() {
    }

    @Test
    fun getWallStructurePropertyNames() {
    }

    @Test
    fun parseLine() {
        val t = """
interface hyper
  a=10
const b = 20
fun f(x) = x*x
struct w1: wall
  a += 5
  p1 += f(a),a,b
10 w1
20 w1
  a += 10
        """.trimIndent()
        //todo clean up
        val lp = LineParser()

        val l = t.lines().map{ Line(it,0,File("")) }
        lp.parseLines(l)
        println((lp.structList[0]).a)
        println((lp.structList[1]).a)
        println()
    }

    @Test
    fun isConstant() {
        val l = Wall()
        val ws = l::class.memberProperties

        println()
    }

    @Test
    fun defineConstant() {
    }

    @Test
    fun isFunction() {
    }

    @Test
    fun defineFunction() {
    }

    @Test
    fun isDefineCustomStruct() {
    }

    @Test
    fun defineCustomStruct() {
    }

    @Test
    fun isInterface() {
    }

    @Test
    fun defineInterface() {
    }

    @Test
    fun isProperty() {
    }

    @Test
    fun addPropertytoWs() {
    }

    @Test
    fun isDefaultStructure() {
    }

    @Test
    fun addDefaultStruct() {
    }

    @Test
    fun isCustomStructure() {
    }

    @Test
    fun addStoredStruct() {
    }

    @Test
    fun addLastStruct() {
    }

    @Test
    fun isWallstructInHashmap() {
    }

    @Test
    fun wsNameOfLine() {
    }

    @Test
    fun wsBeatOfLine() {
    }

    @Test
    fun interfacesOfLine() {
    }

    @Test
    fun propNameOfLine() {
    }

    @Test
    fun propValueOfLine() {
    }

    @Test
    fun assignOfLine() {
    }

    @Test
    fun assignProperty() {
    }
}