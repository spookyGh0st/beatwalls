package types

import beatwalls.logError
import interpreter.parser.Parser
import net.objecthunter.exp4j.ExpressionBuilder
import structure.StructureState
import kotlin.math.roundToInt

typealias BwDouble = () -> Double
typealias BwInt = () -> Int
typealias BwNumber = () -> Number

fun bwDouble(num: Number):BwDouble = { num.toDouble()}
fun bwInt(num: Number):BwInt = { num.toInt()}


internal fun Parser.bwNumber(s: String, ss: StructureState): BwNumber? {
    val e = try {
        ExpressionBuilder(s.toLowerCase())
            .functions(ss.functions)
            .variables(ss.variables.keys)
            .build()
    }catch (e: Exception){
        errorTP(e.message.toString())
        return null
    }
    e.setVariables(ss.variables)
    if (!e.validate().isValid){
        errorTP("The expression $s has errors:\n ${e.validate().errors}")
        return null
    }
    return {
        e.setVariables(ss.variables)
        e.evaluate()
    }
}

internal fun Parser.bwDouble(s: String, ss: StructureState): BwDouble? {
    val num = bwNumber(s, ss) ?: return null
    return { num.invoke().toDouble() }
}


internal fun Parser.bwInt(s: String, ss: StructureState): BwInt? {
    val num = bwNumber(s, ss) ?: return null
    return { num.invoke().toDouble().roundToInt() }

}
