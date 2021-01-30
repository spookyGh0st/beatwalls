package structure

import structure.bwElements.BwObject
import structure.math.Vec3
import types.BwColor
import types.BwDouble
import types.bwDouble

/**
 * A Object Structure can be applied on Obstacles and Notes alike.
 */
abstract class ObjectStructure: Structure() {

    abstract fun createObjects(): List<BwObject>

    /**
     * change The StartRow of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeX: BwDouble? = null

    /**
     * change The StartHeight of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeY: BwDouble? = null

    /**
     * change The StartTime of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeZ: BwDouble? = null

    /**
     * multiplies the StartRow of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleX: BwDouble = bwDouble(1)

    /**
     * multiplies the StartHeight of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleY: BwDouble = bwDouble(1)

    /**
     * multiplies the StartTime of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleZ: BwDouble = bwDouble(1)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addX: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addY: BwDouble = bwDouble(0)

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addZ: BwDouble = bwDouble(0)

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
     * The Color of the Wallstructure. Supports various functions.
     *
     * ```yaml
     * color: green # Turns the entire Structure green
     * color: rainbow(2,0.5) # Creates a Rainbow with 2 repetitions and optimally set's alpha = 0.5
     * color: gradient(red,blue,cyan)   # creates a gradient through the given colors
     * color: random(yellow,green,pink) # Randomly picks a color for each walls
     * color: between(blue,red)         # Picks a random color between blue and red
     *
     * # You can also create your own colors
     * color myColor:
     *   red:   0.2
     *   green: 0.4
     *   blue:  0,6
     *   alpha: 0.8
     * ```
     */
    var color: BwColor? = null

    /**
     * Defines, if mirror also effects the rotation. Can be true or false. Default: true
     */
    var mirrorRotation: Boolean = true

    /**
     * localRotX controls the rotation on the x-axis for each individual wall in degree. allows random. Default: 0
     * NOTE: the position translation to NE is currently broken. Use with care
     */
    var localRotX: BwDouble = bwDouble(0)

    /**
     * localRotY controls the rotation on the y-axis for each individual Wall in degree. allows random. Default: 0
     * NOTE: the position translation to NE is currently broken. Use with care
     */
    var localRotY: BwDouble = bwDouble(0)

    /**
     * * localRotZ controls the rotation on the x-axis for each individual Wall in degree. allows random. Default: 0
     */
    var localRotZ: BwDouble = bwDouble(0)

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
     * Assign all Objects in this Wallstructure to a specific Track
     */
    var track: String? = null

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
     * When false, notes will no longer do their animation where they float up.
     * Part of NE: https://github.com/Aeroluna/NoodleExtensions/blob/master/README.md
     * Opposite of original value, default: true
     */
    var gravity: Boolean = true

    override fun createElements(): List<BwObject> {
        val bwObjects = createObjects()
        val l = mutableListOf<BwObject>()
        for ((i, o) in bwObjects.withIndex()){
            setProgress(i.toDouble() / bwObjects.size)
            translate(o)
            rotate(o)
            scale(o)
            color(o)
            noodle(o)
            l.add(o)
        }
        return l.toList()
    }

    private fun noodle(o: BwObject){
        o.track = track
        o.fake = fake
        o.noteJumpMovementSpeed = noteJumpMovementSpeed?.invoke()
        o.noteJumpStartBeatOffset = noteJumpStartBeatOffset?.invoke()
        o.gravity = gravity
    }

    private fun translate(o: BwObject) {
        o.translation.x = changeX?.invoke() ?: o.translation.x
        o.translation.y = changeY?.invoke() ?: o.translation.y
        o.translation.z = changeZ?.invoke() ?: o.translation.z

        o.translation.x *= scaleX.invoke()
        o.translation.y *= scaleY.invoke()
        o.translation.z *= scaleZ.invoke()

        o.translation.x += addX.invoke()
        o.translation.y += addY.invoke()
        o.translation.z += addZ.invoke()
    }

    private fun scale(o: BwObject){
        o.scale.x = changeWidth?.invoke() ?: o.scale.x
        o.scale.y = changeHeight?.invoke() ?: o.scale.y
        o.scale.z = changeDuration?.invoke() ?: o.scale.z

        o.scale.x *= scaleWidth.invoke()
        o.scale.y *= scaleHeight.invoke()
        o.scale.z *= scaleDuration.invoke()

        o.scale.x += addWidth.invoke()
        o.scale.y += addHeight.invoke()
        o.scale.z += addDuration.invoke()

    }

    private fun rotate(o: BwObject){
        o.globalRotation += Vec3(rotationX(), rotationY(), rotationZ())
        o.rotation += Vec3(localRotX(), localRotY(), localRotZ())
    }

    private fun color(o: BwObject){
        o.color = color?.invoke()?: o.color
    }
}

