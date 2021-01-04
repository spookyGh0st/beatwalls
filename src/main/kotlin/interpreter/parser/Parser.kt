package interpreter.parser
import interpreter.Beatwalls
import interpreter.BlockType
import interpreter.TokenBlock
import interpreter.TokenPair
import structure.*
import types.BwColor
import types.baseColors
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance


typealias BwInterface   = (Structure) -> Unit
typealias StructFactory = () -> Structure

class Parser(val blocks: List<TokenBlock>, val bw: Beatwalls) {
    val interfaces: MutableMap<String, BwInterface> = mutableMapOf("default" to {})
    val structFactories: MutableMap<String, StructFactory> = baseStructs()
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
    }


    fun baseStructs() = (
            sealedClassFacts<WallStructure>() + sealedClassFacts<LightStructure>() + sealedClassFacts<NoteStructure>()
            ).toMutableMap()


    // returns Factories of all available Structures
    private inline fun<reified T: Structure> sealedClassFacts(): HashMap<String, () -> T> {
        val l = T::class.recursiveWsClasses()
        val hm = hashMapOf<String, () -> T>()
        l.forEach {
            val n = it.simpleName?.toLowerCase() ?: ""
            hm[n] = { it.createInstance() }
        }
        return hm
    }
    private fun<E:Any> KClass<E>.recursiveWsClasses(): List<KClass<out E>> {
        return if (this.isSealed)
            this::sealedSubclasses.get().flatMap { it.recursiveWsClasses() }
        else
            listOf(this)
    }
}






