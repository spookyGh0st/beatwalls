package interpreter

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files.createTempDirectory


class ScannerTest {

    val mainSource = """
# some global options
modtype: me

line: # now comes my cool line
# and here is a comment
       
     beat: 10
      
default:
  x: 5 / 23+random(0,13) 

Interface     foo:
  x: 5 / 23+random(0,13) 
  
define myLine:
  p1: 10,0,0

myline:
  beat: 20
  
include: Start.bw
    """.trimIndent()

    val startSource = """
define myWall:
    amount: 50
    """.trimIndent()


    var blocks = mutableListOf<TokenBlock>()


    @Before
    fun prepareFile(){
        val wd = createTempDirectory(null).toFile()
        val mainFile = File(wd,"main.bw")
        val startFile  = File(wd,"start.bw")
        val bw = Beatwalls()
        bw.options.workingDir = wd
        val s = Scanner(mainSource, bw, mainFile)
        mainFile.createNewFile()
        mainFile.writeText(mainSource)
        startFile.createNewFile()
        startFile.writeText(startSource)
        blocks = s.scan()
    }

    @Test
    fun `Test if all Blocks were scanned`() {
        assertEquals(7, blocks.size)
        blocks.forEach { assertEquals(1, it.properties.size) }
    }

    @Test
    fun `Test if all types were correctly scanned`(){
        assertEquals(BlockType.Options,         blocks[0].type)
        assertEquals(BlockType.Structure, blocks[1].type)
        assertEquals(BlockType.Interface, blocks[2].type)
        assertEquals(BlockType.Interface, blocks[3].type)
        assertEquals(BlockType.CustomStructure, blocks[4].type)
        assertEquals(BlockType.Structure, blocks[5].type)
        assertEquals(BlockType.CustomStructure, blocks[6].type)
    }

    @Test
    fun `Test if all groups were correctly named`(){
        assertEquals("global",  blocks[0].name)
        assertEquals("line",    blocks[1].name)
        assertEquals("default", blocks[2].name)
        assertEquals("foo",     blocks[3].name)
        assertEquals("myline",  blocks[4].name)
        assertEquals("myline",  blocks[5].name)
        assertEquals("mywall",  blocks[6].name)
    }
}