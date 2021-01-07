@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package structure.wallStructures

import structure.Structure
import structure.helperClasses.*
import types.*
import java.lang.Exception
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


abstract class WallStructure: Structure() {
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
     * makes the Wall appear right on the beat by adding the njsOffset, default: true
     */
    var time: Boolean = true

    /**
     * change The StartRow of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeX: BwDouble? = null

    /**
     * change the Width of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeWidth: BwDouble? = null

    /**
     * change The StartHeight of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeY: BwDouble? = null

    /**
     * change The Height of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeHeight: BwDouble? = null

    /**
     * change The StartTime of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeZ: BwDouble? = null

    /**
     * change The Duration of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeDuration: BwDouble? = null

    /**
     * multiplies the StartRow of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleX: BwDouble = bwDouble(1)

    /**
     * multiplies the Width of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleWidth: BwDouble = bwDouble(1)

    /**
     * multiplies the StartHeight of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleY: BwDouble = bwDouble(1)

    /**
     * multiplies the Height of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleHeight: BwDouble = bwDouble(1)

    /**
     * multiplies the StartTime of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleZ: BwDouble = bwDouble(1)

    /**
     * multiplies the Duration of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleDuration: BwDouble = bwDouble(1)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addX: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addWidth: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addY: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addHeight: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addZ: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addDuration: BwDouble = bwDouble(0)

    /**
     * increases or decreases the width of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    var fitX: BwDouble? = null

    /**
     * increases or decreases the StartRow of all walls until they have the the specific Width. Random possible with random(min,max). default: null (does nothing)
     */
    var fitWidth: BwDouble? = null

    /**
     * increases or decreases the height of all walls until they have the the specific startHeight. Random possible with random(min,max). default: null (does nothing)
     */
    var fitY: BwDouble? = null

    /**
     * increases or decreases the StartHeight of all walls until they have the the specific Height. Random possible with random(min,max). default: null (does nothing)
     */
    var fitHeight: BwDouble? = null

    /**
     * increases or decreases the duration of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    var fitZ: BwDouble? = null

    /**
     * increases or decreases the StartTime of all walls until they have the the specific duration. Random possible with random(min,max). default: null (does nothing)
     */
    var fitDuration: BwDouble? = null

    /**
     * scales the Duration and startTime, (duration only for positive duration).
     * This is useful for making a structure, that is one beat long longer or shorter
     */
    var scale: BwDouble = bwDouble(1)

    /**
     * reverses the WallStructure on the Starttime/duration. Default: false
     */
    var reverse: Boolean = false

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
     * The Color of the Wallstructure. Click me to see examples
     *
     * ```yaml
     * color: red
     * # turns the entire Wallstructure red.
     * All available colors are here: https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
     *
     * # creates a rainbow :)
     * color: rainbow
     *
     * # also creates a rainbow :), but this one changes 1.5 times as fast as the default one
     * color: rainbow(1.5)
     *
     * # Picks a random color for each wall
     * color: random(blue,Green,cyan)
     *
     * # changes color in the order red, green, blue, yellow.
     * color: flash(red, green, blue, yellow)
     *
     * # gradient from Red to Furry))
     * color: gradient(Red,Cyan)
     * ```
     *
     */
    var color: BwColor? = null

    /**
     * The rotation of the wallstructure around the player, think 360 maps around the X Achsis.
     *  Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotation(true,false)
     */
    var rotationX: BwDouble = bwDouble(0)

    /**
     * The rotation of the wallstructure around the player, think 360 maps around the X Achsis.
     *  Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotation(true,false)
     */
    var rotationY: BwDouble = bwDouble(0)

    /**
     * The rotation of the wallstructure around the player, think 360 maps around the X Achsis.
     *  Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotation(true,false)
     */
    var rotationZ: BwDouble = bwDouble(0)

    /**
     * Defines, if mirror also effects the rotation. Can be true or false. Default: true
     */
    var mirrorRotation: Boolean = true

    /**
     * localRotX controls the rotation on the x-axis for each individual wall in degree. allows random. Default: 0
     */
    var localRotX: BwDouble = bwDouble(0)

    /**
     * localRotY controls the rotation on the y-axis for each individual Wall in degree. allows random. Default: 0
     */
    var localRotY: BwDouble = bwDouble(0)

    /**
     * * localRotZ controls the rotation on the x-axis for each individual Wall in degree. allows random. Default: 0
     */
    var localRotZ: BwDouble = bwDouble(0)

    /**
     * some Wallstructures use Random walls. This is the seed for them
     */
    var seed: BwInt? = null

    /**
     * Assign all Walls in this Wallstructure to a specific Track
     */
    var track: String? = null

    /**
     * turns the entire WallStructure into bombs. only available, if deleteAllPrevious is set.
     */
    var bombs: Boolean = false

    /**
     * Set the NJS of all walls.
     * Part of NE: https://github.com/Aeroluna/NoodleExtensions/blob/master/README.md
     */
    var noteJumpMovementSpeed: BwDouble? = null

    /**
     * Set the spawn offset of an individual object
     * Part of NE: https://github.com/Aeroluna/NoodleExtensions/blob/master/README.md
     */
    var noteJumpStartBeatOffset: BwDouble? = null

    /**
     * When true, causes the note/wall to not show up in the note/wall count and to not count towards score in any way
     * Part of NE: https://github.com/Aeroluna/NoodleExtensions/blob/master/README.md
     */
    var fake: Boolean = false

    /**
     * When false, the note/wall cannot be interacted with.
     * This means notes cannot be cut and walls will not interact with sabers/putting your head in the wall.
     * Notes will still count towards your score.
     * Part of NE: https://github.com/Aeroluna/NoodleExtensions/blob/master/README.md
     */
    var interactable: Boolean = true

    /**
     * When true, notes will no longer do their animation where they float up.
     * Part of NE: https://github.com/Aeroluna/NoodleExtensions/blob/master/README.md
     */
    var disableNoteGravity: Boolean = false


    /** returns the name of the structure */
    open fun name() = this::class.simpleName ?: throw ClassNotFoundException("class does not have a name")

    abstract fun create(): List<SpookyWall>

    override fun createElements(): List<SpookyWall> {
        val c = create()
        val l = mutableListOf<SpookyWall>()
        val center = center(c)

        speedUp(c)
        for ((i, w) in c.withIndex()) {
            // Set the progress Variable so easing works
            structureState.progress = i.toDouble() / c.size

            adjust(w)
            fit(w)
            reverse(w, center)
            rotate(w)
            color(w)
            noodle(w)
            l.addAll(mirror(w))
        }
        return l.toMutableList()
    }

    private fun adjust(w: SpookyWall) {
        w.startRow = changeX?.invoke() ?: w.startRow
        w.width = changeWidth?.invoke() ?: w.width
        w.startHeight = changeY?.invoke() ?: w.startHeight
        w.height = changeHeight?.invoke() ?: w.height
        w.startTime = changeZ?.invoke() ?: w.startTime
        w.duration = changeDuration?.invoke() ?: w.duration

        w.duration *= scaleDuration.invoke()
        w.startTime *= scaleZ.invoke()
        w.height *= scaleHeight.invoke()
        w.startHeight *= scaleY.invoke()
        w.startRow *= scaleZ.invoke()
        w.width *= scaleWidth.invoke()

        w.startTime *= scale.invoke()
        if (w.duration > 0)
            w.duration *= scale.invoke()

        w.startRow += addX.invoke()
        w.width += addWidth.invoke()
        w.startHeight += addY.invoke()
        w.height += addHeight.invoke()
        w.startTime += addZ.invoke()
        w.duration += addDuration.invoke()
    }
    private fun fit(w: SpookyWall){
        if (fitX != null) {
            w.width = (w.startRow + (w.width.takeIf { i -> i > 0 } ?: 0.0)) - fitX!!.invoke()
            w.startRow = fitX!!.invoke()
        }
        if (fitWidth != null) {
            w.startRow = (w.startRow + (w.width.takeIf { i -> i > 0 } ?: 0.0)) - fitWidth!!.invoke()
            w.width = fitWidth!!.invoke()
        }
        if (fitY != null) {
            w.height = (w.startHeight + (w.height.takeIf { i -> i > 0 } ?: 0.0)) - fitY!!.invoke()
            w.startHeight = fitY!!.invoke()
        }
        if (fitHeight != null) {
            w.startHeight = (w.startHeight + (w.height.takeIf { i -> i > 0 } ?: 0.0)) - fitHeight!!.invoke()
            w.height = fitHeight!!.invoke()
        }
        if (fitZ != null) {
            w.duration = (w.startTime + (w.duration.takeIf { i -> i > 0 } ?: 0.0)) - fitZ!!.invoke()
            w.startTime = fitZ!!.invoke()
        }
        if (fitDuration != null) {
            w.startTime = (w.startTime + (w.duration.takeIf { i -> i > 0 } ?: 0.0)) - fitDuration!!.invoke()
            w.duration = fitDuration!!.invoke()
        }
    }

    private fun reverse(w: SpookyWall, center: Point){
        if (reverseX){
            w.startRow = 2* center.x -w.startRow
            w.width = -w.width
        }
        if (reverseY){
            w.startHeight = 2* center.y - w.startHeight
            w.height *= -1
        }
        if (reverse){
            w.startTime = 2 * center.z - w.startTime
            w.height *= -1
        }
    }


    // im not changing that, fuck you, math is hard.
    private fun speedUp(l: List<SpookyWall>){
        if(speeder != null){
            val maxZ = l.maxBy { it.trueMaxPoint.z }?.trueMaxPoint?.z ?: 0.0
            l.forEach { wall ->
                wall.startTime = wall.startTime.pow(speeder!!())
                if (wall.duration > 0)
                    wall.duration = wall.duration.pow(speeder!!())
            }

            val newMaxZ = l.maxByOrNull { it.trueMaxPoint.z }?.trueMaxPoint?.z ?: 0.0
            val mult = 1/(newMaxZ)*maxZ
            l.forEach {
                it.startTime *= mult
                if(it.duration > 0)
                    it.duration *= mult
            }
        }
    }

    private fun rotate(w: SpookyWall){
        w.rotation = listOf(rotationX(), rotationY(), rotationZ())
        w.localRotation = listOf(localRotX(), localRotY(), localRotZ())
    }

    private fun color(w: SpookyWall){
        if (color != null)
            w.color = Color(color!!.r(), color!!.g(), color!!.b(), color!!.a())
    }

    protected fun noodle(w:SpookyWall){
        w.track = track
        w.noteJumpStartBeat = noteJumpMovementSpeed?.invoke()
        w.noteJumpStartBeatOffset = noteJumpStartBeatOffset?.invoke()
        // TODO Assign noodle Values
    }

    private fun mirror(w: SpookyWall): List<SpookyWall>{
        val l = listOf(w)
        return when(mirror()){
            0-> listOf(w)
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

    private fun center(l: List<SpookyWall>): Point {
        val minX = l.minByOrNull { spookyWall -> spookyWall.trueMinPoint.x }?.trueMinPoint?.x?: 0.0
        val maxX = l.maxByOrNull { spookyWall -> spookyWall.trueMaxPoint.x }?.trueMaxPoint?.x?: 0.0
        val minY = l.minByOrNull { spookyWall -> spookyWall.trueMinPoint.y }?.trueMinPoint?.y?: 0.0
        val maxY = l.maxByOrNull { spookyWall -> spookyWall.trueMaxPoint.y }?.trueMaxPoint?.y?: 0.0
        val minZ = l.minByOrNull { spookyWall -> spookyWall.trueMinPoint.z }?.trueMinPoint?.z?: 0.0
        val maxZ = l.maxByOrNull { spookyWall -> spookyWall.trueMaxPoint.z }?.trueMaxPoint?.z?: 0.0
        val centerX = minX + ((maxX-minX )/ 2)
        val centerY = minY + ((maxY-minY )/ 2)
        val centerZ = minZ + ((maxZ-minZ )/ 2)
        return Point(centerX, centerY, centerZ)
    }

    private fun mirrorX(list: List<SpookyWall>): List<SpookyWall> {
        return list.map { wall ->
            val rotation = if (mirrorRotation)
                listOf(
                    (wall.rotation.getOrNull(0)?: 0.0),
                    - (wall.rotation.getOrNull(1)?: 0.0),
                    - (wall.rotation.getOrNull(2)?: 0.0),
                )
            else wall.rotation
            val localRotation = if (mirrorRotation)
                listOf(
                    (wall.localRotation.getOrNull(0)?: 0.0),
                    - (wall.localRotation.getOrNull(1)?: 0.0),
                    - (wall.localRotation.getOrNull(2)?: 0.0),
                )
            else wall.localRotation

            wall.copy(
                startRow = 2*mirrorX()-wall.startRow,
                width = -wall.width,
                rotation = rotation,
                localRotation = localRotation

            ) }
    }
    internal fun mirrorY(list: List<SpookyWall>): List<SpookyWall> {
        return list.map { wall ->
            val rotation = if (mirrorRotation)
                listOf(
                    - (wall.rotation.getOrNull(0)?: 0.0),
                    (wall.rotation.getOrNull(1)?: 0.0),
                    - (wall.rotation.getOrNull(2)?: 0.0),
                )
            else wall.rotation
            val localRotation = if (mirrorRotation)
                listOf(
                    - (wall.localRotation.getOrNull(0)?: 0.0),
                    (wall.localRotation.getOrNull(1)?: 0.0),
                    - (wall.localRotation.getOrNull(2)?: 0.0),
                )
            else wall.localRotation
            wall.copy(
                startHeight = 2*mirrorY()-wall.startHeight,
                height = -wall.height,
                rotation = rotation,
                localRotation = localRotation,
            )
        }
    }
}


