package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.ArgumentExtension
import structure.WallStructure
import kotlin.reflect.KProperty

abstract class BwNumber(var expression: String): BwProperty() {
    constructor(default: Number): this(default.toString())

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