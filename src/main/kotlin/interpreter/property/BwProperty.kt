package interpreter.property

import interpreter.parser.bwPropNames
import interpreter.property.functions.*
import interpreter.property.variables.buildInVariables
import interpreter.property.variables.wallVariables
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException
import structure.WallStructure
import java.lang.NullPointerException
import kotlin.reflect.KProperty


abstract class BwProperty{

    var wsRef: WallStructure? = null
    abstract operator fun getValue(thisRef: WallStructure, property: KProperty<*>): Any?

    /**
     * sets the expression to e
     */
    abstract fun setExpr(e: String)

    /**
     * adds e to the expression
     */
    abstract fun plusExpr(e: String)

    /**
     * times e to the expression
     */
    abstract fun timesExpr(e: String)

    /**
     * takes the expression to the power of e
     */
    abstract fun powExpr(e: String)

    fun buildExpression(expressionString: String): Expression {
        return ExpressionBuilder(expressionString)
            .functions(BwRandom0(wsRef),BwRandom1(wsRef),BwRandom2(wsRef))
            .functions(easingFunctions(wsRef))
            .functions(BwSwitch2(),BwSwitch3(),BwSwitch4(),BwSwitch5())
            .variables(wallVariables.keys)
            .variables(bwPropNames.toMutableSet())
            .build()
    }

    fun setVariables(e: Expression, ws: WallStructure): Expression {

        e.variableNames.forEach{
            val name = it
            val value = variableValue(it,ws)?: throw NullPointerException("$it returns null")
            e.setVariable(name,value)
        }
        return e
    }

    fun variableValue(s:String,ws: WallStructure): Double? = when (s){
        in buildInVariables.keys -> buildInVariables[s]
        in wallVariables.keys -> wallVariables[s]?.invoke(ws.activeWall)
        "i" -> wsRef?.i?:0.0
        else -> throw UnknownFunctionOrVariableException(s,0,0)
    }

    fun setVarsAndCalcExprForWs(expression:Expression,ws: WallStructure): Double  {
        wsRef = ws
        return setVariables(expression,ws).evaluate()
    }
}

fun strPlusExprStr(s: String, e: String) = "(${s})+($e)"
fun strTimesExprStr(s: String, e: String) = "(${s})*($e)"
fun strPowExprStr(s: String, e: String) = "(${s})^($e)"
