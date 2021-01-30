@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package structure.wallStructures

import structure.ObjectStructure
import structure.bwElements.BwObstacle
import structure.math.Vec3
import types.BwDouble
import types.BwInt
import types.bwDouble
import types.bwInt
import kotlin.math.pow

/**
 * Superclass of all Wallstructures.
 * To add a Structure visit the contributing page.
 */

abstract class WallStructure: ObjectStructure() {
    /**
     * mirrors the SpookyWall. Default: 0.
     *
     *  - 0 -> dont mirror,
     *  - 1-> mirror to the other side,
     *  - 2-> mirror to the other side and duplicate,
     *  - 3-> mirror horizontal on y=2
     *  - 4-> mirror horizontal and duplicate
     *  - 5-> mirror on the center of x=0, y=2
     *  - 6-> mirror on the center and duplicate
     *  - 7-> mirror 1 and 6
     *  - 8-> mirror on the center and on the other side and duplicate all 4
     */
    var mirror: BwInt = bwInt(0)

    /**
     * The x Position used for mirror. Default: 0
     */
    var mirrorX: BwDouble = bwDouble(0)

    /**
     * The Y Position used for mirror. Default: 2
     */
    var mirrorY: BwDouble = bwDouble(2)

    /**
     * Beatwalls differences the "Duration" and the "wallspeed".
     * Duration changes the actual scale of the value in the z achsis.
     * It is the same if it is -1 or 1.
     * overriding wallspeed on the other hand makes hyper and fast walls.
     * example: wallspeed: -2.
     * TODO maybe this can abstract away the generel speed?
     */
    var wallSpeed: BwDouble? = null
    /**
     * change the Width of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeWidth: BwDouble? = null

    /**
     * change The Height of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeHeight: BwDouble? = null

    /**
     * change The Duration of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeDuration: BwDouble? = null

    /**
     * multiplies the Width of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleWidth: BwDouble = bwDouble(1)

    /**
     * multiplies the Height of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleHeight: BwDouble = bwDouble(1)

    /**
     * multiplies the Duration of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleDuration: BwDouble = bwDouble(1)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addWidth: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addHeight: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addDuration: BwDouble = bwDouble(0)

    /**
     * increases or decreases the width of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    var fitX: BwDouble? = null

    /**
     * increases or decreases the height of all walls until they have the the specific startHeight. Random possible with random(min,max). default: null (does nothing)
     */
    var fitY: BwDouble? = null

    /**
     * increases or decreases the duration of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    var fitZ: BwDouble? = null

    /**
     * basically mirrors the Wallstructure in itself on the x-Achsis if set to true.
     */
    var reverseX: Boolean = false

    /**
     * basically mirrors the Wallstructure in itself on the x-Achsis if set to true
     */
    var reverseY: Boolean = false

    /**
     * speeds up the wallstructure over time. the duration of the whole structure. Remains. \n
     *
     * value 0-1 start is slower, speed up over time, \n
     *
     * 1-100 start is faster, slow down over time \n
     *
     * The closer the value is to 1, the more stale it gets.
     */
    var speeder: BwDouble? = null

    /**
     * some Wallstructures use Random walls. This is the seed for them
     */
    var seed: BwInt? = null

    /** returns the name of the structure */
    internal open fun name() = this::class.simpleName ?: throw ClassNotFoundException("class does not have a name")

    internal abstract fun createWalls(): List<BwObstacle>

    override fun createObjects(): List<BwObstacle> {

        val c = createWalls()
        // needed because of mirror
        val l = mutableListOf<BwObstacle>()

        reverse(c)
        speedUp(c)
        for ((i, o) in c.withIndex()) {
            // Set the progress Variable so easing works
            setProgress(i.toDouble() / c.size)

            adjust(o)
            fit(o)
            l.addAll(mirror(o))
        }
        return l.toList()
    }

    private fun adjust(o: BwObstacle) {
        o.scale.x = changeWidth?.invoke() ?: o.scale.x
        o.scale.y = changeHeight?.invoke() ?: o.scale.y
        o.scale.z = changeDuration?.invoke() ?: o.scale.z

        o.duration = wallSpeed?.invoke() ?: o.duration

        o.scale.x *= scaleWidth.invoke()
        o.scale.y *= scaleHeight.invoke()
        o.scale.z *= scaleDuration.invoke()

        o.scale.x += addWidth.invoke()
        o.scale.y += addHeight.invoke()
        o.scale.z += addDuration.invoke()
    }
    private fun fit(o: BwObstacle){
        if (fitX != null) {
            o.scale.x = fitScale(o.translation.x, o.scale.x, fitX!!.invoke())
            o.translation.x = fitPos(fitX!!.invoke(), o.scale.x)
        }
        if (fitY != null) {
            o.scale.y = fitScale(o.translation.y, o.scale.y, fitY!!.invoke())
            o.translation.y = fitPos(fitY!!.invoke(), o.scale.y)
        }
        if (fitZ != null) {
            o.scale.z = fitScale(o.translation.z, o.scale.z, fitZ!!.invoke())
            o.translation.z = fitPos(fitZ!!.invoke(), o.scale.z)
        }
    }
    private fun fitScale(pos: Double, scale: Double, fit: Double) =
        pos + scale/2 - fit
    private fun fitPos(fit: Double, scale: Double) =
        fit + scale/2

    private fun reverse(list: List<BwObstacle>) {
        val minX: Double = list.maxOfOrNull { it.translation.x + it.scale.x / 2 } ?: 0.0
        val maxX: Double = list.maxOfOrNull { it.translation.x + it.scale.x / 2 } ?: 0.0
        val minY: Double = list.maxOfOrNull { it.translation.y + it.scale.y / 2 } ?: 0.0
        val maxY: Double = list.maxOfOrNull { it.translation.y + it.scale.y / 2 } ?: 0.0
        val minZ: Double = list.maxOfOrNull { it.translation.z + it.scale.z / 2 } ?: 0.0
        val maxZ: Double = list.maxOfOrNull { it.translation.z + it.scale.z / 2 } ?: 0.0
        val center = Vec3(maxX - minX, maxY - minY, maxZ - minZ)
        for (o in list) {
            if (reverseX)
                o.translation.x = 2 * center.x - o.translation.x
            if (reverseY)
                o.translation.y = 2 * center.y - o.translation.y
            if (reverseX)
                o.translation.z = 2 * center.z - o.translation.z
        }
    }


    // im not changing that, fuck you, math is hard.
    private fun speedUp(l: List<BwObstacle>){
        if(speeder != null){
            val maxZ = l.maxOfOrNull { it.translation.z + it.scale.z/2  }?: 0.0
            val s = speeder?.invoke()?: 1.0
            l.forEach { wall ->
                wall.translation.z = wall.translation.z.pow(s)
                if (wall.scale.z > 0)
                    wall.scale.z = wall.scale.z.pow(s)
            }

            val newMaxZ = l.maxOfOrNull { it.translation.z + it.scale.z/2 }?: 0.0
            val mult = 1/(newMaxZ)*maxZ
            l.forEach {
                it.translation.z *= mult
                if(it.scale.z > 0)
                    it.scale.z *= mult
            }
        }
    }

    private fun mirror(o: BwObstacle): List<BwObstacle> {
        val l = listOf(o)
        return when(mirror()){
            0-> listOf(o)
            1-> mirrorX(l)
            2-> l + mirrorX(l)
            3-> mirrorY(l)
            4-> l + mirrorY(l)
            5-> mirrorY(mirrorX(l))
            6-> l + mirrorY(mirrorX(l))
            7-> mirrorX(l) + mirrorY(l)
            8-> l + mirrorX(l) + mirrorY(l) + mirrorY(mirrorX(l))
            else -> throw Exception("Not a valid mirror code")
        }
    }

    private fun mirrorX(list: List<BwObstacle>): List<BwObstacle> {
        val mult = if (mirrorRotation) -1.0 else  1.0
        return list.map { wall ->
            val pos = wall.translation.copy(x = 2*mirrorX() - wall.translation.x)
            val scale = wall.scale.copy(x = -1*wall.scale.x)
            val rot = wall.globalRotation.copy(y =  mult *wall.globalRotation.y, z = mult * wall.globalRotation.z)
            val localRot = wall.rotation.copy(y =  mult *wall.rotation.y, z = mult * wall.rotation.z)

            wall.copy(
                translation = pos,
                scale = scale,
                globalRotation = rot,
                rotation = localRot
            )
        }
    }

    internal fun mirrorY(list: List<BwObstacle>): List<BwObstacle> {
        val mult = if (mirrorRotation) -1.0 else  1.0
        return list.map { wall ->
            val pos = wall.translation.copy(y = 2*mirrorY() - wall.translation.y)
            val scale = wall.scale.copy(y = -1 * wall.scale.y)
            val rot = wall.globalRotation.copy(x =  mult *wall.globalRotation.x, z = mult * wall.globalRotation.z)
            val localRot = wall.rotation.copy(x =  mult *wall.rotation.x, z = mult * wall.rotation.z)

            wall.copy(
                translation = pos,
                scale = scale,
                globalRotation = rot,
                rotation = localRot
            )
        }
    }
}


