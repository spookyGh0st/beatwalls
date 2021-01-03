package interpreter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import structure.CustomWallStructure
import structure.Line
import structure.Structure
import structure.helperClasses.Point
import java.io.File


class FullTest {
    lateinit var blocks:List<TokenBlock>
    lateinit var structs:List<Structure>

    @Before
    fun runTests() {
        val uri = this::class.java.getResource("/map").toURI()
        val wd = File(uri)
        val mainFile = File(wd,"main.bw")
        val bw = Beatwalls()
        bw.options.workingDir = wd

        val s = Scanner(mainFile.readText(), bw, mainFile)
        blocks = s.scan()

        val p = Parser(blocks, bw)
        structs = p.parse()

        assertFalse("The run should not have any errors", bw.hadError)
    }

    @Test
    fun `Test if all Blocks were scanned`() {
        assertEquals(11, blocks.size)
        blocks.forEach {
            val expect = 2
            val act = it.properties.size
            assertEquals("[${it.file.name} line: ${it.line}] name: ${it.name} should have 2 properties",expect, act)
        }
    }

    @Test
    fun `Test if all types were correctly scanned`(){
        val types = listOf(
            BlockType.Options,
            BlockType.Structure,
            BlockType.Interface,
            BlockType.Interface,
            BlockType.CustomStructure,
            BlockType.Structure,
            BlockType.Structure,
            BlockType.CustomStructure,
            BlockType.CustomStructure,
            BlockType.CustomStructure,
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
            "default",
            "foo",
            "introline",
            "introline",
            "introline",
            "_start1",
            "_start2",
            "start",
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
            {ws:Structure -> ws as CustomWallStructure; (ws.superStructure as Line).p2} to Point(20,0,8),
            {ws:Structure -> ws as CustomWallStructure; (ws.superStructure as Line).p2} to Point(30,0,8),
            {ws:Structure -> ws.beat()} to 30.0,
        )
        for ((i, element) in t.withIndex()){
            assertEquals(element.second, element.first(structs[i]))
        }
    }


}