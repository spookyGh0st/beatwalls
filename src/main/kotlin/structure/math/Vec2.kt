package structure.math

import kotlin.math.sqrt

data class Vec2(var x: Double = 0.0, var y: Double = 0.0){
    constructor(x: Number, y: Number): this(x.toDouble(), y.toDouble())
    val length
        get() = sqrt(x * x + y * y )

    /**
     * Normalise the vector to length 1
     */
    fun normalize(){
        val l = 1.0/ sqrt(x * x + y * y )
        x*=l; y*= l
    }

    fun toVec3(z: Double = 0.0): Vec3 {
        return Vec3(x,y,z)
    }


    operator fun plus(vec2: Vec2): Vec2 {
        return Vec2(x + vec2.x, y + vec2.y)
    }
    operator fun minus(vec2: Vec2): Vec2 {
        return Vec2(x - vec2.x, y - vec2.y)
    }
    operator fun times(fac: Double): Vec2 {
        return Vec2(x * fac, y * fac)
    }
    operator fun times(fac: Int): Vec2 {
        return Vec2(x * fac, y * fac)
    }
    operator fun div(fact: Double): Vec2 {
        val xfact = 1.0/ fact
        return Vec2(x * xfact, y * xfact)
    }
    operator fun div(fact: Int): Vec2 {
        val xfact = 1.0/ fact
        return Vec2(x * xfact, y * xfact)
    }
    operator fun get(index: Int) = when(index){
        0 -> x; 1 -> y
        else -> throw IndexOutOfBoundsException()
    }

    fun toList(): List<Double> =
        listOf(x,y)

    operator fun times(vec2: Vec2): Vec2 {
        return Vec2(
            vec2.x * x,
            vec2.y * y,
        )

    }
}

operator fun Double.times(vec2: Vec2): Vec2 {
    return Vec2(vec2.x*this, vec2.y*this )
}

operator fun Int.times(vec2: Vec2): Vec2 {
    return Vec2(vec2.x*this, vec2.y*this)
}
