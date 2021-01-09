package structure.helperClasses

import kotlin.math.sqrt

data class Vec3(var x: Double, var y: Double, var z: Double){
    constructor(x: Number, y: Number, z: Number): this(x.toDouble(), y.toDouble(), z.toDouble())
    constructor(): this(0.0, 0.0, 0.0)

    val length
    get() = sqrt(x*x + y*y + z*z)

    /**
     * Normalise the vector to length 1
     */

    fun normalize(){
        val l = 1.0/ sqrt(x*x + y*y + z*z);
        x*=l; y*= l; z*=l
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
}

operator fun Double.times(vec3: Vec3): Vec3 {
    return Vec3(vec3.x*this, vec3.y*this, vec3.z*this)
}

operator fun Int.times(vec3: Vec3): Vec3 {
    return Vec3(vec3.x*this, vec3.y*this, vec3.z*this)
}

interface BwElement

interface BwObject: BwElement{
    var beat: Double
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
    var beat: Double,
    var type: Int,
    var value: Int,
    var propID: Int,
    var lightID: Int
): BwElement
