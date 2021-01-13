package structure.helperClasses

import kotlin.math.sqrt

data class Vec3(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0){
    constructor(x: Number, y: Number, z: Number): this(x.toDouble(), y.toDouble(), z.toDouble())
    val length
    get() = sqrt(x*x + y*y + z*z)

    /**
     * Normalise the vector to length 1
     */

    fun normalize(){
        val l = 1.0/ sqrt(x*x + y*y + z*z);
        x*=l; y*= l; z*=l
    }
    fun matMul(vec3: Vec3): Vec3 {
        return Vec3(
            vec3.x * x,
            vec3.y * y,
            vec3.z * z,
        )
    }

    operator fun plus(vec3: Vec3): Vec3 {
        return Vec3(x+vec3.x, y+vec3.y, z+vec3.z)
    }
    operator fun minus(vec3: Vec3): Vec3 {
        return Vec3(x-vec3.x, y-vec3.y, z-vec3.z)
    }
    operator fun times(fac: Double): Vec3 {
        return Vec3(x*fac, y*fac, z*fac)
    }
    operator fun times(fac: Int): Vec3 {
        return Vec3(x*fac, y*fac, z*fac)
    }
    operator fun div(fact: Double): Vec3{
        val xfact = 1.0/ fact
        return Vec3(x*xfact, y*xfact, z*xfact)
    }
    operator fun div(fact: Int): Vec3{
        val xfact = 1.0/ fact
        return Vec3(x*xfact, y*xfact, z*xfact)
    }
    operator fun get(index: Int) = when(index){
        0 -> x; 1 -> y; 2 -> z
        else -> throw IndexOutOfBoundsException(index)
    }

    fun toList(): List<Double> =
        listOf(x,y,z)

    operator fun times(mat3: Mat3): Vec3 =
        Vec3(
            mat3.x.x * x + mat3.x.y * y + mat3.x.z * z,
            mat3.y.x * x + mat3.y.y * y + mat3.y.z * z,
            mat3.z.x * x + mat3.z.y * y + mat3.z.z * z,
        )
}

operator fun Double.times(vec3: Vec3): Vec3 {
    return Vec3(vec3.x*this, vec3.y*this, vec3.z*this)
}

operator fun Int.times(vec3: Vec3): Vec3 {
    return Vec3(vec3.x*this, vec3.y*this, vec3.z*this)
}

data class Mat3(
    val x: Vec3 = Vec3(),
    val y: Vec3 = Vec3(),
    val z: Vec3 = Vec3(),
    ){
    operator fun get(index: Int) = when(index) {
        0 -> x; 1 -> y; 2 -> z
        else -> throw IndexOutOfBoundsException(index)
    }

    operator fun times(mat3: Mat3): Mat3 = Mat3(
        Vec3(
            x.x * mat3.x.x + x.y * mat3.y.x + x.z * mat3.z.x,
            x.x * mat3.x.y + x.y * mat3.y.y + x.z * mat3.z.y,
            x.x * mat3.x.z + x.y * mat3.y.z + x.z * mat3.z.z,
        ), Vec3(
            y.x * mat3.x.x + y.y * mat3.y.x + y.z * mat3.z.x,
            y.x * mat3.x.y + y.y * mat3.y.y + y.z * mat3.z.y,
            y.x * mat3.x.z + y.y * mat3.y.z + y.z * mat3.z.z,
        ), Vec3(
            z.x * mat3.x.x + z.y * mat3.y.x + z.z * mat3.z.x,
            z.x * mat3.x.y + z.y * mat3.y.y + z.z * mat3.z.y,
            z.x * mat3.x.z + z.y * mat3.y.z + z.z * mat3.z.z,

        )
    )
}

interface BwElement{
    var beat: Double
}

interface BwObject: BwElement{
    var position: Vec3
    var rotation: Vec3
    var localRotation: Vec3
    var color: Color?
    var noteJumpMovementSpeed: Double?
    var noteJumpStartBeatOffset: Double?
    var fake: Boolean
    var interactable: Boolean
    var gravity: Boolean
    var track: String?
}

data class BwNote(
    var cutDirection: Double,
    var type: NoteType,
    var flipLineIndex: Double,
    var flipJump: Double,
    override var color: Color?,
    override var beat: Double,
    override var position: Vec3,
    override var rotation: Vec3,
    override var localRotation: Vec3,
    override var noteJumpMovementSpeed: Double?,
    override var noteJumpStartBeatOffset: Double?,
    override var fake: Boolean,
    override var interactable: Boolean,
    override var gravity: Boolean,
    override var track: String?,
): BwObject

enum class NoteType(type: Int){
    Red(1),
    Blue(2),
    Bomb(3),
}

data class BwEvent(
    var type: Int,
    var value: Int,
    var propID: Int,
    var lightID: Int,
    override var beat: Double,
): BwElement
