@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package structure

import assetFile.BwDouble
import assetFile.BwInt
import structure.helperClasses.*
import structure.specialStrucures.*
import kotlin.random.Random

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

fun bwDouble(num: Number):BwDouble = { num.toDouble()}
fun bwInt(num: Number):BwInt = { num.toInt()}

sealed class WallStructure:Structure()
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
     * color: red
     * # turns the entire Wallstructure red.
     * All available colors are here: https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
     *
     * color: 255,0,0
     * # also turns the entire WallStructure red, but uses rgb values.
     *
     * color: rainbow
     * # creates a rainbow :)
     *
     * color: rainbow(1.5)
     * # also creates a rainbow :), but this one changes 1.5 times as fast as the default one
     *
     * color: flash(green)
     * # Flashes between green and the default color
     *
     * color: flash(128,128,128,Green)
     * # Flashes between Dark Gray (128,128,128) and Green.
     *
     * color: flash(red, green, blue, yellow)
     * # changes color in the order red, green, blue, yellow.
     *
     * color: gradient(Red,Cyan)
     * # gradient from Red to Furry (Cyan))
     *
     */
    var color: ColorMode = NoColor

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

    /** generates the walls */
    abstract override fun generate():List<SpookyWall>

    /** returns the name of the structure */
    open fun name() = this::class.simpleName ?: throw ClassNotFoundException("class does not have a name")
}

/**
 * dont touch
 */
class EmptyWallStructure:WallStructure() { override fun generate() = emptyList<SpookyWall>() }

//   _____                 _       __   _       __      _________ __                  __
//  / ___/____  ___  _____(_)___ _/ /  | |     / /___ _/ / / ___// /________  _______/ /___  __________  _____
//  \__ \/ __ \/ _ \/ ___/ / __ `/ /   | | /| / / __ `/ / /\__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// ___/ / /_/ /  __/ /__/ / /_/ / /    | |/ |/ / /_/ / / /___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///____/ .___/\___/\___/_/\__,_/_/     |__/|__/\__,_/_/_//____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/
//    /_/

/**
 * Cannot be directly called, but instead must be called using the Define: Parameter
 */
class CustomWallStructure(
    override val name: String,
    override val superStructure: WallStructure,
    override val structures: List<WallStructure>
) : WallStructure(), CustomStructInterface{
    override fun generate(): List<SpookyWall> {
        return superStructure.generate() + structures.flatMap { it.generate() }
    }
}
/**
 * Random Noise (small mini cubes)
 */
class RandomNoise:WallStructure(){
    /**
     * the amount of the created Walls, if no value is given creates 8x the beatcount
     */
    var amount: Int? = null

    /**
     * controls one corner of the Area
     */
    var p1 = Point(-6, 0, 0)

    /**
     * controls the other corner of the Area
     */
    var p2 = Point(6, 5, 1)

    /**
     * avoids spawning structures in the playspace. default: true
     */
    var avoidCenter: Boolean = true
    /**
     * generating the Walls
     */
    override fun generate() = run()
}

/**
 * 3d Grid
 */
class FurryGrid : WallStructure() {
    /**
     * the X-Size of one panel in the grid
     */
    var panelX = 1.0

    /**
     * the Y-Size of one panel in the grid
     */
    var panelY = 0.0

    /**
     * the Z-Size of one panel in the grid
     */
    var panelZ = 1.0

    /**
     * the X-Size of the whole grid, aka how often it will repeat in the X-direction
     */
    var gridX = 8

    /**
     * the Y-Size of the whole grid, aka how often it will repeat in the Y-direction
     */
    var gridY = 1

    /**
     * the Z-Size of the whole grid aka how often it will repeat in the Z-direction
     */
    var gridZ = 8

    /**
     * different modes of walls
     *
     * 0 = create every wall in the pattern
     *
     * 1 = chess-pattern
     *
     * want more? write me
     */
    var mode = 0

    /**
     * the start Point of the grid
     */
    var p1: Point =
        Point(-4, 0, 0)

    /**
     * generating the Walls
     */
    override fun generate() = run()
}

/**
 * draws multiple lines around Sections of the cuboid.
 */
class RandomCuboidLines : WallStructure() {
    /**
     * the first corner of the cuboid. Default is -2,0,0
     */
    var p1: Point =
        Point(-2, 0, 0)

    /**
     * the second corner of the cuboid. Default is 2,4,8
     */
    var p2: Point = Point(2, 4, 4)

    /**
     * The amount of walls per line. Default is 8
     */
    var amount: Int = 8

    /**
     * The amount of lines that will be created. Defaults to the duration
     */
    var count: Int? = null

    /**
     * In how many sections will each side/floor be splitted. Must be at least 3. Default: 4
     */
    var sections: Int = 4

    /**
     * 2 = only sides, 4 - bottom and top aswell
     */
    var randomSidePicker: Int = 4
    /**
     * generating the Walls
     */
    override fun generate() = run()
}

/**
 * Draw a curve of Walls. This uses BezierCurve. You can imagine it like a line between point 1 and point 4, that gets pulled upon by the controlpoints. Maybe this link can help (the dots are the Points) https://www.desmos.com/calculator/cahqdxeshd
 */
class Curve : WallStructure() {
    /**
     * the start Point of the Curve
     */
    var p1: Point = Point(0, 0, 0)

    /**
     * the first Controllpoint, defaults to the startPoint
     */
    var p2: Point? = null

    /**
     * second ControlPoint, defaults to the end point
     */
    var p3: Point? = null

    /**
     * The EndPoint of the Curve
     */
    var p4: Point = Point(0, 0, 0)

    /**
     * amount of Walls
     */
    var amount: Int = 8

    /**
     * generating the Walls
     */
    override fun generate() = run()
}

/**
 * Draw a steady curve of Walls. that is exactly 1 beat long
 * */
class SteadyCurve:WallStructure(){
    /**
     * the start Point of the Curve
     */
    var p1: Point = Point(0, 0, 0)

    /**
     * the first Controllpoint, defaults to the startPoint
     */
    var p2: Point? = null

    /**
     * second ControlPoint, defaults to the end point
     */
    var p3: Point? = null

    /**
     * The EndPoint of the Curve
     */
    var p4: Point = Point(0, 0, 1)

    /**
     * amount of Walls
     */
    var amount: Int = 8

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}

/**
 * Define your own WallStructure from existing WallStructures.
 */
class Define: WallStructure() {
    /**
     * the name the structure gets saved to
     */
    var name: String = "customStructure"
    /**
     * The name of Different Structures. Separated by comma (example: structures: Floor, Ceiling)
     * You can also define Parameters of the first Structure
     * These get loaded in Order, So if your reference defined Structures, those must be listed before that
     * The Beat Value gets every time, so it should be 0 most of the time
     */
    var structures: List<WallStructure> = listOf()

    /**
     * dont touch
     */
    var isTopLevel = false

    /**
     * generating the Walls
     */
    override fun generate() = run()

    override fun name(): String {
        if (isTopLevel)
            return structures.first().name()
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Define

        if (name != other.name) return false
        if (structures != other.structures) return false
        if (isTopLevel != other.isTopLevel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + structures.hashCode()
        result = 31 * result + isTopLevel.hashCode()
        return result
    }


}


/**
 * Define your own WallStructure from existing WallStructures.
 */
class RandomStructures: WallStructure() {
    /**
     * The name of Different Structures. Separated by comma (example: structures: Floor, Ceiling)
     * You can also define Parameters of the first Structure
     * These get loaded in Order, So if your reference defined Structures, those must be listed before that
     * The Beat Value gets every time, so it should be 0 most of the time
     */
    var structures: List<WallStructure> = listOf()

    /**
     * The first point of the area which your structures get placed into
     */
    var p1 = Point(-8,0,0)

    /**
     * The first point of the area which your structures get placed into
     */
    var p2 = Point(8,0,8)

    /**
     * How many structures you want to place. default: 8
     */
    var amount: Int = 8

    /**
     * avoids spawning structures in the playspace. default: true
     */
    var avoidCenter: Boolean = true


    /**
     * generating the Walls
     */
    override fun generate()  = run()
}

/**
 * create a single Wall
 */
class Wall: WallStructure() {
    /**
     * The StartTime of the wall, relative to the beat count.
     * Should be left at 0 most of the time.
     * Default: 0
     */
    var startTime = 0.0

    /**
     * Duration of the Wall in beats
     */
    var duration = 1.0

    /**
     * The startHeight of the wall.
     */
    var startHeight = 0.0

    /**
     * The Height of the wall.
     */
    var height = 0.0

    /**
     * The startRow of the Wall
     * 0 equals center. 1 equals 1 block size
     */
    var startRow = 0.0

    /**
     * The width of the Wall
     */
    var width = 0.0

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}

/**
 * spinning time! make walls spin around the player
 */
class Helix: WallStructure() {

    /**
     * how many spirals will be created
     */
    var count = 2

    /**
     * The radius of the Helix
     */
    var radius = 2.0

    /**
     * does not reflect the actual amount of walls, instead is more of an multiplier (will be changed with version 1.0)
     */
    var amount = 10

    /**
     * the start in degree
     */
    var startRotation = 0.0

    /**
     * describes, how many "Spins" the helix has
     */
    var rotationAmount = 1.0

    /**
     * Point of the center, defaults to 0,2,0
     */
    var center = Point(0, 2, 0)

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}

class NoodleHelix: WallStructure(){
    /**
     * how many spirals will be created
     */
    var count = 1

    /**
     * The radius of the Helix
     */
    var radius = 2.0

    /**
     * the endradius. default: null (normal radius)
     */
    var endRadius:Double? = null
    /**
     *  the amount of walls created. Default: 8*scale
     */
    var amount = 8*(scale?.invoke()?:1).toInt()

    /**
     * spins every wall additionally this amount
     */
    var localRotationOffset = 0.0
    /**
     * the start in degree
     */
    var startRotation = 0.0

    /**
     * describes in degree, how many "Spins" the helix has. default: 360
     */
    var rotationAmount = 360.0

    /**
     * Point of the center, defaults to 0,2,0
     */
    var center = Point(0, 2, 0)

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}

/**
 * NOT IMPLEMENTED YET
 * loops throuh the given wallstructures and increments their values
 */
class Loop: WallStructure(){
    /**
     * The name of Different Structures. Separated by comma (example: structures: Floor, Ceiling)
     * You can also define Parameters of the first Structure
     * These get loaded in Order, So if your reference defined Structures, those must be listed before that
     * The Beat Value gets every time, so it should be 0 most of the time
     */
    var structures: List<WallStructure> = listOf()

    /**
     * how often to loop through it
     */
    var amount: Int = 8

    override fun generate() = run()
}

/**
 * Draws a wall of line between the 2 provided Points
 */
class Line: WallStructure(){

    /**
     * how many walls will be created. Default: 6 * duration
     */
    var amount: Int? = null

    /**
     * The startPoint
     */
    var p1 = Point(0, 0, 0)

    /**
     * the End Point
     */
    var p2 = Point(0, 0, 1)

    /**
     * generating the Walls
     */
    override fun generate() = run()
}

/**
 * place random blocks around the player
 */
class RandomBlocks: WallStructure(){

    var duration = 4

    var amount= 8

    var wallDuration = 1.0

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}

/**
 * random curves in a given cubic. Always starts at p1 and ends at p2.
 */
class RandomCurve: WallStructure(){

    /**
     * dont touch
     */
    private var randomSideChooser = Random.nextBoolean()

    /**
     * first Point that controls the cubic, in which section walls are created. defaults to a random side
     */
    var p1: Point = if (randomSideChooser) Point(
        1,
        0,
        0
    ) else Point(-1, 0, 0)

    /**
     * second Point that crontrols in which section walls are created. z must be higher than p1
     */
    var p2: Point = if (randomSideChooser) Point(
        4,
        0,
        1
    ) else Point(-4, 4, 1)

    /**
     * the amount of Walls per beat
     */
    var amount: Int = 8

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}

/**
 * NOT IMPLEMENTED YET
 *
 * Helix Curve lets you define 1/4 of a curve around the center. The program creates the rest of the helix
 */
class HelixCurve: WallStructure() {
    /**
     * the start Point of the Curve
     */
    var p1: Point = Point(0, 0, 0)

    /**
     * the first Controllpoint, defaults to the startPoint
     */
    var p2: Point =
        Point(4.0, 0.0, 0.33)

    /**
     * second ControlPoint, defaults to the end point
     */
    var p3: Point =
        Point(4.0, 0.0, 0.66)

    /**
     * The EndPoint of the Curve
     */
    var p4: Point = Point(4, 2, 1)

    /**
     * the amount of walls per Helix
     */
    var amount: Int = 8

    /**
     * how many helix spines will be Created. Only 2 or 4 allowed
     */
    var count: Int = 4

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}

//                                               _                _      ____               _
//   __ _    ___   _ __     ___   _ __    __ _  | |_    ___    __| |    / ___|   ___     __| |   ___
//  / _` |  / _ \ | '_ \   / _ \ | '__|  / _` | | __|  / _ \  / _` |   | |      / _ \   / _` |  / _ \
// | (_| | |  __/ | | | | |  __/ | |    | (_| | | |_  |  __/ | (_| |   | |___  | (_) | | (_| | |  __/
//  \__, |  \___| |_| |_|  \___| |_|     \__,_|  \__|  \___|  \__,_|    \____|  \___/   \__,_|  \___|
//  |___/

/**
 * Creates a continues line with up to 31 Points. has a typo and will be deleted on version 1.0
 */
@Deprecated("Will be removed on 1.0")
class ContinuesCurve : WallStructure(){
    /**
     * dont touch
     */
    val creationAmount = 32
    /**
     * The duration of each wall
     */
    var duration: Double = -3.0
    /**
     * The amount of Walls per beat
     */
    var amount: Int = 8

    /**
     * The 1 Point. use this to set an exact Point the wall will go through
     */
    var p1: Point? = null

    /**
     * The ControllPoint for the 1 Point. Use this to guide the curve to his direction
     */
    var c1: Point? = null

    /**
     * The 2 Point. use this to set an exact Point the wall will go through
     */
    var p2: Point? = null

    /**
     * The ControllPoint for the 2 Point. Use this to guide the curve to his direction
     */
    var c2: Point? = null

    /**
     * The 3 Point. use this to set an exact Point the wall will go through
     */
    var p3: Point? = null

    /**
     * The ControllPoint for the 3 Point. Use this to guide the curve to his direction
     */
    var c3: Point? = null

    /**
     * The 4 Point. use this to set an exact Point the wall will go through
     */
    var p4: Point? = null

    /**
     * The ControllPoint for the 4 Point. Use this to guide the curve to his direction
     */
    var c4: Point? = null

    /**
     * The 5 Point. use this to set an exact Point the wall will go through
     */
    var p5: Point? = null

    /**
     * The ControllPoint for the 5 Point. Use this to guide the curve to his direction
     */
    var c5: Point? = null

    /**
     * The 6 Point. use this to set an exact Point the wall will go through
     */
    var p6: Point? = null

    /**
     * The ControllPoint for the 6 Point. Use this to guide the curve to his direction
     */
    var c6: Point? = null

    /**
     * The 7 Point. use this to set an exact Point the wall will go through
     */
    var p7: Point? = null

    /**
     * The ControllPoint for the 7 Point. Use this to guide the curve to his direction
     */
    var c7: Point? = null

    /**
     * The 8 Point. use this to set an exact Point the wall will go through
     */
    var p8: Point? = null

    /**
     * The ControllPoint for the 8 Point. Use this to guide the curve to his direction
     */
    var c8: Point? = null

    /**
     * The 9 Point. use this to set an exact Point the wall will go through
     */
    var p9: Point? = null

    /**
     * The ControllPoint for the 9 Point. Use this to guide the curve to his direction
     */
    var c9: Point? = null

    /**
     * The 10 Point. use this to set an exact Point the wall will go through
     */
    var p10: Point? = null

    /**
     * The ControllPoint for the 10 Point. Use this to guide the curve to his direction
     */
    var c10: Point? = null

    /**
     * The 11 Point. use this to set an exact Point the wall will go through
     */
    var p11: Point? = null

    /**
     * The ControllPoint for the 11 Point. Use this to guide the curve to his direction
     */
    var c11: Point? = null

    /**
     * The 12 Point. use this to set an exact Point the wall will go through
     */
    var p12: Point? = null

    /**
     * The ControllPoint for the 12 Point. Use this to guide the curve to his direction
     */
    var c12: Point? = null

    /**
     * The 13 Point. use this to set an exact Point the wall will go through
     */
    var p13: Point? = null

    /**
     * The ControllPoint for the 13 Point. Use this to guide the curve to his direction
     */
    var c13: Point? = null

    /**
     * The 14 Point. use this to set an exact Point the wall will go through
     */
    var p14: Point? = null

    /**
     * The ControllPoint for the 14 Point. Use this to guide the curve to his direction
     */
    var c14: Point? = null

    /**
     * The 15 Point. use this to set an exact Point the wall will go through
     */
    var p15: Point? = null

    /**
     * The ControllPoint for the 15 Point. Use this to guide the curve to his direction
     */
    var c15: Point? = null

    /**
     * The 16 Point. use this to set an exact Point the wall will go through
     */
    var p16: Point? = null

    /**
     * The ControllPoint for the 16 Point. Use this to guide the curve to his direction
     */
    var c16: Point? = null

    /**
     * The 17 Point. use this to set an exact Point the wall will go through
     */
    var p17: Point? = null

    /**
     * The ControllPoint for the 17 Point. Use this to guide the curve to his direction
     */
    var c17: Point? = null

    /**
     * The 18 Point. use this to set an exact Point the wall will go through
     */
    var p18: Point? = null

    /**
     * The ControllPoint for the 18 Point. Use this to guide the curve to his direction
     */
    var c18: Point? = null

    /**
     * The 19 Point. use this to set an exact Point the wall will go through
     */
    var p19: Point? = null

    /**
     * The ControllPoint for the 19 Point. Use this to guide the curve to his direction
     */
    var c19: Point? = null

    /**
     * The 20 Point. use this to set an exact Point the wall will go through
     */
    var p20: Point? = null

    /**
     * The ControllPoint for the 20 Point. Use this to guide the curve to his direction
     */
    var c20: Point? = null

    /**
     * The 21 Point. use this to set an exact Point the wall will go through
     */
    var p21: Point? = null

    /**
     * The ControllPoint for the 21 Point. Use this to guide the curve to his direction
     */
    var c21: Point? = null

    /**
     * The 22 Point. use this to set an exact Point the wall will go through
     */
    var p22: Point? = null

    /**
     * The ControllPoint for the 22 Point. Use this to guide the curve to his direction
     */
    var c22: Point? = null

    /**
     * The 23 Point. use this to set an exact Point the wall will go through
     */
    var p23: Point? = null

    /**
     * The ControllPoint for the 23 Point. Use this to guide the curve to his direction
     */
    var c23: Point? = null

    /**
     * The 24 Point. use this to set an exact Point the wall will go through
     */
    var p24: Point? = null

    /**
     * The ControllPoint for the 24 Point. Use this to guide the curve to his direction
     */
    var c24: Point? = null

    /**
     * The 25 Point. use this to set an exact Point the wall will go through
     */
    var p25: Point? = null

    /**
     * The ControllPoint for the 25 Point. Use this to guide the curve to his direction
     */
    var c25: Point? = null

    /**
     * The 26 Point. use this to set an exact Point the wall will go through
     */
    var p26: Point? = null

    /**
     * The ControllPoint for the 26 Point. Use this to guide the curve to his direction
     */
    var c26: Point? = null

    /**
     * The 27 Point. use this to set an exact Point the wall will go through
     */
    var p27: Point? = null

    /**
     * The ControllPoint for the 27 Point. Use this to guide the curve to his direction
     */
    var c27: Point? = null

    /**
     * The 28 Point. use this to set an exact Point the wall will go through
     */
    var p28: Point? = null

    /**
     * The ControllPoint for the 28 Point. Use this to guide the curve to his direction
     */
    var c28: Point? = null

    /**
     * The 29 Point. use this to set an exact Point the wall will go through
     */
    var p29: Point? = null

    /**
     * The ControllPoint for the 29 Point. Use this to guide the curve to his direction
     */
    var c29: Point? = null

    /**
     * The 30 Point. use this to set an exact Point the wall will go through
     */
    var p30: Point? = null

    /**
     * The ControllPoint for the 30 Point. Use this to guide the curve to his direction
     */
    var c30: Point? = null

    /**
     * The 31 Point. use this to set an exact Point the wall will go through
     */
    var p31: Point? = null

    /**
     * The ControllPoint for the 31 Point. Use this to guide the curve to his direction
     */
    var c31: Point? = null

    /**
     * The 32 Point. use this to set an exact Point the wall will go through
     */
    var p32: Point? = null

    /**
     * The ControllPoint for the 32 Point. Use this to guide the curve to his direction
     */
    var c32: Point? = null

    override fun generate(): List<SpookyWall> {
        val l = mutableListOf<SpookyWall>()
        for(i in 1 until creationAmount){
            val point = readPoint("p$i")
            val controlPoint = readPoint("c$i")
            val nextPoint = readPoint("p${i +1}")
            val nextControlPoint = readPoint("c${i+1}")
            val nextNextPoint = try { readPoint("p${i+2}") } catch (e:Exception){ null }
            if(point!=null && nextPoint!=null && controlPoint!=null && nextControlPoint!= null) {
                val tempP1 = point
                val tempP2 = controlPoint.copy(z = point.z + (1/3.0) * (nextPoint.z - point.z))
                val tempP3 = calcP3(
                    point,
                    nextPoint,
                    nextControlPoint,
                    nextNextPoint
                )
                val tempP4 = nextPoint
                val amount = ((tempP4.z - tempP1.z) * amount).toInt()
                l.addAll(curve(tempP1, tempP2, tempP3, tempP4, amount))
            }
        }
        return l.toList()
    }
}
/**
 * Creates a continues line with up to 31 Points. this one does not have a typo in the name :)
 */
class ContinuousCurve : WallStructure(){
    /**
     * dont touch
     */
    val creationAmount = 32
    /**
     * The duration of each wall
     */
    var duration: Double = -3.0
    /**
     * The amount of Walls per beat
     */
    var amount: Int = 8

    /**
     * The 1 Point. use this to set an exact Point the wall will go through
     */
    var p1: Point? = null

    /**
     * The ControllPoint for the 1 Point. Use this to guide the curve to his direction
     */
    var c1: Point? = null

    /**
     * The 2 Point. use this to set an exact Point the wall will go through
     */
    var p2: Point? = null

    /**
     * The ControllPoint for the 2 Point. Use this to guide the curve to his direction
     */
    var c2: Point? = null

    /**
     * The 3 Point. use this to set an exact Point the wall will go through
     */
    var p3: Point? = null

    /**
     * The ControllPoint for the 3 Point. Use this to guide the curve to his direction
     */
    var c3: Point? = null

    /**
     * The 4 Point. use this to set an exact Point the wall will go through
     */
    var p4: Point? = null

    /**
     * The ControllPoint for the 4 Point. Use this to guide the curve to his direction
     */
    var c4: Point? = null

    /**
     * The 5 Point. use this to set an exact Point the wall will go through
     */
    var p5: Point? = null

    /**
     * The ControllPoint for the 5 Point. Use this to guide the curve to his direction
     */
    var c5: Point? = null

    /**
     * The 6 Point. use this to set an exact Point the wall will go through
     */
    var p6: Point? = null

    /**
     * The ControllPoint for the 6 Point. Use this to guide the curve to his direction
     */
    var c6: Point? = null

    /**
     * The 7 Point. use this to set an exact Point the wall will go through
     */
    var p7: Point? = null

    /**
     * The ControllPoint for the 7 Point. Use this to guide the curve to his direction
     */
    var c7: Point? = null

    /**
     * The 8 Point. use this to set an exact Point the wall will go through
     */
    var p8: Point? = null

    /**
     * The ControllPoint for the 8 Point. Use this to guide the curve to his direction
     */
    var c8: Point? = null

    /**
     * The 9 Point. use this to set an exact Point the wall will go through
     */
    var p9: Point? = null

    /**
     * The ControllPoint for the 9 Point. Use this to guide the curve to his direction
     */
    var c9: Point? = null

    /**
     * The 10 Point. use this to set an exact Point the wall will go through
     */
    var p10: Point? = null

    /**
     * The ControllPoint for the 10 Point. Use this to guide the curve to his direction
     */
    var c10: Point? = null

    /**
     * The 11 Point. use this to set an exact Point the wall will go through
     */
    var p11: Point? = null

    /**
     * The ControllPoint for the 11 Point. Use this to guide the curve to his direction
     */
    var c11: Point? = null

    /**
     * The 12 Point. use this to set an exact Point the wall will go through
     */
    var p12: Point? = null

    /**
     * The ControllPoint for the 12 Point. Use this to guide the curve to his direction
     */
    var c12: Point? = null

    /**
     * The 13 Point. use this to set an exact Point the wall will go through
     */
    var p13: Point? = null

    /**
     * The ControllPoint for the 13 Point. Use this to guide the curve to his direction
     */
    var c13: Point? = null

    /**
     * The 14 Point. use this to set an exact Point the wall will go through
     */
    var p14: Point? = null

    /**
     * The ControllPoint for the 14 Point. Use this to guide the curve to his direction
     */
    var c14: Point? = null

    /**
     * The 15 Point. use this to set an exact Point the wall will go through
     */
    var p15: Point? = null

    /**
     * The ControllPoint for the 15 Point. Use this to guide the curve to his direction
     */
    var c15: Point? = null

    /**
     * The 16 Point. use this to set an exact Point the wall will go through
     */
    var p16: Point? = null

    /**
     * The ControllPoint for the 16 Point. Use this to guide the curve to his direction
     */
    var c16: Point? = null

    /**
     * The 17 Point. use this to set an exact Point the wall will go through
     */
    var p17: Point? = null

    /**
     * The ControllPoint for the 17 Point. Use this to guide the curve to his direction
     */
    var c17: Point? = null

    /**
     * The 18 Point. use this to set an exact Point the wall will go through
     */
    var p18: Point? = null

    /**
     * The ControllPoint for the 18 Point. Use this to guide the curve to his direction
     */
    var c18: Point? = null

    /**
     * The 19 Point. use this to set an exact Point the wall will go through
     */
    var p19: Point? = null

    /**
     * The ControllPoint for the 19 Point. Use this to guide the curve to his direction
     */
    var c19: Point? = null

    /**
     * The 20 Point. use this to set an exact Point the wall will go through
     */
    var p20: Point? = null

    /**
     * The ControllPoint for the 20 Point. Use this to guide the curve to his direction
     */
    var c20: Point? = null

    /**
     * The 21 Point. use this to set an exact Point the wall will go through
     */
    var p21: Point? = null

    /**
     * The ControllPoint for the 21 Point. Use this to guide the curve to his direction
     */
    var c21: Point? = null

    /**
     * The 22 Point. use this to set an exact Point the wall will go through
     */
    var p22: Point? = null

    /**
     * The ControllPoint for the 22 Point. Use this to guide the curve to his direction
     */
    var c22: Point? = null

    /**
     * The 23 Point. use this to set an exact Point the wall will go through
     */
    var p23: Point? = null

    /**
     * The ControllPoint for the 23 Point. Use this to guide the curve to his direction
     */
    var c23: Point? = null

    /**
     * The 24 Point. use this to set an exact Point the wall will go through
     */
    var p24: Point? = null

    /**
     * The ControllPoint for the 24 Point. Use this to guide the curve to his direction
     */
    var c24: Point? = null

    /**
     * The 25 Point. use this to set an exact Point the wall will go through
     */
    var p25: Point? = null

    /**
     * The ControllPoint for the 25 Point. Use this to guide the curve to his direction
     */
    var c25: Point? = null

    /**
     * The 26 Point. use this to set an exact Point the wall will go through
     */
    var p26: Point? = null

    /**
     * The ControllPoint for the 26 Point. Use this to guide the curve to his direction
     */
    var c26: Point? = null

    /**
     * The 27 Point. use this to set an exact Point the wall will go through
     */
    var p27: Point? = null

    /**
     * The ControllPoint for the 27 Point. Use this to guide the curve to his direction
     */
    var c27: Point? = null

    /**
     * The 28 Point. use this to set an exact Point the wall will go through
     */
    var p28: Point? = null

    /**
     * The ControllPoint for the 28 Point. Use this to guide the curve to his direction
     */
    var c28: Point? = null

    /**
     * The 29 Point. use this to set an exact Point the wall will go through
     */
    var p29: Point? = null

    /**
     * The ControllPoint for the 29 Point. Use this to guide the curve to his direction
     */
    var c29: Point? = null

    /**
     * The 30 Point. use this to set an exact Point the wall will go through
     */
    var p30: Point? = null

    /**
     * The ControllPoint for the 30 Point. Use this to guide the curve to his direction
     */
    var c30: Point? = null

    /**
     * The 31 Point. use this to set an exact Point the wall will go through
     */
    var p31: Point? = null

    /**
     * The ControllPoint for the 31 Point. Use this to guide the curve to his direction
     */
    var c31: Point? = null

    /**
     * The 32 Point. use this to set an exact Point the wall will go through
     */
    var p32: Point? = null

    /**
     * The ControllPoint for the 32 Point. Use this to guide the curve to his direction
     */
    var c32: Point? = null

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}

