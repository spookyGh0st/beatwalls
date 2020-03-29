package compiler.property

import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.Function
import structure.Interface
import structure.WallStructure
import kotlin.math.roundToInt
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


abstract class BwProperty<T> {
    lateinit var expressionString: String
    var isInitialized=false
    var preDefinedVars: Set<Constant> = setOf()
    var preDefinedFunctions: Set<Function> = setOf()

    fun initialize(expression: String, variables: List<Constant>, functions: List<Function>){
        this.expressionString=expression
        this.preDefinedVars=variables.toSet()
        this.preDefinedFunctions=functions.toSet()
        this.isInitialized=true
    }

    abstract operator fun getValue(thisRef: WallStructure, property: KProperty<*>): T
    operator fun getValue(thisRef: WallStructure, property: KProperty<*>,field: T): T {
        initialize(field.toString(), emptyList(), emptyList())
        return getValue(thisRef,property, field)
    }

    @Suppress("UNCHECKED_CAST")
    fun getConstants(wallStructure: WallStructure) = wallStructure::class.memberProperties
        .asSequence()
        .map { it as KProperty1<WallStructure,Any?> }
        .map { it.also { it.isAccessible=true } }
        .filter { it.getDelegate(wallStructure) is BwProperty<*> }
        .map {
            val key = it.name.toLowerCase()
            val value = (it.getDelegate(wallStructure) as BwProperty<*>).expressionString
            Constant("${key}=${value}")
        }
        .filter { it.syntaxStatus}
        .toSet()


    fun getExpression(expression: String, newConstants: Set<Constant>): Expression {
        return Expression(
            expression,
            *(preDefinedVars+newConstants).toTypedArray(),
            *preDefinedFunctions.toTypedArray()
        )
    }

    /**
     * returns true if the expression is valid
     */
    fun checkExpression(expression: Expression)=
        !(expression.calculate().isNaN() || !expression.syntaxStatus)
}


fun main(){
    println(Double.NaN.roundToInt())
    val a = BwString()
    val i=Interface()
    val s="123,f(12,32),321"
    println(s.split(Regex(",(?![^(]*\\))")))
    //(i::testProperty1.also { it.isAccessible=true }.getDelegate() as BwProperty<*>).initialize("5.0", emptyList(), emptyList())
    //println(i.testProperty)
    //println(i.testRecursiveProperty)
    //println(i.testRecursiveProperty)

    //val l = arrayOf(Constant("variable",2.0),Constant("Novariable",3.0))
    //val f = arrayOf(Function("f(x)=x+2"))
    //val v = Expression("3+variable",*f,*l)
    //println(v.calculate())
    //val s=Constant("3-hallo")
}

class r(){

}
