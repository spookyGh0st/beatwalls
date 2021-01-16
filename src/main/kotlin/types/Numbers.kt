package types

import beatwalls.logError
import net.objecthunter.exp4j.ExpressionBuilder
import structure.StructureState
import kotlin.math.roundToInt

typealias BwDouble = () -> Double
typealias BwInt = () -> Int
typealias BwNumber = () -> Number

fun bwDouble(num: Number):BwDouble = { num.toDouble()}
fun bwInt(num: Number):BwInt = { num.toInt()}

const val keyRepeatCount: String = "c"
const val keyProgress: String = "p"
const val keyScale: String = "s"

internal fun bwNumber(s: String, ss: StructureState):BwNumber{
    val e = ExpressionBuilder(s)
        .functions(ss.functions)
        .variables(ss.variables.keys)
        .build()
    e.setVariables(ss.variables)
    if (!e.validate().isValid){
        logError("The expression $s has errors:\n ${e.validate().errors}")
        logError("Defaulting to 0.0")
        return { 0.0 }
    }
    return {
        e.setVariables(ss.variables)
        e.evaluate()
    }
}

internal fun bwDouble(s: String, ss: StructureState): BwDouble=
    { bwNumber(s, ss).invoke().toDouble() }

internal fun bwInt(s: String, ss: StructureState): BwInt=
    { bwNumber(s, ss).invoke().toDouble().roundToInt() }
