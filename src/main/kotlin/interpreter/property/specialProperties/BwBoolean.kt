package interpreter.property.specialProperties

import structure.WallStructure
import kotlin.math.roundToInt
import kotlin.reflect.KProperty

//since we only cast true and false at the end, this is basically a number
class BwBoolean(expression: String = "0.0"): BwNumber(expression) {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Boolean {
        wsRef = thisRef
        return setVarsAndCalcExprForWs(expression,thisRef).roundToInt() != 0
    }
}

class BwBooleanOrNull(e: String = "null"): BwNumber(e) {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Boolean? {
        wsRef = thisRef
        if (expressionString == "null") return null
        return setVarsAndCalcExprForWs(expression,thisRef).roundToInt() != 0
    }
}
