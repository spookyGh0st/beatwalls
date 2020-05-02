package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import org.mariuszgromada.math.mxparser.Argument
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

    override fun toArguments(baseName: String): List<Argument> {
        return listOf(Argument("$baseName=$expression"))
    }
}

class BwDoubleOrNull(private var expression: String = "null"): BwProperty() {
    constructor(default: Double?): this(default.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double? {
        if (strExpressesNull(expression)) return null
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

    override fun toArguments(baseName: String): List<Argument> {
        return listOf(Argument("$baseName=$expression"))
    }
}
