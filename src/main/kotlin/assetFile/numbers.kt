package assetFile

import beatwalls.errorExit
import net.objecthunter.exp4j.ExpressionBuilder
import structure.StructureState
import kotlin.math.roundToInt


typealias BwDouble = () -> Double
typealias BwInt = () -> Int
typealias BwNumber = () -> Number

internal fun bwNumber(s: String, ss: StructureState):BwNumber{
    val e = ExpressionBuilder(s).functions(allFunctions(ss)).build()
    if (!e.validate().isValid)
        errorExit { "The expression $s has errors:\n ${e.validate().errors}" }
    return { e.evaluate() }
}

internal fun bwDouble(s: String, ss: StructureState): BwDouble=
    { bwNumber(s, ss).invoke().toDouble() }

internal fun bwInt(s: String, ss: StructureState): BwInt=
    { bwNumber(s, ss).invoke().toDouble().roundToInt() }


internal fun bwDoubleOrNull(s: String, ss: StructureState): BwDouble? {
    if (s == "null") return null
    return { bwNumber(s, ss).invoke().toDouble() }
}

internal fun bwIntOrNull(s: String, ss: StructureState): BwInt? {
    if (s == "null") return null
    return { bwNumber(s, ss).invoke().toDouble().roundToInt() }
}
