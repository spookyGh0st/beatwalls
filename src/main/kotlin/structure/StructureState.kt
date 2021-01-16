package structure

import net.objecthunter.exp4j.function.Function
import types.baseFunctions
import types.keyProgress
import types.keyRepeatCount
import kotlin.random.Random

/**
 * Each Structure owns one StructureState
 * It is the part the Structure shares with it's Expressions
 * Functions can access it's elements
 * It gets adjusted when looping through it's generated Elements
 */
data class StructureState(
    var R: Random = Random,
    var variables: MutableMap<String,Double> = mutableMapOf(
        keyRepeatCount to 0.0,
        keyProgress to 0.0
    ),
){
    var progress: Double
        get() = variables[keyProgress]?: 0.0
        set(value) { variables[keyProgress] = value }
    var count: Double
        get() = variables[keyProgress]?: 0.0
        set(value) { variables[keyProgress] = value }
    var scale: Double
        get() = variables[keyProgress]?: 0.0
        set(value) { variables[keyProgress] = value }

    var functions: List<Function> = baseFunctions(this)
}