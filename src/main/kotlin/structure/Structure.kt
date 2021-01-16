package structure

import interpreter.parser.StructFactory
import structure.bwElements.BwElement
import types.*
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.jvm.jvmName

abstract class Structure {
    /**
     * This holds the state that get's shared to Expressions.
     * It can be used to transfer the progression in easing (0-1),
     * the count (numbers of elements created)
     * or a seeded Random Element
     */
    internal val structureState: StructureState = StructureState()

    /**
     * A List of Interfaces that get applied to the Structure
     */
    internal var interfaces = listOf<String>()

    /**
     * Beat the of the structure in the map.
     * This takes BPM changes into account
     */
    var beat: BwDouble = bwDouble(0)

    /**
     * Repeats the Structure c times.
     * You can change variables supporting repeat by using the variable 'c' in your expressions
     *
     * beat: 10 + c
     */
    var repeat: BwInt = bwInt(0)

    protected abstract fun createElements(): List<BwElement>

    protected fun setProgress(p: Double){
        structureState.progress = p
    }

    internal fun run(): List<BwElement> {
        val l = mutableListOf<BwElement>()

        val n = repeat()
        for (count in 0..n){
            structureState.count = count.toDouble()
            val elements = createElements()
            elements.forEach { it.beat += beat() }
            l.addAll(elements)
        }
        return l.toList()
    }
}


fun baseStructs(vararg structs: KClass<out Structure>): Map<String, StructFactory> {
    val m = mutableMapOf<String,StructFactory>()
    structs.forEach {
        val name = (it.simpleName?: it.jvmName).toLowerCase()
        val fact = { it.createInstance() }
        m[name] = fact
    }
    return m.toMap()
}

/**
 * Custom Structures must inherent from the specific Structuretypes (WS/LS) directly.
 * This allows them to hold the specific properties
 */
interface CustomStructInterface{
    val name: String
    val superStructure: Structure
    val structures: List<Structure>
}

/**
 * The Default Custom Structure can hold all types, but only has limited Structure Attributes.
 * This get's used when the Types of the superStructures and structures are different
 * todo This should be solved with Types, check custom Section in the Parser.
 */
class CustomStructure(
    override val name: String,
    override val superStructure: Structure,
    override val structures: List<Structure>
): Structure(), CustomStructInterface{
    override fun createElements(): List<BwElement> =
        superStructure.run() + structures.flatMap { it.run() }
}

