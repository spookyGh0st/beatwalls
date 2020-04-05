package compiler.property

import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.Function
import structure.Interface
import structure.WallStructure
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


abstract class BwProperty<T>(var expressionString: String) {
    var isInitialized=false
    var preDefinedVars: Set<Constant> = setOf()
    var wallValues: Set<Constant> = setOf()
    var runTimeValues: Set<Constant> = setOf()
    var preDefinedFunctions: Set<Function> = setOf()

    fun initialize(expression: String, variables: List<Constant>, functions: List<Function>){
        this.expressionString=expression
        this.preDefinedVars=variables.toSet()
        this.preDefinedFunctions=functions.toSet()
        this.isInitialized=true
    }

    abstract operator fun getValue(thisRef: WallStructure, property: KProperty<*>): T

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

fun<T> KProperty1<out T,Any?>.getDelegate(receiver: WallStructure): Any? {
    return this.getDelegate(receiver)
}

fun main(){
    val a = Interface()
    a.initializeProperty("testProperty2","2 ")
    a.initializeProperty("testProperty1","10 * testProperty2")
    println("t1: ${a.testProperty1}")
    println("t2: ${a.testProperty2}")
}
fun WallStructure.initializeProperty(name: String, value: String){
    val prop = this::class.memberProperties.find { it.name.toLowerCase() == name.toLowerCase() }
    if(prop == null || prop.returnType.isMarkedNullable)
        throw Exception()
    prop as KProperty1<WallStructure, Any>
    prop.isAccessible = true
    val del = prop.getDelegate(this)
    if (del !is BwProperty<*>) throw  Exception()
    del.initialize(value.toLowerCase(), emptyList(), emptyList())
}

class r(){

}
