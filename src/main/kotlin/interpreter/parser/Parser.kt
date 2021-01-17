package interpreter.parser
import interpreter.Beatwalls
import interpreter.BlockType
import interpreter.TokenBlock
import interpreter.TokenPair
import structure.Structure
import structure.baseStructures
import types.BwColor
import types.baseColors


typealias BwInterface   = (Structure) -> Unit
typealias StructFactory = () -> Structure

class Parser(val blocks: List<TokenBlock>, val bw: Beatwalls) {
    val interfaces: MutableMap<String, BwInterface> = baseInterfaces()
    val variables: MutableMap<String, Double> = mutableMapOf()
    val structFactories: MutableMap<String, StructFactory> = baseStructures.toMutableMap()
    val colors: MutableMap<String, BwColor> = baseColors()

    lateinit var currentTP: TokenPair
    lateinit var currentBlock: TokenBlock
    val structures = mutableListOf<Structure>()

    /**
     * shortens the typing of error Messages.
     * Make sure this only get's called when parsing a TokenBlock
     */
    fun errorBlock(msg: String) = bw.error(currentBlock.file, currentBlock.line, msg)

    /**
     * shortens the typing of error Messages.
     * Make sure this only get's called when parsing a Property
     */
    fun errorTP(msg: String) = bw.error(currentTP.file,currentTP.line, msg)

    fun parse(): List<Structure> {
        for (tb in blocks){
            currentBlock = tb
            parseTokenBlock()
        }
        return structures.toList()
    }

    fun parseTokenBlock() = when (currentBlock.type){
        BlockType.Options           -> parseOption()
        BlockType.Interface         -> parseInterface()
        BlockType.CustomStructure   -> parseCustomStructure()
        BlockType.Structure         -> parseStructure()
        BlockType.Color             -> parseColor()
        BlockType.Variable          -> parseVariable()
    }
}






