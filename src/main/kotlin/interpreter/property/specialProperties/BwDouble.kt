package interpreter.property.specialProperties

import interpreter.property.BwProperty
import structure.WallStructure
import java.lang.Exception
import kotlin.math.pow
import kotlin.reflect.KProperty

class BwDouble(expression: String = "0.0"): BwNumber(expression) {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double {
        wsRef = thisRef // this is needed so we pass the right ws in the functions
        return expression.evaluate()
    }
}

class BwDoubleOrNull(e: String = "null"): BwNumber(e) {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double? {
        wsRef = thisRef // this is needed so we pass the right ws in the functions
        if (expressionString == "null") return null
        return expression.evaluate()
    }
}
