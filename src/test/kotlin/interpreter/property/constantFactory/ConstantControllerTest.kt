package interpreter.property.constantFactory

import org.junit.Assert.*
import org.junit.Test
import org.mariuszgromada.math.mxparser.*

class ConstantControllerTest{

    @Test
    fun `test different types`(){
        val const = Constant("a = foo")
        val arg = Argument("a = foo")
        val e = { t: PrimitiveElement -> Expression("10",t).calculate() }
        println("const: ${const.syntaxStatus} = ${e(const)}")
        println("arg: ${arg.checkSyntax()} = ${e(arg)}")
        arg.argumentValue = 20.0
        const.constantValue = 20.0
        println("const: ${const.syntaxStatus} = ${e(const)}")
        println("arg: ${arg.checkSyntax()} = ${e(arg)}")
        val argument = Argument("a",SpookyWallArgument())
    }
}

class SpookyWallArgument: ArgumentExtension{
    override fun clone(): ArgumentExtension {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getArgumentValue(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}