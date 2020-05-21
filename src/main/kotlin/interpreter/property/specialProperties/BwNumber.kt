package interpreter.property.specialProperties

import interpreter.property.*
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import structure.WallStructure
import kotlin.math.pow

abstract class BwNumber(var expressionString: String): BwProperty() {
    // this needs to be lazy, so wsRef is set
    val expression: Expression by lazy { buildExpression(expressionString)  }

    /**
     * returns an expression with all saved Constants and functions
     */


    /**
     * creates an Expression and calculates it
     */

    override fun setExpr(e: String) {
        expressionString = e
    }

    override fun plusExpr(e: String) {
        expressionString = strPlusExprStr(expressionString, e)
    }

    override fun timesExpr(e: String) {
        expressionString = strTimesExprStr(expressionString, e)
    }

    override fun powExpr(e: String) {
        expressionString = strPowExprStr(expressionString, e)
    }
}