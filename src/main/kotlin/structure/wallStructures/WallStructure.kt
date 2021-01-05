@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package structure.wallStructures

import structure.Structure
import structure.helperClasses.NoRotation
import structure.helperClasses.RotationMode
import structure.helperClasses.SpookyWall
import types.*

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


abstract class WallStructure: Structure()
{
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
    var scale: BwDouble? = null

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
     * The rotation of the wallstructure around the player, think 360 maps. click me.
     * This rotates on the Y Axe and is left in for backwards compability.
     * Use rotation(X/Y/Z) from now on.
     *
     * ```
     * # rotates the entire Wallstructure 90 degrees
     * rotation: 90
     *
     * # also rotates the entire Wallstructure 90 degrees. You can use negative values as well
     * rotation: 450
     *
     * # rotates the walls linear from 45 to 90.
     * rotation: ease(45,90)
     *
     * # rotates the walls from 180 to 90 degrees using easeInOutQuad.
     * # all Easing methods can be found here https://easings.net/en
     * rotation: ease(180,90,easeInOutQuad)
     *
     * # switches the rotation between the given values. here the first wall will have rotation 24, ththe second one 48 the third one 50, the fourth 24  and so forth
     * rotation: switch(24,48,50)
     *
     * # rotates all the way around the player (360 degrees)
     * rotation: circle
     *
     * # rotates twice all the way around the player backwards
     * rotation: circle(-2)
     * ```
     *
     * default: noRotation
     *
     *  Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotation(true,false)
     */
    var rotation: RotationMode = NoRotation

    /**
     * The rotation of the wallstructure around the player, think 360 maps around the X Achsis.
     *
     * ```
     * # rotates the entire Wallstructure 90 degrees
     * rotationX: 90
     *
     * # also rotates the entire Wallstructure 90 degrees. You can use negative values as well
     * rotationX: 450
     *
     * # rotates the walls linear from 45 to 90.
     * rotationX: ease(45,90)
     *
     * # rotates the walls from 180 to 90 degrees using easeInOutQuad.
     * # all Easing methods can be found here https://easings.net/en
     * rotationX: ease(180,90,easeInOutQuad)
     *
     * # switches the rotationX between the given values. here the first wall will have rotationX 24, ththe second one 48 the third one 50, the fourth 24  and so forth
     * rotationX: switch(24,48,50)
     *
     * # rotates all the way around the player (360 degrees)
     * rotationX: circle
     *
     * # rotates twice all the way around the player backwards
     * rotationX: circle(-2)
     * ```
     *
     * default: noRotation
     *
     *  Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotation(true,false)
     */
    var rotationX: RotationMode = NoRotation

    /**
     * The rotation of the wallstructure around the player, think 360 maps around the X Achsis.
     *
     * ```
     * # rotates the entire Wallstructure 90 degrees
     * rotationY: 90
     *
     * # also rotates the entire Wallstructure 90 degrees. You can use negative values as well
     * rotationY: 450
     *
     * # rotates the walls linear from 45 to 90.
     * rotationY: ease(45,90)
     *
     * # rotates the walls from 180 to 90 degrees using easeInOutQuad.
     * # all Easing methods can be found here https://easings.net/en
     * rotationY: ease(180,90,easeInOutQuad)
     *
     * # switches the rotationY between the given values. here the first wall will have rotationY 24, ththe second one 48 the third one 50, the fourth 24  and so forth
     * rotationY: switch(24,48,50)
     *
     * # rotates all the way around the player (360 degrees)
     * rotationY: circle
     *
     * # rotates twice all the way around the player backwards
     * rotationY: circle(-2)
     * ```
     *
     * default: noRotation
     *
     *  Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotation(true,false)
     */
    var rotationY: RotationMode = NoRotation

    /**
     * The rotation of the wallstructure around the player, think 360 maps around the X Achsis.
     *
     * ```
     * # rotates the entire Wallstructure 90 degrees
     * rotationZ: 90
     *
     * # also rotates the entire Wallstructure 90 degrees. You can use negative values as well
     * rotationZ: 450
     *
     * # rotates the walls linear from 45 to 90.
     * rotationZ: ease(45,90)
     *
     * # rotates the walls from 180 to 90 degrees using easeInOutQuad.
     * # all Easing methods can be found here https://easings.net/en
     * rotationZ: ease(180,90,easeInOutQuad)
     *
     * # switches the rotationZ between the given values. here the first wall will have rotationZ 24, ththe second one 48 the third one 50, the fourth 24  and so forth
     * rotationZ: switch(24,48,50)
     *
     * # rotates all the way around the player (360 degrees)
     * rotationZ: circle
     *
     * # rotates twice all the way around the player backwards
     * rotationZ: circle(-2)
     * ```
     *
     * default: noRotation
     *
     *  Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotation(true,false)
     */
    var rotationZ: RotationMode = NoRotation

    /**
     * Defines, if mirror also effects the rotation. Can be true or false. Default: true
     */
    var mirrorRotation: Boolean = true

    /**
     * localRotX controls the rotation on the x-axis for each individual wall in degree. allows random. Default: 0
     *
     *  example:
     *
     *  ```yaml
     *  localRotX: 20 # rotates each wall 20 degree to the right
     *  localRotX: random(-20,20) # rotates each wall on the x axis randomnly between -20 and 20.
     *  ```
     */
    var localRotX: RotationMode = NoRotation

    /**
     * localRotY controls the rotation on the y-axis for each individual Wall in degree. allows random. Default: 0
     *
     *  example:
     *
     *  ```yaml
     *  localRotY: 20 # rotates each wall 20 degree to the right
     *  localRotY: random(-20,20) # rotates each wall on the x axis randomnly between -20 and 20.
     *  ```
     */
    var localRotY: RotationMode = NoRotation
    /**
     * * localRotZ controls the rotation on the x-axis for each individual Wall in degree. allows random. Default: 0
    *
    *  example:
    *
    *  ```yaml
    *  localRotZ: 20 # rotates each wall 20 degree to the right
    *  localRotZ: random(-20,20) # rotates each wall on the x axis randomnly between -20 and 20.
     *  ```
    */
    var localRotZ: RotationMode = NoRotation

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
     * changes the NJMS of a single specific wall.
     * This wil change the speed a wall will have
     */
    var noteJumpMovementSpeed: BwDouble? = null

    /**
     * I have no idea, ask cyan
     */
    var noteJumpMovementSpeedOffset: BwDouble? = null

    /** returns the name of the structure */
    open fun name() = this::class.simpleName ?: throw ClassNotFoundException("class does not have a name")

    override fun createElements(): List<SpookyWall> {
        TODO("Not yet implemented")
    }
    abstract fun generate(): List<SpookyWall>
}
