package interpreter.property.elements

import junit.framework.TestCase
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import java.util.concurrent.Executors
import kotlin.random.Random

class WallArgumentKtTest : TestCase() {

    fun testWallArguments() {
        val e = ExpressionBuilder("random(20)")
            .variables("x", "y","z","ak","i")
            .functions(RandomFunc(),Random2Func())
            .build()
        e.setVariable("x", 2.3)

        val exec = Executors.newFixedThreadPool(1)
        repeat(50000) {
            //  e.setVariable("i", it/50000.0)
            //  e.setVariable("x", it.toDouble())
            //  e.setVariable("y", it.toDouble()+1)
            println(e.evaluate())

        }
        println(e.variableNames)
    }

    fun testToDoubleOrZero() {

    }
}
class RandomFunc(): Function("random",2){
    private val r = Random(0)
    override fun apply(vararg p0: Double): Double = r.nextDouble(p0[0],p0[1])

    }
class Random2Func(): Function("random",1){
    private val r = Random(0)
    override fun apply(vararg p0: Double): Double = r.nextDouble(p0[0])

}

