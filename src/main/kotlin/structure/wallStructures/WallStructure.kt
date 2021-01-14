@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package structure.wallStructures

import structure.ObjectStructure
import structure.helperClasses.BwObstacle
import structure.math.Vec3
import types.BwDouble
import types.BwInt
import types.bwDouble
import types.bwInt
import kotlin.math.pow

/*
This is the relevant File for the creation of all WallStructures

Structure of this Document
sealed class to allow for save calls in the assetParser
this file contains only documentation of the structures and calls run for each
in the specialStructures Folder the run and relevant Functions are defined via extension Functions

so how do i add a structure
Define the parameters and documentation of the structure here as usual. You dont need to define the parameters already defined in the Wallstructure class
add the run Function in the relevant file in the specialStructures folder You can create Walls with SpookyWall and add them with add(Wall) or add(Collection of walls).
submit a pull request and wait for approval.

To allow for stuff like easing you need to use the Types BwDouble/Int/Point/etc.
To set them to a default single value use the functions below
*/


abstract class WallStructure: ObjectStructure() {
    /**
     * mirrors the SpookyWall. Default: 0. click me:
     *
     *  0 -> dont mirror,
     *
     *  1-> mirror to the other side,
     *
     *  2-> mirror to the other side and duplicate,
     *
     *  3-> mirror horizontal on y=2
     *
     *  4-> mirror horizontal and duplicate
     *
     *  5-> mirror on the center of x=0, y=2
     *
     *  6-> mirror on the center and duplicate
     *
     *  7-> mirror 1 and 6
     *
     *  8-> mirror on the center and on the other side and duplicate all 4
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
     * scales the Duration and startTime, (duration only for positive duration).
     * This is useful for making a structure, that is one beat long longer or shorter
     */
    var scale: BwDouble = bwDouble(1)

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
    open fun name() = this::class.simpleName ?: throw ClassNotFoundException("class does not have a name")

    abstract fun createWalls(): List<BwObstacle>

    override fun createObjects(): List<BwObstacle> {
        val c = createWalls()
        val l = mutableListOf<BwObstacle>()

        reverse(c)
        speedUp(c)
        for ((i, o) in c.withIndex()) {
            // Set the progress Variable so easing works
            structureState.progress = i.toDouble() / c.size

            adjust(o)
            fit(o)
            l.addAll(mirror(o))
        }
        return l.toMutableList()
    }

    private fun adjust(o: BwObstacle) {
        o.scale.x = changeWidth?.invoke() ?: o.scale.x
        o.scale.y = changeHeight?.invoke() ?: o.scale.y
        o.scale.z = changeDuration?.invoke() ?: o.scale.z

        o.scale.x *= scaleWidth.invoke()
        o.scale.y *= scaleHeight.invoke()
        o.scale.z *= scaleDuration.invoke()

        o.position.z *= scale.invoke()
        if (o.scale.z > 0)
            o.scale.z *= scale.invoke()

        o.scale.x += addWidth.invoke()
        o.scale.y += addHeight.invoke()
        o.scale.z += addDuration.invoke()
    }
    private fun fit(o: BwObstacle){
        if (fitX != null) {
            o.scale.x = fitScale(o.position.x, o.scale.x, fitX!!.invoke())
            o.position.x = fitPos(fitX!!.invoke(), o.scale.x)
        }
        if (fitY != null) {
            o.scale.y = fitScale(o.position.y, o.scale.y, fitY!!.invoke())
            o.position.y = fitPos(fitY!!.invoke(), o.scale.y)
        }
        if (fitZ != null) {
            o.scale.z = fitScale(o.position.z, o.scale.z, fitZ!!.invoke())
            o.position.z = fitPos(fitZ!!.invoke(), o.scale.z)
        }
    }
    private fun fitScale(pos: Double, scale: Double, fit: Double) =
        pos + scale/2 - fit
    private fun fitPos(fit: Double, scale: Double) =
        fit + scale/2

    private fun reverse(list: List<BwObstacle>) {
        val minX: Double = list.maxOfOrNull { it.position.x + it.scale.x / 2 } ?: 0.0
        val maxX: Double = list.maxOfOrNull { it.position.x + it.scale.x / 2 } ?: 0.0
        val minY: Double = list.maxOfOrNull { it.position.y + it.scale.y / 2 } ?: 0.0
        val maxY: Double = list.maxOfOrNull { it.position.y + it.scale.y / 2 } ?: 0.0
        val minZ: Double = list.maxOfOrNull { it.position.z + it.scale.z / 2 } ?: 0.0
        val maxZ: Double = list.maxOfOrNull { it.position.z + it.scale.z / 2 } ?: 0.0
        val center = Vec3(maxX - minX, maxY - minY, maxZ - minZ)
        for (o in list) {
            if (reverseX)
                o.position.x = 2 * center.x - o.position.x
            if (reverseY)
                o.position.y = 2 * center.y - o.position.y
            if (reverseX)
                o.position.z = 2 * center.z - o.position.z
        }
    }


    // im not changing that, fuck you, math is hard.
    private fun speedUp(l: List<BwObstacle>){
        if(speeder != null){
            val maxZ = l.maxOfOrNull { it.position.z + it.scale.z/2  }?: 0.0
            val s = speeder?.invoke()?: 1.0
            l.forEach { wall ->
                wall.position.z = wall.position.z.pow(s)
                if (wall.scale.z > 0)
                    wall.scale.z = wall.scale.z.pow(s)
            }

            val newMaxZ = l.maxOfOrNull { it.position.z + it.scale.z/2 }?: 0.0
            val mult = 1/(newMaxZ)*maxZ
            l.forEach {
                it.position.z *= mult
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
            val pos = wall.position.copy(x = 2*mirrorX() - wall.position.x)
            val scale = wall.scale.copy(x = 2*mirrorX() - wall.position.x)
            val rot = wall.rotation.copy(y =  mult *wall.rotation.y, z = mult * wall.rotation.z)
            val localRot = wall.localRotation.copy(y =  mult *wall.localRotation.y, z = mult * wall.localRotation.z)

            wall.copy(
                position = pos,
                scale = scale,
                rotation = rot,
                localRotation = localRot
            )
        }
    }

    internal fun mirrorY(list: List<BwObstacle>): List<BwObstacle> {
        val mult = if (mirrorRotation) -1.0 else  1.0
        return list.map { wall ->
            val pos = wall.position.copy(y = 2*mirrorY() - wall.position.y)
            val scale = wall.scale.copy(y = 2*mirrorY() - wall.position.y)
            val rot = wall.rotation.copy(x =  mult *wall.rotation.x, z = mult * wall.rotation.z)
            val localRot = wall.localRotation.copy(x =  mult *wall.localRotation.z, z = mult * wall.localRotation.z)

            wall.copy(
                position = pos,
                scale = scale,
                rotation = rot,
                localRotation = localRot
            )
        }
    }
}


