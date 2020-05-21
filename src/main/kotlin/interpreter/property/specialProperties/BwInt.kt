package interpreter.property.specialProperties

import structure.WallStructure
import kotlin.math.roundToInt
import kotlin.reflect.KProperty


class BwInt(expression: String = "0.0"): BwNumber(expression) {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int {
        wsRef = thisRef
        return setVarsAndCalcExprForWs(expression,thisRef).roundToInt()
    }
}

class BwIntOrNull(e: String = "null"): BwNumber(e) {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int? {
        wsRef = thisRef
        if (expressionString == "null") return null
        return setVarsAndCalcExprForWs(expression,thisRef).roundToInt()
    }
}
