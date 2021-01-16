package types

import net.objecthunter.exp4j.function.Function
import structure.StructureState
import kotlin.math.*

fun baseFunctions(ss: StructureState) = listOf(
    Linear(ss),
    EaseInQuad(ss),
    EaseOutQuad(ss),
    EaseInOutQuad(ss),
    EaseInCubic(ss),
    EaseOutCubic(ss),
    EaseInOutCubic(ss),
    EaseInQuart(ss),
    EaseOutQuart(ss),
    EaseInOutQuart(ss),
    EaseInQuint(ss),
    EaseOutQuint(ss),
    EaseInOutQuint(ss),
    EaseInSine(ss),
    EaseOutSine(ss),
    EaseInOutSine(ss),
    EaseInCirc(ss),
    EaseOutCirc(ss),
    EaseInOutCirc(ss),
    EaseInBack(ss),
    EaseOutBack(ss),
    EaseInOutBack(ss),
    EaseInElastic(ss),
    EaseOutElastic(ss),
    EaseInOutElastic(ss),
    EaseInBounce(ss),
    EaseOutBounce(ss),
    EaseInOutBounce(ss),
    Random(ss)
)

class Random(val ss: StructureState) : Function("random", 2 ) {
    override fun apply(vararg args: Double): Double =
       ss.R.nextDouble(args[0],args[1])
}

abstract class BwEasing(name: String, val fs: StructureState): Function(name,2) {
    override fun apply(vararg args: Double): Double {
        val start = args[0]
        val end = args[1]
        val progress = fs.variables[keyProgress]?:0.0
        return start + (end - start) * invoke(progress)
    }
    abstract fun invoke(x: Double): Double
}

class Linear(fs: StructureState) : BwEasing("linear", fs ) {
    override fun invoke(x: Double): Double = x
}

class EaseInQuad(fs: StructureState) : BwEasing("easeinquad", fs) {
    override fun invoke(x: Double): Double = x*x
}

class EaseOutQuad(fs: StructureState) : BwEasing("easeoutquad", fs) {
    override fun invoke(x: Double): Double = x *(2-x)
}

class EaseInOutQuad(fs: StructureState) : BwEasing("easeinoutquad", fs) {
    override fun invoke(x: Double): Double = if(x<0.5) 2*x*x else -1+(4-2*x)*x
}

class EaseInCubic(fs: StructureState) : BwEasing("easeincubic", fs) {
    override fun invoke(x: Double): Double = x*x*x
}

class EaseOutCubic(fs: StructureState) : BwEasing("easeoutcubic", fs) {
    override fun invoke(x: Double): Double = (x - 1).pow(3) + 1
}

class EaseInOutCubic(fs: StructureState) : BwEasing("easeinoutcubic", fs) {
    override fun invoke(x: Double): Double = if(x<0.5) 4*x*x*x else (x-1)*(2*x-2)*(2*x-2)+1
}

class EaseInQuart(fs: StructureState) : BwEasing("easeinquart", fs) {
    override fun invoke(x: Double): Double = x*x*x*x
}

class EaseOutQuart(fs: StructureState) : BwEasing("easeoutquart", fs) {
    override fun invoke(x: Double): Double = 1-(x-1).pow(4)
}

class EaseInOutQuart(fs: StructureState) : BwEasing("easeinoutquart", fs) {
    override fun invoke(x: Double): Double = if (x<0.5) 8* x.pow(4) else  1-8*(x-1).pow(4)
}

class EaseInQuint(fs: StructureState) : BwEasing("easeinquint", fs) {
    override fun invoke(x: Double): Double = x.pow(5)
}

class EaseOutQuint(fs: StructureState) : BwEasing("easeoutquint", fs) {
    override fun invoke(x: Double): Double = 1+(x-1).pow(5)
}

class EaseInOutQuint(fs: StructureState) : BwEasing("easeinoutquint", fs) {
    override fun invoke(x: Double): Double = if (x<0.5) 16 * x.pow(5) else 1+16*(x-1).pow(5)
}

class EaseInSine(fs: StructureState) : BwEasing("easeinsine", fs) {
    override fun invoke(x: Double): Double = 1 - cos(x* PI /2)
}

class EaseOutSine(fs: StructureState) : BwEasing("easeoutsine", fs) {
    override fun invoke(x: Double): Double = sin(x* PI / 2)
}

class EaseInOutSine(fs: StructureState) : BwEasing("easeinoutsine", fs) {
    override fun invoke(x: Double): Double = -(cos(PI *x) -1)/2
}

class EaseInCirc(fs: StructureState) : BwEasing("easeincirc", fs) {
    override fun invoke(x: Double): Double =  1 - sqrt(1 - x.pow(2))
}

class EaseOutCirc(fs: StructureState) : BwEasing("easeoutcirc", fs) {
    override fun invoke(x:Double): Double = sqrt(1 - (x-1).pow(2))
}

class EaseInOutCirc(fs: StructureState) : BwEasing("easeinoutcirc", fs) {
    override fun invoke(x: Double): Double = if (x < 0.5) (1 - sqrt(1 - (2*x).pow(2))) / 2 else (sqrt(1 - (-2*x+2).pow(2)) + 1) / 2
}

class EaseInBack(fs: StructureState) : BwEasing("easeinback", fs) {
    override fun invoke(x: Double): Double =  c3 * x * x * x - c1 * x * x
}

class EaseOutBack(fs: StructureState) : BwEasing("easeoutback", fs) {
    override fun invoke(x: Double): Double = 1 + c3 * (x-1).pow(3) + c1 * (x-1).pow(2)
}

class EaseInOutBack(fs: StructureState) : BwEasing("easeinoutback", fs) {
    override fun invoke(x: Double): Double =
        if(x < 0.5)
            ((2 * x).pow(2) * ((c2 + 1) * 2 * x - c2)) / 2
        else
            ((2*x-2).pow( 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2
}

class EaseInElastic(fs: StructureState) : BwEasing("easeinelastic", fs) {
    override fun invoke(x: Double): Double =
        when(x){
            0.0-> 0.0
            1.0 -> 1.0
            else -> -(2.0.pow(10 * x - 10) * sin((x * 10 - 10.75) * c4))
        }
}

class EaseOutElastic(fs: StructureState) : BwEasing("easeoutelastic", fs) {
    override fun invoke(x: Double): Double =
        when(x){
            0.0-> 0.0
            1.0 -> 1.0
            else -> -(2.0.pow(-10 * x) * sin((x * 10 - 0.75) * c4) + 1)
        }
}

class EaseInOutElastic(fs: StructureState) : BwEasing("easeinoutelastic", fs) {
    override fun invoke(x: Double): Double =
        when{
            x==0.0-> 0.0
            x==1.0 -> 1.0
            x<0.5 -> -(2.0.pow(20 * x - 10) * sin((20 * x - 11.125) * c5)) / 2
            else -> (2.0.pow(-20 * x + 10) * sin((20 * x - 11.125) * c5)) / 2 + 1
        }
}

class EaseInBounce(fs: StructureState) : BwEasing("easeinbounce", fs) {
    override fun invoke(x: Double): Double = 1- bounceOut(1 - x)
}

class EaseOutBounce(fs: StructureState) : BwEasing("easeoutbounce", fs) {
    override fun invoke(x: Double): Double = bounceOut(x)
}

class EaseInOutBounce(fs: StructureState) : BwEasing("easeinoutbounce", fs) {
    override fun invoke(x: Double): Double = if(x<0.5) (1- bounceOut(
        1 - 2 * x
    ))/2 else (1+ bounceOut(2 * x - 1))/2
}

internal fun bounceOut(x:Double): Double {
    val n1 = 7.5625
    val d1 = 2.75
    return when {
        x < 1 / d1 -> {
            n1 * x * x
        }
        x < 2 / d1 -> {
            n1 * ((x - 1.5) / d1) * (x - 1.5) + 0.75
        }
        x < 2.5 / d1 -> {
            n1 * ((x - 2.25) / d1) * (x - 2.25) + 0.9375
        }
        else -> {
            n1 * ((x - 2.625) / d1) * (x - 2.625) + 0.984375
        }
    }
}

private const val c1 = 1.70158
private const val c2 = c1 * 1.525
private const val c3 = c1 + 1
private const val c4 = (2 * PI) / 3
private const val c5 = (2 * PI) / 4.5




