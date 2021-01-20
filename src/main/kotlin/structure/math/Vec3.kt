package structure.math

import kotlin.math.sqrt

data class Vec3(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0){
    constructor(x: Number, y: Number, z: Number): this(x.toDouble(), y.toDouble(), z.toDouble())
    val length
    get() = sqrt(x * x + y * y + z * z)

    /**
     * Normalise the vector to length 1
     */

    fun normalize(){
        val l = 1.0/ sqrt(x * x + y * y + z * z)
        x*=l; y*= l; z*=l
    }

    fun toVec2(): Vec2 {
        return Vec2(x,y)
    }

    operator fun plus(vec3: Vec3): Vec3 {
        return Vec3(x + vec3.x, y + vec3.y, z + vec3.z)
    }
    operator fun minus(vec3: Vec3): Vec3 {
        return Vec3(x - vec3.x, y - vec3.y, z - vec3.z)
    }
    operator fun times(fac: Double): Vec3 {
        return Vec3(x * fac, y * fac, z * fac)
    }
    operator fun times(fac: Int): Vec3 {
        return Vec3(x * fac, y * fac, z * fac)
    }
    operator fun div(fact: Double): Vec3 {
        val xfact = 1.0/ fact
        return Vec3(x * xfact, y * xfact, z * xfact)
    }
    operator fun div(fact: Int): Vec3 {
        val xfact = 1.0/ fact
        return Vec3(x * xfact, y * xfact, z * xfact)
    }
    operator fun get(index: Int) = when(index){
        0 -> x; 1 -> y; 2 -> z
        else -> throw IndexOutOfBoundsException()
    }

    operator fun times(mat3: Mat3): Vec3 =
        Vec3(
            mat3.x.x * x + mat3.x.y * y + mat3.x.z * z,
            mat3.y.x * x + mat3.y.y * y + mat3.y.z * z,
            mat3.z.x * x + mat3.z.y * y + mat3.z.z * z,
        )

    fun toList(): List<Double> =
        listOf(x,y,z)

    operator fun times(vec3: Vec3): Vec3 {
        return Vec3(
            vec3.x * x,
            vec3.y * y,
            vec3.z * z,
        )

    }
}

operator fun Double.times(vec3: Vec3): Vec3 {
    return Vec3(vec3.x*this, vec3.y*this, vec3.z*this)
}

operator fun Int.times(vec3: Vec3): Vec3 {
    return Vec3(vec3.x*this, vec3.y*this, vec3.z*this)
}
