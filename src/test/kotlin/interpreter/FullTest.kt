package interpreter

import interpreter.parser.Parser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import structure.wallStructures.CustomWallStructure
import structure.wallStructures.Line
import structure.Structure
import structure.math.Vec3
import java.io.File
import kotlin.test.assertTrue


class FullTest {
    lateinit var blocks:List<TokenBlock>
    lateinit var structs:List<Structure>

    @Before
    fun runTests() {
        val uri = this::class.java.getResource("/map").toURI()
        val wd = File(uri)
        val mainFile = File(wd,"main.bw")
        val bw = Beatwalls(wd)

        val s = Scanner(mainFile.readText(), bw, mainFile)
        blocks = s.scan()

        val p = Parser(blocks, bw)
        structs = p.parse()

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
        val t = listOf<Pair<(Structure)-> Any, Any>>(
            {ws:Structure -> ws.beat()} to 10.0,
            {ws:Structure -> ws as CustomWallStructure; (ws.superStructure as Line).p1} to Vec3(20,0,8),
            {ws:Structure -> ws as CustomWallStructure; (ws.superStructure as Line).p1} to Vec3(30,0,8),
            {ws:Structure -> ws.beat()} to 30.0,
        )
        for ((i, element) in t.withIndex()){
            assertEquals(element.second, element.first(structs[i]))
        }
    }


}