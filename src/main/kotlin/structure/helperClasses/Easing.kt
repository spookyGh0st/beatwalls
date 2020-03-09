package structure.helperClasses

import kotlin.math.*


@Suppress("unused")
enum class Easing: (Double)-> Double{
    Linear {
        override fun invoke(x: Double): Double = x
    },
    EaseInQuad {
        override fun invoke(x: Double): Double = x*x
    },
    EaseOutQuad {
        override fun invoke(x: Double): Double = x *(2-x)
    },
    EaseInOutQuad {
        override fun invoke(x: Double): Double = if(x<0.5) 2*x*x else -1+(4-2*x)*x
    },
    EaseInCubic {
        override fun invoke(x: Double): Double = x*x*x
    },
    EaseOutCubic {
        override fun invoke(x: Double): Double = (x - 1).pow(3) + 1
    },
    EaseInOutCubic {
        override fun invoke(x: Double): Double = if(x<0.5) 4*x*x*x else (x-1)*(2*x-2)*(2*x-2)+1
    },
    EaseInQuart {
        override fun invoke(x: Double): Double = x*x*x*x
    },
    EaseOutQuart {
        override fun invoke(x: Double): Double = 1-(x-1).pow(4)
    },
    EaseInOutQuart {
        override fun invoke(x: Double): Double = if (x<0.5) 8* x.pow(4) else  1-8*(x-1).pow(4)
    },
    EaseInQuint {
        override fun invoke(x: Double): Double = x.pow(5)
    },
    EaseOutQuint {
        override fun invoke(x: Double): Double = 1+(x-1).pow(5)
    },
    EaseInOutQuint {
        override fun invoke(x: Double): Double = if (x<0.5) 16 * x.pow(5) else 1+16*(x-1).pow(5)
    },
    EaseInSine {
        override fun invoke(x: Double): Double = 1 - cos(x* PI /2)
    },
    EaseOutSine{
        override fun invoke(x: Double): Double = sin(x* PI / 2)
    },
    EaseInOutSine{
        override fun invoke(x: Double): Double = -(cos(PI*x)-1)/2
    },
    EaseInCirc{
        override fun invoke(x: Double): Double =  1 - sqrt(1 - x.pow(2))
    },
    EaseOutCirc{
        override fun invoke(x:Double): Double = sqrt(1 - (x-1).pow(2))
    },
    EaseInOutCirc{
        override fun invoke(x: Double): Double = if (x < 0.5) (1 - sqrt(1 - (2*x).pow(2))) / 2 else (sqrt(1 - (-2*x+2).pow(2)) + 1) / 2
    },
    EaseInBack{
        override fun invoke(x: Double): Double =  c3 * x * x * x - c1 * x * x
    },
    EaseOutBack {
        override fun invoke(x: Double): Double = 1 + c3 * (x-1).pow(3) + c1 * (x-1).pow(2)
    },
    EaseInOutBack {
        override fun invoke(x: Double): Double =
            if(x < 0.5)
                ((2 * x).pow(2) * ((c2 + 1) * 2 * x - c2)) / 2
            else
                ((2*x-2).pow( 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2
    },
    EaseInElastic{
        override fun invoke(x: Double): Double =
            when(x){
                0.0-> 0.0
                1.0 -> 1.0
                else -> -(2.0.pow(10 * x - 10) * sin((x * 10 - 10.75) * c4))
            }
    },
    EaseOutElastic{
        override fun invoke(x: Double): Double =
            when(x){
                0.0-> 0.0
                1.0 -> 1.0
                else -> -(2.0.pow(-10 * x) * sin((x * 10 - 0.75) * c4) + 1)
            }
    },
    EaseInOutElastic{
        override fun invoke(x: Double): Double =
            when{
                x==0.0-> 0.0
                x==1.0 -> 1.0
                x<0.5 -> -(2.0.pow(20 * x - 10) * sin((20 * x - 11.125) * c5)) / 2
                else -> (2.0.pow(-20 * x + 10) * sin((20 * x - 11.125) * c5)) / 2 + 1
            }
    },
    EaseInBounce{
        override fun invoke(x: Double): Double = 1- bounceOut(1-x)
    },
    EaseOutBounce{
        override fun invoke(x: Double): Double = bounceOut(x)
    },
    EaseInOutBounce{
        override fun invoke(x: Double): Double = if(x<0.5) (1- bounceOut(1-2*x))/2 else (1+ bounceOut(2*x-1))/2
    }
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




