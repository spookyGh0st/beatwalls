package interpreter.property.elements

import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.Function
import org.mariuszgromada.math.mxparser.parsertokens.Token
import structure.helperClasses.SpookyWall
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class ElementFactory {
    lateinit var wall: SpookyWall
    val functions = hashMapOf<String, FunctionFactory>()

    val wallArguments: HashMap<String, Argument> = wallArguments(wall)

    fun addPrimitiveElementsToExpression(e: Expression) {
        val t = e.copyOfInitialTokens.filter { it.keyWord == "" }

    }

    fun addArguments(e: Expression, t: List<Token>) {
        t.filter { it.looksLike == "argument" }.forEach {
            val n = it.tokenStr
            val a = wallArguments[n] ?: throw Exception()
            e.addArguments(a)
        }
    }
}




typealias FunctionFactory = () -> Function




