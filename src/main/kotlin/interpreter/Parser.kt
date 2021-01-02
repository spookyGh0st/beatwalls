package interpreter
import assetFile.*
import assetFile.bwDouble
import assetFile.bwInt
import beatwalls.errorExit
import structure.*
import structure.helperClasses.ColorMode
import structure.helperClasses.Point
import structure.helperClasses.RotationMode
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.typeOf


class Parser(val blocks: List<TokenBlock>, val bw: Beatwalls) {
    val interfaces: MutableMap<String, (Structure) -> Unit> = mutableMapOf("default" to {})
    val factories: MutableMap<String, () -> Structure> = (baseStructures<WallStructure>() + baseStructures<Lighstructure>()).toMutableMap()

    var currentBlock: TokenBlock = blocks.first()
    val structures = mutableListOf<Structure>()

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
    }

    fun parseOption(){
        currentBlock.properties.forEach {
            try {
                parseOptionProperty(it)
            } catch (e:Exception){
                bw.error(it.file, it.line, "Could not parse Option Parameter")
            }
        }
    }

    /**
     * An Interface is just a function that does something with a Structure
     * Currently it holds some properties, that it can applly to the Structure
     * The correct type get's checked at Runtime
     */
    fun parseInterface(){
        val p = currentBlock.properties
        val i = { s: Structure ->
            p.forEach { parseProperty(s, it) }
        }
        interfaces[currentBlock.name.toLowerCase()] = i
    }

    /**
     * A Custom Structure is a structure designed to hold other structures
     * This way you can play building blocks with them to build your own stuff
     */
    @Suppress("UNCHECKED_CAST")
    fun parseCustomStructure(){
        val p = currentBlock.properties
        if (p.isEmpty() || p[0].k.toLowerCase() != "structures") {
            error("The first property of a define Blocks must be the structures it inherents from")
            return
        }
        val inheritanceProperty = p[0]
        val properties= p.subList(1,p.size)
        val structures = inheritanceProperty.v.toLowerCase().replace(" ","").split(',').filter { it.isNotBlank() }

        val facts: MutableList<() -> Structure> = mutableListOf()
        for (s in structures){
            val fac = factories[s]
            if (fac == null) error("Structure $s does not exist")
            else facts.add(fac)
        }

        if (facts.isEmpty()){
            error("The define block must inherent from at least one other Wallstructure")
            return
        }

        val strcts = facts.subList(1, facts.size).map { it() }
        val name = currentBlock.name.toLowerCase()

        val f = {
            val inh = facts[0]()
            val ws = when {
                inh is WallStructure && strcts.all { it is WallStructure }  ->
                    CustomWsStructure(name,inh, strcts as List<WallStructure>) // this is not unsave you bodo compiler
                inh is Lighstructure && strcts.all { it is Lighstructure }  ->
                    CustomLsStructure(name,inh, strcts as List<Lighstructure>) // this is not unsave you bodo compiler
                else -> CustomStructure(name, inh, strcts)
            }
            parseStructureProperties(ws, properties)
            ws
        }
        factories[name] = f
    }

    /**
     * To generate the Structure we just have to find it and build it.
     * After generating and parsing it, we apply all interfaces plus the default Interface on it.
     */
    fun parseStructure(){
        val func = factories[currentBlock.name]
        if (func == null) { error("Structure ${currentBlock.name} does not exist"); return }
        val ws = func()
        parseStructureProperties(ws, currentBlock.properties)
        structures.add(ws)
    }

    fun parseStructureProperties(struct: Structure, properties: List<TokenPair>){
        for (tp in properties){
            parseProperty(struct, tp)
        }
        for (i in struct.interfaces + "default"){
            interfaces[i]?.invoke(struct)?: error("Interface $i does not exist")
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun parseProperty(ws: Structure, tp: TokenPair){
        val p: KProperty1<out Structure, *>? = property(ws, tp.k)
        if (p == null && ws is CustomStructInterface) return parseProperty(ws.superStructure,tp)
        if (p == null){ bw.error(tp.file, tp.line, "Property ${tp.k} does not exist"); return }

        val ss = ws.structureState
        val value: Any? = when (p.returnType) {
            typeOf<Boolean>() -> tp.v.toBoolean()
            typeOf<Int>() -> tp.v.toIntOrNull()
            typeOf<Double>()-> tp.v.toDoubleOrNull()
            typeOf<()->Double>() -> tp.v.toDoubleFunc()
            typeOf<String>() -> tp.v
            typeOf<List<String>>() -> tp.v.toLowerCase().replace(" ","").split(',')
            typeOf<Point>() -> tp.v.toPoint()
            typeOf<ColorMode>() -> tp.v.toColorMode()
            typeOf<RotationMode>() -> tp.v.toRotationMode()
            typeOf<BwDouble>()  -> bwDouble(tp.v,ss)
            typeOf<BwDouble?>() -> bwDoubleOrNull(tp.v,ss)
            typeOf<BwInt>()  -> bwInt(tp.v,ss)
            typeOf<BwInt?>() -> bwIntOrNull(tp.v,ss)
            else -> errorExit { "Unknown type: ${p.returnType}" }
        }
        writeProb(ws, p, value)
    }

    // From here once there shall be dragons
    // everything below this is complicated shit
    // I wrote it once, tested it and then forgot about it

    fun parseOptionProperty(tp: TokenPair){
        when(tp.k.toLowerCase()) {
            "characteristic" -> bw.options.Characteristic = tp.v
            "clearwalls" -> bw.options.clearWalls = tp.v.toLowerCase().toBoolean()
            "clearnotes" -> bw.options.clearNotes = tp.v.toLowerCase().toBoolean()
            "clearbombs" -> bw.options.clearWalls = tp.v.toLowerCase().toBoolean()
            "halfjumpduration" -> bw.options.halfJumpDuration = tp.v.toLowerCase().toDouble()
            "modtype" -> try {
                bw.options.modType = Options.ModType.valueOf(tp.v)
            } catch (e: IllegalArgumentException) {
                bw.error(tp.file, tp.line, "${tp.v} is not a supported Value. (only ME/NE")
            }
            "difficulty" -> try {
                bw.options.difficulty = Options.DifficultyType.valueOf(tp.v)
            } catch (e: IllegalArgumentException) {
                bw.error(tp.file, tp.line, "${tp.v} is not a supported Value. (only Easy, Normal, ....)")
            }
        }
    }

    // returns Factories of all available Structures
    inline fun<reified T: Structure> baseStructures(): HashMap<String, () -> T> {
        val l = T::class.recursiveWsClasses()
        val hm = hashMapOf<String, () -> T>()
        l.forEach {
            val n = it.simpleName?.toLowerCase() ?: ""
            hm[n] = { it.createInstance() }
        }
        return hm
    }
    fun<E:Any> KClass<E>.recursiveWsClasses(): List<KClass<out E>> {
        return if (this.isSealed)
            this::sealedSubclasses.get().flatMap { it.recursiveWsClasses() }
        else
            listOf(this)
    }

    private fun error(message: String){
        bw.error(currentBlock.file, currentBlock.line, message)
    }

    // wrapper of the property functions
    // has the define-logic of WallStructures
    private fun writeProb(struct: Structure, p: KProperty1<out Structure, *>, value: Any?) {
        when (p) {
            !is KMutableProperty<*> -> error("Property is is not mutable")
            else -> p.setter.call(struct, value)
        }
    }

    // gets the property of a Structure of a given name
    private fun property(struct: Structure, name: String): KProperty1<out Structure, *>? {
        val props = struct::class.memberProperties
        val p = props.find { it.name.equals(name, ignoreCase = true) }?: return null
        p.isAccessible = true
        return p
    }
}
