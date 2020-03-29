package compiler.property

import org.mariuszgromada.math.mxparser.Expression

class InvalidExpressionException(private val expression: Expression, private val msg: String = ""):Exception(){
    override val message: String?
        get() = msg + expression.errorMessage
}