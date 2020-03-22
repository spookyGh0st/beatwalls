package compiler.default

import compiler.Compiler
import compiler.Expressions.defineFun
import org.mariuszgromada.math.mxparser.Function

val randomFunction = Function("random(min,max) =rUnid(1,10)")

fun Compiler.includeDefaultFunctions() {
    defineFun(randomFunction)
}