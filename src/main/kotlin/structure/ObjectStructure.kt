package structure

import structure.bwElements.BwObject
import structure.bwElements.Color
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
            structureState.progress = i.toDouble() / bwObjects.size
            adjust(o)
            rotate(o)
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


    private fun adjust(o: BwObject) {
        o.position.x = changeX?.invoke() ?: o.position.x
        o.position.y = changeY?.invoke() ?: o.position.y
        o.position.z = changeZ?.invoke() ?: o.position.z

        o.position.x *= scaleX.invoke()
        o.position.y *= scaleY.invoke()
        o.position.z *= scaleZ.invoke()

        o.position.x += addX.invoke()
        o.position.y += addY.invoke()
        o.position.z += addZ.invoke()
    }

    private fun rotate(o: BwObject){
        o.rotation = Vec3(rotationX(), rotationY(), rotationZ())
        o.localRotation = Vec3(localRotX(), localRotY(), localRotZ())
    }

    private fun color(o: BwObject){
        if (color != null)
            o.color = Color(color!!.r(), color!!.g(), color!!.b(), color!!.a())
    }
}

