package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import org.mariuszgromada.math.mxparser.Argument
import structure.WallStructure
import kotlin.reflect.KProperty

data class BwDouble(expression: String = "0.0"): BwNumber(expression) {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double {
        return calcExpression(expression,thisRef)
    }
}

class BwDoubleOrNull(expression: String = "null"): BwNumber(expression) {
    constructor(default: Double?): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double? {
        if (strExpressesNull(expression)) return null
        return calcExpression(expression,thisRef)
    }
}
