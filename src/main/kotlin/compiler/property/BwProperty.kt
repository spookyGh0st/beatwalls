package compiler.property

import compiler.property.constantFactory.ConstantController
import compiler.property.constantFactory.getEasingFunctions
import compiler.property.constantFactory.getWsConstants
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Expression
import structure.Interface
import structure.WallStructure
import structure.helperClasses.SpookyWall
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


abstract class BwProperty(){
    // controls all the easing
    private val ec = ConstantController()
    private val easingFunctions = getEasingFunctions(ec)

    // stored wallstructure and SpookywallConstants
    // all here stored constants are valid
    private var swConstants = listOf<Constant>()
    private var wsConstants = listOf<Constant>()




    /**
     * returns an expression with all saved Constants and functions
     */
    fun buildExpression(expressionString: String): Expression {
            val cs = swConstants + wsConstants + easingFunctions
            val ca = cs.toTypedArray()
            return Expression(expressionString,*ca)
    }

    /**
     * creates an Expression and calculates it
     */
    fun calcExpression(expressionString: String): Double {
        val e = buildExpression(expressionString)
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



    fun setWsConstants(ws: WallStructure) {
        val const = getWsConstants(ws)
        val correctConst = const.filter { it.syntaxStatus }
        wsConstants = correctConst.toList()
    }

    fun setEasingFunctions(progress: Double) {
        ec.progress=progress
    }

    fun strExpressesNull(s:String) = s == "null" || s.isEmpty()
}

fun strPlusExprStr(s: String, e: String) = "(${s})+($e)"
fun strTimesExprStr(s: String, e: String) = "(${s})*($e)"
fun strPowExprStr(s: String, e: String) = "(${s})^($e)"

fun main(){
    var v = 1.0
    val a = Constant("a",v)
    a.constantValue = 2.0
    val e = Expression("10+a",a)
    println(e.calculate())

   // a.initializeProperty("testProperty1","linear(10,20) ")
   // a.initializeProperty("testProperty2","2 ")
   // println("t1: ${a.testProperty1}")
   // println("t2: ${a.testProperty2}")
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
    del.setEasingFunctions(0.5)
    //todo all bw should share a same upperclass, which sets stuff like the sw and easing
}


class r(){

}
