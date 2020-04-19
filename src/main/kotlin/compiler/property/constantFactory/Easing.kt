package compiler.property.constantFactory

import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.Function
import org.mariuszgromada.math.mxparser.FunctionExtension
import structure.helperClasses.Easing

fun getEasingFunctions(ec: ConstantController): List<Function> =
    Easing.values().map { it.toFunction(ec) }

fun Easing.toFunction(ec: ConstantController): Function =
    Function(name.toLowerCase(),EasingFunction(this,ec))

data class EasingFunction(val e: Easing,val ec: ConstantController): FunctionExtension {
    var min: Double = 0.0
    var max: Double = 1.0

    override fun calculate(): Double = min + (max - min) * e(ec.progress)
    override fun setParameterValue(i: Int, v: Double) {
        when(i){
            0 -> min=v
            1 -> max=v
        }
    }

    override fun getParameterName(i: Int): String =
        when(i){
            0 -> "min"
            1 -> "max"
            else -> throw Exception()
        }


    override fun clone(): FunctionExtension {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getParametersNumber(): Int = 2

}
