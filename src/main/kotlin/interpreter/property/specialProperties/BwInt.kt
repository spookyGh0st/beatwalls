package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import org.mariuszgromada.math.mxparser.Argument
import structure.WallStructure
import kotlin.math.roundToInt
import kotlin.reflect.KProperty


class BwInt(private var expression: String = "0.0"): BwProperty() {
    constructor(e: Int): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int {
        return calcExpression(expression,thisRef).roundToInt()
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

class BwIntOrNull(private var expression: String = "null"): BwProperty() {
    constructor(e: Int?): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int? {
        if (strExpressesNull(expression)) return null
        return calcExpression(expression,thisRef).roundToInt()
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