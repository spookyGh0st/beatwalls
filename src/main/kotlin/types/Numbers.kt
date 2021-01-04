package types

import beatwalls.errorExit
import net.objecthunter.exp4j.ExpressionBuilder
import structure.StructureState
import kotlin.math.roundToInt

typealias BwDouble = () -> Double
typealias BwInt = () -> Int
typealias BwNumber = () -> Number

fun bwDouble(num: Number):BwDouble = { num.toDouble()}
fun bwInt(num: Number):BwInt = { num.toInt()}

const val keyRepeatCount: String = "count"

internal fun bwNumber(s: String, ss: StructureState):BwNumber{
    val e = ExpressionBuilder(s)
        .functions(allFunctions(ss))
        .variable(keyRepeatCount)
        .variables(ss.variables.keys)
        .build()
    e.setVariable(keyRepeatCount, 0.0)
    e.setVariables(ss.variables)
    if (!e.validate().isValid)
        errorExit { "The expression $s has errors:\n ${e.validate().errors}" }
    return {
        e.setVariable(keyRepeatCount, ss.count.toDouble())
        e.evaluate()
    }
}

internal fun bwDouble(s: String, ss: StructureState): BwDouble=
    { bwNumber(s, ss).invoke().toDouble() }

internal fun bwInt(s: String, ss: StructureState): BwInt=
    { bwNumber(s, ss).invoke().toDouble().roundToInt() }
