package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import structure.WallStructure
import kotlin.reflect.KProperty

class BwDouble(private var expression: String = "0.0"): BwProperty() {
    constructor(default: Number): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double {
        return calcExpression(expression,thisRef)
    }

    override fun setExpr(e: String) {
        expression = e
    }

    override fun plusExpr(e: String) {
        expression = strPlusExprStr(expression, e)
    }

    override fun timesExpr(e: String) {
        expression = strTimesExprStr(expression, e)
    }

    override fun powExpr(e: String) {
        expression = strPowExprStr(expression, e)
    }

    override fun toString(): String = expression
}

class BwDoubleOrNull(private var expresssion: String = "null"): BwProperty() {
    constructor(default: Double?): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double? {
        if (strExpressesNull(expresssion)) return null
        return calcExpression(expresssion,thisRef)
    }

    override fun setExpr(e: String) {
        expresssion = e
    }

    override fun plusExpr(e: String) {
        expresssion = strPlusExprStr(expresssion, e)
    }

    override fun timesExpr(e: String) {
        expresssion = strTimesExprStr(expresssion, e)
    }

    override fun powExpr(e: String) {
        expresssion = strPowExprStr(expresssion, e)
    }

    override fun toString(): String = expresssion
}
