package compiler.property

import structure.WallStructure
import kotlin.math.roundToInt
import kotlin.reflect.KProperty

class BwInt(default: String="1"): BwProperty<Int>(default) {
    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int {
        val expression = getExpression(expressionString, getConstants(thisRef))
        if(!checkExpression(expression)) throw InvalidExpressionException(
            expression
        )
        return expression.calculate().roundToInt()
    }
}