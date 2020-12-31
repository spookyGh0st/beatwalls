package interpreter

import org.junit.Test

import org.junit.Assert.*
import java.io.File

class ScannerTest {
    private val tmpFile = File.createTempFile("foo","bar")
    private val bw = Beatwalls()

    val source = """
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
  name: myLine

myline:
  beat: 20
    """.trimIndent()

    val s = Scanner(source, bw, tmpFile)
    val blocks = s.scan()

    @Test
    fun `Test if all Blocks were scanned`() {
        assertEquals(6, blocks.size)
        blocks.forEach { assertEquals(1, it.properties.size) }
    }

    @Test
    fun `Test if all types were correctly scanned`(){
        assertEquals(BlockType.Options, blocks[0].type)
        assertEquals(BlockType.Structure, blocks[1].type)
        assertEquals(BlockType.Interface, blocks[2].type)
        assertEquals(BlockType.Interface, blocks[3].type)
        assertEquals(BlockType.CustomStructure, blocks[4].type)
        assertEquals(BlockType.Structure, blocks[5].type)
    }

    @Test
    fun `Test if all groups were correctly named`(){
        assertEquals("global",  blocks[0].name)
        assertEquals("line",    blocks[1].name)
        assertEquals("default", blocks[2].name)
        assertEquals("foo",     blocks[3].name)
        assertEquals("myline",  blocks[4].name)
        assertEquals("myline",  blocks[5].name)

    }
}