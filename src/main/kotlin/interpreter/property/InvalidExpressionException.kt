package interpreter.property

import net.objecthunter.exp4j.Expression

class InvalidExpressionException(private val expression: Expression, private val msg: String = ""):Exception(msg + expression.also { it.validate().errors })
