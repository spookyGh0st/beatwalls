package compiler.property

import structure.WallStructure
import kotlin.reflect.KProperty

class BwDouble(default: String="0.0"): BwProperty<Double>(default) {
    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double {
        val expression = getExpression(expressionString, getConstants(thisRef))
        if(!checkExpression(expression)) throw InvalidExpressionException(
            expression
        )
        return expression.calculate()
    }
}