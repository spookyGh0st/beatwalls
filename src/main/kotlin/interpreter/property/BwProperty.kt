package interpreter.property

import interpreter.property.constantFactory.ConstantController
import org.mariuszgromada.math.mxparser.*
import org.mariuszgromada.math.mxparser.Function
import structure.WallStructure
import java.io.Serializable
import kotlin.reflect.KProperty


abstract class BwProperty:Serializable{

    // stored wallstructure and SpookywallConstants
    // all here stored constants are valid

    /**
     * returns an expression with all saved Constants and functions
     */
    fun buildExpression(expressionString: String,cc:ConstantController): Expression {
        val elements =  buildPrimitiveElements(cc)
        return Expression(expressionString.toLowerCase(),*elements)
    }


    fun buildPrimitiveElements(cc: ConstantController): Array<PrimitiveElement> {
        val elements = buildConstants(cc) + buildArguments(cc)+ buildFunctions(cc)
        return elements.toTypedArray()
    }
    fun buildConstants(cc: ConstantController): List<Constant> {
        val cl = cc.wallConstants + cc.progressConstant  + cc.customConstants
        return cl.filter { it.syntaxStatus }
    }
    fun buildArguments(cc: ConstantController): List<Argument> {
        val cl =  cc.structureConstants
        return cl.filter { it.checkSyntax() }
    }

    fun buildFunctions(cc: ConstantController): List<Function>  {
        val fl = cc.easingFunctions + cc.customFunctions
        return fl.filter { it.checkSyntax() }
    }

    /**
     * creates an Expression and calculates it
     */
    fun calcExpression(expressionString: String,ws: WallStructure): Double {
        val e = buildExpression(expressionString,ws.constantController)
        val value = e.calculate()
        if(e.syntaxStatus)
            return value
        throw InvalidExpressionException(e,"This means, that a constant or functions is propably not loaded")
    }

    abstract fun toArguments(baseName: String): List<Argument>

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

    fun strExpressesNull(s:String) = s == "null" || s.isEmpty()
}

fun strPlusExprStr(s: String, e: String) = "(${s})+($e)"
fun strTimesExprStr(s: String, e: String) = "(${s})*($e)"
fun strPowExprStr(s: String, e: String) = "(${s})^($e)"
