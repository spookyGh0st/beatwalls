package interpreter

import interpreter.parser.Parser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import structure.wallStructures.CustomWallStructure
import structure.wallStructures.Line
import structure.Structure
import structure.bwElements.BwElement
import structure.math.Vec2
import java.io.File
import kotlin.test.assertTrue


class FullTest {
    lateinit var blocks:List<TokenBlock>
    lateinit var structs:List<Structure>
    lateinit var elements:List<BwElement>

    @Before
    fun runTests() {
        val uri = File("src/main/resources/map")
        val bw = Beatwalls(uri)

        val s = Scanner(bw.mainFile.readText(), bw, bw.mainFile)
        blocks = s.scan()

        val p = Parser(blocks, bw)
        structs = p.parse()

        val ev = Evaluator(structs,bw)
        elements = ev.evaluate()

        val tr = Translator(elements,bw)
        tr.translate()

        assertFalse("The run should not have any errors", bw.hadError)
    }

    @Test
    fun `Test if all Blocks were scanned`() {
        assertEquals(13, blocks.size)
        blocks.forEach {
            val expect = 1
            val act = it.properties.size
            assertTrue(act >= expect,"${it.name} on ${it.file.name} on line ${it.line} does not have at least 1 Property")
        }
    }

    @Test
    fun `Test if all types were correctly scanned`(){
        val types = listOf(
            BlockType.Options,
            BlockType.Structure,
            BlockType.Color,
            BlockType.Interface,
            BlockType.Interface,
            BlockType.CustomStructure,
            BlockType.Structure,
            BlockType.Structure,
            BlockType.CustomStructure,
            BlockType.CustomStructure,
            BlockType.CustomStructure,
            BlockType.Variable,
            BlockType.Structure,
        )
        for ((i, t) in types.withIndex()){
            assertEquals(t, blocks[i].type)
        }
    }

    @Test
    fun `Test if all groups were correctly named`(){
        val names = listOf(
            "global",
            "line",
            "mycolor",
            "default",
            "foo",
            "introline",
            "introline",
            "introline",
            "_start1",
            "_start2",
            "start",
            "bar",
            "start",
        )

        for ((i, s) in names.withIndex()){
            assertEquals(s, blocks[i].name)
        }
    }

    @Test
    fun `Test if all Structures were parsed`(){
        assertEquals(4, structs.size)
    }

    @Test
    fun `Test if the Properties have been set correctly`(){
        val t = listOf(
            {ws:Structure -> ws.beat()} to 10.0,
            {ws:Structure -> ws as CustomWallStructure; (ws.superStructure as Line).p1} to Vec2(20,0),
            {ws:Structure -> ws as CustomWallStructure; (ws.superStructure as Line).p1} to Vec2(30,0),
            {ws:Structure -> ws.beat()} to 30.0,
        )
        for ((i, element) in t.withIndex()){
            assertEquals(element.second, element.first(structs[i]))
        }
    }

    @Test
    fun `Test if the correct amount of elements were created`(){
        assertEquals(20, elements.size)
    }
}