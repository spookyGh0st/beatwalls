package compiler.property

import structure.WallStructure
import structure.helperClasses.Point
import kotlin.reflect.KProperty

class BwPoint(): BwProperty<Point>() {
    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Point {
        // constants of the wallstructures properties
        val constants = getConstants(thisRef)
        // splits the 3 values separated by commas
        val valueExpressionStrings = expressionString.split(Regex(",(?![^(]*\\))"))
        // turns the strings into Expressions
        val valueExpressions = valueExpressionStrings.map { getExpression(it, constants) }
        // throws an exception if one Expression is not valid
        valueExpressions.forEach { if (!checkExpression(it))throw InvalidExpressionException(
            it
        )
        }
        // retrieves the values from the expressions
        val values = valueExpressions.map { it.calculate() }
        // turns the values into a Point
        return Point(
            values.elementAtOrNull(0) ?: 0.0,
            values.elementAtOrNull(1) ?: 0.0,
            values.elementAtOrNull(2) ?: 0.0
        )
    }
}