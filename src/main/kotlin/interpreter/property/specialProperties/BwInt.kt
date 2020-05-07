package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import org.mariuszgromada.math.mxparser.Argument
import structure.WallStructure
import kotlin.math.roundToInt
import kotlin.reflect.KProperty


class BwInt(expression: String = "0"): BwNumber(expression) {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int {
        return calcExpression(expression,thisRef).roundToInt()
    }
}

class BwIntOrNull(expression: String = "null"): BwNumber(expression) {
    constructor(default: Double?): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int? {
        if (strExpressesNull(expression)) return null
        return calcExpression(expression,thisRef).roundToInt()
    }
}