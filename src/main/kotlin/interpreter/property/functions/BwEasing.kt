package interpreter.property.functions

import net.objecthunter.exp4j.function.Function
import structure.WallStructure
import kotlin.math.*

fun easingFunctions(wsRef: WallStructure?) = listOf(
    Linear(wsRef),
    EaseInQuad(wsRef),
    EaseOutQuad(wsRef),
    EaseInOutQuad(wsRef),
    EaseInCubic(wsRef),
    EaseOutCubic(wsRef),
    EaseInOutCubic(wsRef),
    EaseInQuart(wsRef),
    EaseOutQuart(wsRef),
    EaseInOutQuart(wsRef),
    EaseInQuint(wsRef),
    EaseOutQuint(wsRef),
    EaseInOutQuint(wsRef),
    EaseInSine(wsRef),
    EaseOutSine(wsRef),
    EaseInOutSine(wsRef),
    EaseInCirc(wsRef),
    EaseOutCirc(wsRef),
    EaseInOutCirc(wsRef),
    EaseInBack(wsRef),
    EaseOutBack(wsRef),
    EaseInOutBack(wsRef),
    EaseInElastic(wsRef),
    EaseOutElastic(wsRef),
    EaseInOutElastic(wsRef),
    EaseInBounce(wsRef),
    EaseOutBounce(wsRef),
    EaseInOutBounce(wsRef)
)

abstract class BwEasing(name: String, val wsRef: WallStructure?): Function(name,2) {
    override fun apply(vararg args: Double): Double {
        val start = args[0]
        val end = args[1]
        return start + (end - start) * invoke(wsRef?.i?:0.0)
    }
    abstract fun invoke(x: Double): Double
}
class Linear(wsRef: WallStructure?) : BwEasing("linear", wsRef) {
    override fun invoke(x: Double): Double = x
}

class EaseInQuad(wsRef: WallStructure?) : BwEasing("easeinquad", wsRef) {
    override fun invoke(x: Double): Double = x*x
}

class EaseOutQuad(wsRef: WallStructure?) : BwEasing("easeoutquad", wsRef) {
    override fun invoke(x: Double): Double = x *(2-x)
}

class EaseInOutQuad(wsRef: WallStructure?) : BwEasing("easeinoutquad", wsRef) {
    override fun invoke(x: Double): Double = if(x<0.5) 2*x*x else -1+(4-2*x)*x
}

class EaseInCubic(wsRef: WallStructure?) : BwEasing("easeincubic", wsRef) {
    override fun invoke(x: Double): Double = x*x*x
}

class EaseOutCubic(wsRef: WallStructure?) : BwEasing("easeoutcubic", wsRef) {
    override fun invoke(x: Double): Double = (x - 1).pow(3) + 1
}

class EaseInOutCubic(wsRef: WallStructure?) : BwEasing("easeinoutcubic", wsRef) {
    override fun invoke(x: Double): Double = if(x<0.5) 4*x*x*x else (x-1)*(2*x-2)*(2*x-2)+1
}

class EaseInQuart(wsRef: WallStructure?) : BwEasing("easeinquart", wsRef) {
    override fun invoke(x: Double): Double = x*x*x*x
}

class EaseOutQuart(wsRef: WallStructure?) : BwEasing("easeoutquart", wsRef) {
    override fun invoke(x: Double): Double = 1-(x-1).pow(4)
}

class EaseInOutQuart(wsRef: WallStructure?) : BwEasing("easeinoutquart", wsRef) {
    override fun invoke(x: Double): Double = if (x<0.5) 8* x.pow(4) else  1-8*(x-1).pow(4)
}

class EaseInQuint(wsRef: WallStructure?) : BwEasing("easeinquint", wsRef) {
    override fun invoke(x: Double): Double = x.pow(5)
}

class EaseOutQuint(wsRef: WallStructure?) : BwEasing("easeoutquint", wsRef) {
    override fun invoke(x: Double): Double = 1+(x-1).pow(5)
}

class EaseInOutQuint(wsRef: WallStructure?) : BwEasing("easeinoutquint", wsRef) {
    override fun invoke(x: Double): Double = if (x<0.5) 16 * x.pow(5) else 1+16*(x-1).pow(5)
}

class EaseInSine(wsRef: WallStructure?) : BwEasing("easeinsine", wsRef) {
    override fun invoke(x: Double): Double = 1 - cos(x* PI /2)
}

class EaseOutSine(wsRef: WallStructure?) : BwEasing("easeoutsine", wsRef) {
    override fun invoke(x: Double): Double = sin(x* PI / 2)
}

class EaseInOutSine(wsRef: WallStructure?) : BwEasing("easeinoutsine", wsRef) {
    override fun invoke(x: Double): Double = -(cos(PI*x)-1)/2
}

class EaseInCirc(wsRef: WallStructure?) : BwEasing("easeincirc", wsRef) {
    override fun invoke(x: Double): Double =  1 - sqrt(1 - x.pow(2))
}

class EaseOutCirc(wsRef: WallStructure?) : BwEasing("easeoutcirc", wsRef) {
    override fun invoke(x:Double): Double = sqrt(1 - (x-1).pow(2))
}

class EaseInOutCirc(wsRef: WallStructure?) : BwEasing("easeinoutcirc", wsRef) {
    override fun invoke(x: Double): Double = if (x < 0.5) (1 - sqrt(1 - (2*x).pow(2))) / 2 else (sqrt(1 - (-2*x+2).pow(2)) + 1) / 2
}

class EaseInBack(wsRef: WallStructure?) : BwEasing("easeinback", wsRef) {
    override fun invoke(x: Double): Double =  c3 * x * x * x - c1 * x * x
}

class EaseOutBack(wsRef: WallStructure?) : BwEasing("easeoutback", wsRef) {
    override fun invoke(x: Double): Double = 1 + c3 * (x-1).pow(3) + c1 * (x-1).pow(2)
}

class EaseInOutBack(wsRef: WallStructure?) : BwEasing("easeinoutback", wsRef) {
    override fun invoke(x: Double): Double =
        if(x < 0.5)
            ((2 * x).pow(2) * ((c2 + 1) * 2 * x - c2)) / 2
        else
            ((2*x-2).pow( 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2
}

class EaseInElastic(wsRef: WallStructure?) : BwEasing("easeinelastic", wsRef) {
    override fun invoke(x: Double): Double =
        when(x){
            0.0-> 0.0
            1.0 -> 1.0
            else -> -(2.0.pow(10 * x - 10) * sin((x * 10 - 10.75) * c4))
        }
}

class EaseOutElastic(wsRef: WallStructure?) : BwEasing("easeoutelastic", wsRef) {
    override fun invoke(x: Double): Double =
        when(x){
            0.0-> 0.0
            1.0 -> 1.0
            else -> -(2.0.pow(-10 * x) * sin((x * 10 - 0.75) * c4) + 1)
        }
}

class EaseInOutElastic(wsRef: WallStructure?) : BwEasing("easeinoutelastic", wsRef) {
    override fun invoke(x: Double): Double =
        when{
            x==0.0-> 0.0
            x==1.0 -> 1.0
            x<0.5 -> -(2.0.pow(20 * x - 10) * sin((20 * x - 11.125) * c5)) / 2
            else -> (2.0.pow(-20 * x + 10) * sin((20 * x - 11.125) * c5)) / 2 + 1
        }
}

class EaseInBounce(wsRef: WallStructure?) : BwEasing("easeinbounce", wsRef) {
    override fun invoke(x: Double): Double = 1- bounceOut(1 - x)
}

class EaseOutBounce(wsRef: WallStructure?) : BwEasing("easeoutbounce", wsRef) {
    override fun invoke(x: Double): Double = bounceOut(x)
}

class EaseInOutBounce(wsRef: WallStructure?) : BwEasing("easeinoutbounce", wsRef) {
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




