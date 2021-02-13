package math

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
            x*mat3.x.x + y*mat3.y.x + z*mat3.z.x,
            x*mat3.x.y + y*mat3.y.y + z*mat3.z.y,
            x*mat3.x.z + y*mat3.y.z + z*mat3.z.z,
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

    fun toVec4(w: Double): Vec4 =
        Vec4(x,y,z,w)

    fun dot(v: Vec3): Double =
        x*v.x + y*v.y + z*v.z

    fun cross(b:Vec3): Vec3 {
        val v = Vec3()

        v.x = y * b.z - z * b.y
        v.y = z * b.x - x * b.z
        v.z = x * b.y - y * b.x

        return v

    }
}

