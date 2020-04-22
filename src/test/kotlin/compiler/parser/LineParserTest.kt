package compiler.parser

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
10 wall: hyper 
  a += 5
  p1 += f(a),a,b
        """.trimIndent()
        //todo clean up
        val lp = LineParser()

        val l = t.lines().map{ Line(it,0,File("")) }
        lp.parseLines(l)
        println((lp.structList[0] as Wall).p1)
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

    @Test
    fun addProperty() {
        val ws = Wall()
        val i = Interface(listOf())
        val value = 10.0
        val line = Line("a = $value",10,File(""))
        compiler.parser.types.addProperty(ws, line)
        compiler.parser.types.addProperty(i, line)
        assertEquals(ws.a,value,0.0)
        assertEquals(i.lines, mutableSetOf(line))
        assert(kotlin.runCatching { compiler.parser.types.addProperty(Unit, line) }.isFailure)
    }

    @Test
    fun wallStructFromLine() {
        data class R(val l: compiler.parser.Line, val storedInterfaces: HashMap<String,Interface>,val f:(String)->WallStructure,val result: LastStructure)
        val l = Line()
        val i = Interface(listOf())
        var ls = R(
                Line("10 line: default",10, File("")),
                hashMapOf("default" to i),
                { if(it == "line") l else( Wall() ) },
                LastStructure(l, listOf(i),true)
            )
        var ds = DataSet(interfaces = ls.storedInterfaces)
            assertEquals(ls.result, wallStructFromLine(ls.l,ds,ls.f))
        ls = R(
            Line("10line: default",10, File("")),
            hashMapOf("d" to i),
            { if(it == "line") Wall() else l },
            LastStructure(l, listOf(),true)
        )
        ds = DataSet(interfaces = ls.storedInterfaces)
        assert(kotlin.runCatching { wallStructFromLine(ls.l,ds,ls.f) }.isFailure)

        ls = R(
            Line("10 wall: default",10, File("")),
            hashMapOf("d" to i),
            { if(it == "line") Wall() else l },
            LastStructure(l, listOf(),true)
        )
        ds = DataSet(interfaces = ls.storedInterfaces)
        assert(kotlin.runCatching { wallStructFromLine(ls.l,ds,ls.f) }.isFailure)
    }

    @Test
    fun isDouble() {
        val ls = listOf<Pair<CharSequence?,Boolean>>(
            null to false,
            "" to false,
            "foo" to false,
            "10foo" to false,
            "10" to true,
            "10.0" to true,
            "-20.30" to true,
            "20e2" to true
        )
        for(l in ls){
            assertEquals(l.second,isDouble(l.first))
        }

    }

    @Test
    fun baseStructuresTest() {
        val b =baseStructures()
        val ls = listOf<Triple<String, KClass<out WallStructure>,Boolean>>(
            Triple("steadycurve", SteadyCurve::class, true),
            Triple("SteadyCurve", SteadyCurve::class, false),
            Triple("Line", Line::class, false),
            Triple("line", Line::class, true),
            Triple("line", WallStructure::class, false),
            Triple("line", SteadyCurve::class, false)

        )
        for(l in ls){
            if(l.third){
                assertEquals(b[l.first],l.second)
            }else
                assertNotEquals(b[l.first],l.second)
        }
    }

    @Test
    fun getSyntaxStatus() {
        var f = Function("f(x)=x*2")
        assertEquals(f.syntaxStatus , f.checkSyntax())
        f = Function("f(x)=x*2*y")
        assertEquals(f.syntaxStatus,f.checkSyntax())
    }
}