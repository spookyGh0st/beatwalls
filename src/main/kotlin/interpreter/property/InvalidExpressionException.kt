package interpreter.property

import org.mariuszgromada.math.mxparser.Expression

class InvalidExpressionException(private val expression: Expression, private val msg: String = ""):Exception(msg + expression.also { it.setVerboseMode() }.errorMessage){

}