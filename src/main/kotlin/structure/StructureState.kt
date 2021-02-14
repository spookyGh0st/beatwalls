package structure

import net.objecthunter.exp4j.function.Function
import types.baseFunctions
import kotlin.random.Random

open class ExpressionState(
    var variables: MutableMap<String, Double> = mutableMapOf(),
    var functions: MutableList<Function> = mutableListOf(),
    var rand: Random = Random
)

/**
 * Each Structure owns one StructureState
 * It is the part the Structure shares with it's Expressions
 * Functions can access it's elements
 * It gets adjusted when looping through it's generated Elements
 */
class StructureState: ExpressionState(){
    private val keyRepeatCount: String = "r"
    private val keyProgress: String = "p"
    private val keyDuration: String = "d"

    init {
        variables[keyRepeatCount] = 0.0
        variables[keyProgress] = 0.0
        variables[keyDuration] = 1.0
        functions.addAll(baseFunctions(this))
    }

    var progress: Double
        get() = variables[keyProgress]?: 0.0
        set(value) { variables[keyProgress] = value }
    var count: Double
        get() = variables[keyRepeatCount]?: 0.0
        set(value) { variables[keyRepeatCount] = value }
    var duration: Double
        get() = variables[keyDuration]?: 1.0
        set(value) { variables[keyDuration] = value }
}