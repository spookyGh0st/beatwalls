package interpreter.property.constantFactory

import org.junit.Assert.*
import org.junit.Test
import org.mariuszgromada.math.mxparser.*
import kotlin.math.exp

class ConstantControllerTest{

    @Test
    fun `test different types`(){
        val const = Constant("a = foo")
        val arg = Argument("a = foo")
        val e = { t: PrimitiveElement -> Expression("a",t).calculate() }
        println("const: ${const.syntaxStatus} = ${e(const)}")
        println("arg: ${arg.checkSyntax()} = ${e(arg)}")
        arg.argumentValue = 20.0
        const.constantValue = 20.0
        println("const: ${const.syntaxStatus} = ${e(const)}")
        println("arg: ${arg.checkSyntax()} = ${e(arg)}")
        val argument = Argument("a",SpookyWallArgument())
        val expra = Expression("a",arg)
        println("BERFORE: ${expra.calculate()}")
        arg.argumentValue = 10.0
        println("AFTER: ${expra.calculate()}")

        val c = Argument("b = 10 + 5 - a",arg)
        c.defineArgument("a",20.0)
        val es = Expression("10 + 5 - b")
        val r = es.copyOfInitialTokens
        es.addArguments(c)

        println(es.calculate())

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