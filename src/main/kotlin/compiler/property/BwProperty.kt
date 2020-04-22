package compiler.property

import compiler.property.constantFactory.ConstantController
import org.mariuszgromada.math.mxparser.*
import org.mariuszgromada.math.mxparser.Function
import structure.Line
import structure.WallStructure
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


abstract class BwProperty(){

    // stored wallstructure and SpookywallConstants
    // all here stored constants are valid

    /**
     * returns an expression with all saved Constants and functions
     */
    fun buildExpression(expressionString: String,cc:ConstantController): Expression {
        val elements =  buildPrimitiveElements(cc)
        return Expression(expressionString,*elements)
    }


    fun buildPrimitiveElements(cc: ConstantController): Array<PrimitiveElement> {
        val elements = buildConstants(cc) + buildFunctions(cc)
        return elements.toTypedArray()
    }
    fun buildConstants(cc: ConstantController): List<Constant> {
        val cl = cc.wallConstants + cc.progressConstant + cc.structureConstants + cc.customConstants
        return cl.filter { it.syntaxStatus }
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
        throw InvalidExpressionException(e)
    }


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

    abstract override fun toString(): String

    fun strExpressesNull(s:String) = s == "null" || s.isEmpty()
}

fun strPlusExprStr(s: String, e: String) = "(${s})+($e)"
fun strTimesExprStr(s: String, e: String) = "(${s})*($e)"
fun strPowExprStr(s: String, e: String) = "(${s})^($e)"

fun main(){
    val a = Line()

    a.initializeProperty("testProperty","linear(10,20) + testProperty2")
    a.initializeProperty("testRecursiveProperty","2 ")
    a.constantController.progress = 0.5
    println("t1: ${a.testProperty}")
    println("t2: ${a.testRecursiveProperty}")
}

fun WallStructure.initializeProperty(name: String, value: String){
    val prop = this::class.memberProperties.find { it.name.toLowerCase() == name.toLowerCase() }
    if(prop == null || prop.returnType.isMarkedNullable)
        throw Exception()
    @Suppress("UNCHECKED_CAST")
    prop as KProperty1<WallStructure, Any>
    prop.isAccessible = true
    val del = prop.getDelegate(this)
    if (del !is BwProperty) throw  Exception()
    del.setExpr(value.toLowerCase())
    //todo all bw should share a same upperclass, which sets stuff like the sw and easing
}


class r(){

}
