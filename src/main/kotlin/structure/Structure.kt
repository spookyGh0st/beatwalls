package structure

import types.BwDouble
import types.BwInt
import types.bwDouble
import types.bwInt
import kotlin.random.Random

abstract class Structure {
    /**
     * This holds the state that get's shared to Expressions.
     * It can be used to transfer the progression in easing (0-1),
     * the count (numbers of elements created)
     * or a seeded Random Element
     */
    val structureState: StructureState = StructureState()

    /**
     * Beat the of the structure in the map.
     * This takes BPM changes into account
     */
    var beat: BwDouble = bwDouble(0)

    /**
     * A List of Interfaces that get applied to the Structure
     *
     */
    var interfaces = listOf<String>()

    /**
     * Repeats the Structure c times.
     * You can change variables supporting repeat by using the variable 'c' in your expressions
     *
     * beat: 10 + c
     */
    var repeat: BwInt = bwInt(0)

    abstract fun generate(): List<*>
}

/**
 * Each Structure owns one StructureState
 * It is the part the Structure shares with it's Expressions
 * Functions can access it's elements
 * It gets adjusted when looping through it's generated Elements
 */
data class StructureState(
    var progress: Double = 0.0,
    var count: Int = 0,
    var R: Random = Random
)

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
    override fun generate(): List<*> {
        return superStructure.generate() + structures.flatMap { it.generate() }
    }
}

