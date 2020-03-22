package compiler.Expressions

import compiler.Compiler
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import org.mariuszgromada.math.mxparser.PrimitiveElement

fun Compiler.addArg(element: PrimitiveElement) {
    args += Pair(element.hashCode(), element)
    when (element) {
        is Constant -> valList += Pair(element.constantName, element.hashCode())
        is Function -> funList += Pair(element.functionName, element.hashCode())
    }
}

fun Compiler.defineVal(s: String) {
    val f = s.removePrefix("val ")
    addArg(Constant(f, *args.values.toTypedArray()))
}

fun Compiler.defineFun(f: Function) {
    addArg(f)
}

fun Compiler.defineFun(s: String) {
    val f = s.removePrefix("fun ")
    addArg(Function(f, *args.values.toTypedArray()))
}
