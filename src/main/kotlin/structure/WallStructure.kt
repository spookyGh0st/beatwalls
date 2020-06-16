@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package structure

import chart.difficulty._obstacles
import chart.difficulty.toSpookyWall
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import interpreter.property.specialProperties.*
import structure.helperClasses.ColorMode
import structure.helperClasses.NoColor
import structure.helperClasses.Point
import structure.helperClasses.SpookyWall
import structure.specialStrucures.run
import java.io.Serializable
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
 */

//    _   __                           __   _____ __                  __
//   / | / /___  _________ ___  ____ _/ /  / ___// /________  _______/ /___  __________  _____
//  /  |/ / __ \/ ___/ __ `__ \/ __ `/ /   \__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// / /|  / /_/ / /  / / / / / / /_/ / /   ___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///_/ |_/\____/_/  /_/ /_/ /_/\__,_/_/   /____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/

sealed class WallStructure:Serializable
{
    // the progress on 0..1 of the generation of walls. set this to allow for easing
    var i: Double  = 0.0
    // a reference to the currently active wall. set this to allow for wall variables
    var activeWall: SpookyWall = SpookyWall()
    // Random element so we can use seeding for functions and ws intern generation
    val r: Random by lazy {  Random(seed?: Random.nextInt()) }
    // local variables
    var variables: HashMap<String,Double> = hashMapOf()
    // repeatCounter used for repeats in WallStructures. Neded because of nested Properties
    var repeatCounter = RepeatCounter()

    /**
     * some Wallstructures use Random walls. This is the seed for them
     */
    val seed: Int? by BwIntOrNull()

    /**
     * This is needed for all the functions and properties.
     * This allows for the use of other bwProperties in the expressions
     */
    open val a by BwInt()

    /**
     * dont touch
     */
    internal var spookyWalls: ArrayList<SpookyWall> = arrayListOf()

    /**
     * dont touch
     */
    var beat: Double = 0.0

    //    ____  ___    _   ______  ____  __  ___   _____________  ______________
    //   / __ \/   |  / | / / __ \/ __ \/  |/  /  / ___/_  __/ / / / ____/ ____/
    //  / /_/ / /| | /  |/ / / / / / / / /|_/ /   \__ \ / / / / / / /_  / /_
    // / _, _/ ___ |/ /|  / /_/ / /_/ / /  / /   ___/ // / / /_/ / __/ / __/
    ///_/ |_/_/  |_/_/ |_/_____/\____/_/  /_/   /____//_/  \____/_/   /_/

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
    val mirror by BwInt()

    /**
     * The x Position used for mirror. Default: 0
     */
    val mirrorX by BwDouble()

    /**
     * The y Position used for mirror. Default: 2
     */
    val mirrorY by BwDouble(2)

    /**
     * times the SpookyWall by adding the njsOffset, default: true
     */
    val timeToNjsOffset by BwBoolean(true)

    /**
     * change The x of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    val x by BwDouble("wall${SpookyWall::x.name}")
    /**
     * change The y of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    val y by BwDouble("wall${SpookyWall::y.name}")
    /**
     * change The StartTime of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    val z by BwDouble("wall${SpookyWall::z.name}")

    /**
     * change The Duration of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    val duration by BwDouble("wall${SpookyWall::duration.name}") // todo rename to duration
    /**
     * change The Height of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    val height by BwDouble("wall${SpookyWall::height.name}")
    /**
     * change the Width of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    val width by BwDouble("wall${SpookyWall::width.name}")

    /**
     * increases or decreases the width of all walls until they have the the specific X. Random possible with random(min,max). default: null (does nothing)
     */
    val fitX by BwDoubleOrNull()
    /**
     * increases or decreases the height of all walls until they have the the specific y. Random possible with random(min,max). default: null (does nothing)
     */
    val fitY by BwDoubleOrNull()
    /**
     * increases or decreases the duration of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    val fitZ by BwDoubleOrNull()

    /**
     * increases or decreases the x of all walls until they have the the specific Width. Random possible with random(min,max). default: null (does nothing)
     */
    val fitWidth by BwDoubleOrNull()
    /**
     * increases or decreases the StartTime of all walls until they have the the specific duration. Random possible with random(min,max). default: null (does nothing)
     */
    val fitDuration by BwDoubleOrNull()
    /**
     * increases or decreases the y of all walls until they have the the specific Height. Random possible with random(min,max). default: null (does nothing)
     */
    val fitHeight by BwDoubleOrNull()


    /**
     * scales both X and Width at the same time.
     */
    val scaleX by BwDouble(1)

    /**
     * scales both Y and Height at the same time.
     */
    val scaleY by BwDouble(1)

    /**
     * scales both Z and duration at the same time.
     * the Duration only gets scaled, if it is positive.
     * This is useful for stretching out Wallstructures over a wider section
     */
    val scaleZ by BwDouble(1)

    /**
     * reverses the WallStructure on the Starttime/duration
     */
    val reverse: Boolean by BwBoolean(false)

    /**
     * basically mirrors the Wallstructure in itself on the x-Achsis if set to true
     */
    val reverseX by BwBoolean(false)

    /**
     * basically mirrors the Wallstructure in itself on the x-Achsis if set to true
     */
    val reverseY: Boolean by BwBoolean(false)

    /**
     * speeds up the wallstructure over time. the duration of the whole structure. Remains. \n
     *
     * value 0-1 start is slower, speed up over time, \n
     *
     * 1-100 start is faster, slow down over time \n
     *
     * The closer the value is to 1, the more stale it gets.
     */
    val speeder: Double? by BwDoubleOrNull()


    /**
     * how often you want to repeat the Structure.
     */
    val repeat: Int by BwInt(1) //= Default.repeat

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
     * color: rainbow(1.0)
     * # creates a rainbow :)
     *
     * color: rainbow(1.5)
     * # also creates a rainbow :), but this one changes 1.5 times as fast as the default one
     *
     * color: flash2(green, black)
     * # Flashes between green and black. You need to specify the number of colors
     *
     * color: flash3(black, 128,128,128,Green)
     * # Flashes between black, Dark Gray (128,128,128) and Green.
     *
     * color: flash4(red, green, blue, yellow)
     * # changes color in the order red, green, blue, yellow.
     *
     * color: gradient(Red,Cyan)
     * # gradient from Red to Furry (Cyan))
     *
     */
    val color by BwColor()

    /**
     * The rotationY of the track of this wallstructure around the player on the X-Axis, think walls coming from the ground/sky
     * Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotationY(true,false)
     */
    val rotationX: Double by BwDouble("wall${SpookyWall::rotationX.name}")
    /**
     * The rotationY of the track of this wallstructure around the player on the y-Axis, think 360 maps.
     * Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotationY(true,false)
     */
    val rotationY: Double by BwDouble("wall${SpookyWall::rotationY.name}")
    /**
     * The rotationY of the track of this wallstructure around the player on the z-Axis, not used much..
     * Other interesting Properties: mirrorRotation -> controls if mirror also effects the rotationY(true,false)
     */
    val rotationZ: Double by BwDouble("wall${SpookyWall::rotationZ.name}")

    /**
     * Defines, if mirror also effects the rotationY. Can be true or false. Default: true
     */
    val mirrorRotation: Boolean by BwBoolean(true)

    /**
     * localRotX controls the rotationY on the x-axis for each individual wall in degree. allows random. Default: 0
     *
     *  example:
     *
     *  ```yaml
     *  localRotX: 20 # rotates each wall 20 degree to the right
     *  localRotX: random(-20,20) # rotates each wall on the x axis randomnly between -20 and 20.
     *  ```
     */
    val localRotX by BwDouble("wall${SpookyWall::localRotX.name}")

    /**
     * localRotY controls the rotationY on the y-axis for each individual Wall in degree. allows random. Default: 0
     *
     *  example:
     *
     *  ```yaml
     *  localRotY: 20 # rotates each wall 20 degree to the right
     *  localRotY: random(-20,20) # rotates each wall on the x axis randomnly between -20 and 20.
     *  ```
     */
    val localRotY by BwDouble("wall${SpookyWall::localRotY.name}")
    /**
     * * localRotZ controls the rotationY on the x-axis for each individual Wall in degree. allows random. Default: 0
     *
     *  example:
     *
     *  ```yaml
     *  localRotZ: 20 # rotates each wall 20 degree to the right
     *  localRotZ: random(-20,20) # rotates each wall on the x axis randomnly between -20 and 20.
     *  ```
     */
    val localRotZ by BwDouble("wall${SpookyWall::localRotZ.name}")


    /**
     * todo
     * used for some internal stuff, dont touch
     */
    val track: String? = Default.track

    /**
     * turns the entire WallStructure into bombs. only available, if deleteAllPrevious is set.
     */
    val bombs by BwBoolean(false)

    /**
     * I have no idea, ask cyan
     */
    val noteJumpMovementSpeed by BwDoubleOrNull()

    /**
     * I have no idea, ask cyan
     */
    val noteJumpMovementSpeedOffset by BwDoubleOrNull()

    companion object Default {
        var color: ColorMode = NoColor
        var track: String? = null
    }

    /** generates the walls */
    abstract fun generateWalls():List<SpookyWall>

    /** returns the name of the structure */
    open fun name() = this::class.simpleName ?: throw ClassNotFoundException("class does not have a name")
}


/**
 * dont touch
 * todo remove
 */
class EmptyWallStructure : WallStructure() {
    override fun generateWalls() = emptyList<SpookyWall>()
}

//   _____                 _       __   _       __      _________ __                  __
//  / ___/____  ___  _____(_)___ _/ /  | |     / /___ _/ / / ___// /________  _______/ /___  __________  _____
//  \__ \/ __ \/ _ \/ ___/ / __ `/ /   | | /| / / __ `/ / /\__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// ___/ / /_/ /  __/ /__/ / /_/ / /    | |/ |/ / /_/ / / /___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///____/ .___/\___/\___/_/\__,_/_/     |__/|__/\__,_/_/_//____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/
//    /_/

/**
 * Random Noise (small mini cubes)
 */
class RandomNoise:WallStructure(){
    /**
     * the amount of the created Walls, if no value is given creates 8x the beatcount
     */
    val amount: Int by BwInt("(p2.z-p1.z)*8")

    /**
     * controls one corner of the Area
     */
    val p1 by BwPoint(-6, 0, 0)

    /**
     * controls the other corner of the Area
     */
    val p2 by BwPoint(6, 5, 1)

    /**
     * avoids spawning structures in the playspace. default: true
     */
    val avoidCenter by BwBoolean(true)
    /**
     * generating the Walls
     */
    override fun generateWalls() = run()
}

/**
 * 3d Grid
 */
class FurryGrid : WallStructure() {
    /**
     * the X-Size of one panel in the grid
     */
    val panelX by BwDouble(1.0)

    /**
     * the Y-Size of one panel in the grid
     */
    val panelY by BwDouble(0.0)

    /**
     * the Z-Size of one panel in the grid
     */
    val panelZ by BwDouble(1.0)

    /**
     * the X-Size of the whole grid, aka how often it will repeat in the X-direction
     */
    val gridX by BwInt(8)

    /**
     * the Y-Size of the whole grid, aka how often it will repeat in the Y-direction
     */
    val gridY by BwInt(1)

    /**
     * the Z-Size of the whole grid aka how often it will repeat in the Z-direction
     */
    val gridZ by BwInt(8)

    /**
     * different modes of walls
     *
     * 0 = create every wall in the pattern
     *
     * 1 = chess-pattern
     *
     * want more? write me
     */
    val mode by BwInt(0)

    /**
     * the start Point of the grid
     */
    val p1: Point by BwPoint(-4, 0, 0)

    /**
     * generating the Walls
     */
    override fun generateWalls() = run()
}

/**
 * draws multiple lines around Sections of the cuboid.
 */
class RandomCuboidLines : WallStructure() {
    /**
     * the first corner of the cuboid. Default is -2,0,0
     */
    val p1: Point by BwPoint(-2, 0, 0)

    /**
     * the second corner of the cuboid. Default is 2,4,8
     */
    val p2: Point by BwPoint(2, 4, 4)

    /**
     * The amount of walls per line. Default is 8
     */
    val amount: Int by BwInt(8)

    /**
     * The amount of lines that will be created. Defaults to the duration
     */
    val count: Int  by BwInt("p2.z-p1.z")

    /**
     * In how many sections will each side/floor be splitted. Must be at least 3. Default: 4
     */
    val sections: Int by BwInt(4)

    /**
     * 2 = only sides, 4 - bottom and top aswell
     */
    val randomSidePicker: Int by BwInt(4)
    /**
     * generating the Walls
     */
    override fun generateWalls() = run()
}

/**
 *  Use a json Array of NE Walls and have them be an WallStructure
 */
class RawWs: WallStructure(){
    /**
     * An Array of Walls in NE-Form
     */
    val json: String by BwString("")
    val walls by lazy {
        val collectionType  = object : TypeToken<Collection<_obstacles?>?>() {}.type
        Gson().fromJson<Collection<_obstacles>>(json,collectionType)
        .map { it.toSpookyWall() }
    }
    override fun generateWalls(): List<SpookyWall> = walls
}

/**
 * Draw a curve of Walls. This uses BezierCurve. You can imagine it like a line between point 1 and point 4, that gets pulled upon by the controlpoints. Maybe this link can help (the dots are the Points) https://www.desmos.com/calculator/cahqdxeshd
 */
class Curve : WallStructure() {
    /**
     * the start Point of the Curve
     */
    val p1: Point by BwPoint(0, 0, 0)

    /**
     * the first Controllpoint, defaults to the startPoint offset by 0.33 beats
     */
    val p2 by BwPoint("p1.x,p2.y,p3.z + 0.33")

    /**
     * second ControlPoint, defaults to the end point offset by -0.33 beats
     */
    val p3: Point by BwPoint("p4.x,p4.y,p4.z-0.33")

    /**
     * The EndPoint of the Curve
     */
    val p4: Point by BwPoint(0, 0, 0)

    /**
     * amount of Walls
     */
    val amount: Int by BwInt(8)

    /**
     * generating the Walls
     */
    override fun generateWalls() = run()
}

/**
 * Draw a steady curve of Walls. that is exactly 1 beat long
 * */
class SteadyCurve:WallStructure(){
    /**
     * the start Point of the Curve
     */
    val p1: Point by BwPoint(0, 0, 0)

    /**
     * the first Controllpoint, defaults to the startPoint
     */
    val p2 by BwPoint("p1.x,p2.y,p3.z + 0.33")

    /**
     * second ControlPoint, defaults to the end point
     */
    val p3: Point by BwPoint("p4.x,p4.y,p4.z-0.33")

    /**
     * The EndPoint of the Curve
     */
    val p4: Point by BwPoint(0, 0, 0)

    /**
     * amount of Walls
     */
    val amount: Int by BwInt(8)

    /**
     * generating the Walls
     */
    override fun generateWalls()  = run()
}

/**
 * Define your own WallStructure from existing WallStructures.
 *
 * Define your own structure with something like this
 *
 * ```yaml
 * myLine: Line
 *  p1: 4,0,0
 *  p2: 2,4,2
 * ```
 *
 * and then use it with
 * ```yaml
 * 10: myLine
 * ```
 *
 * You can also supply more then one structure.
 * For example when you have more then one line already defined
 *
 * ```yaml
 * myLine:
 *  structures: _line1, _line2
 *
 *
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
     * generating the Walls
     */
    override fun generateWalls() = run()

    override fun name(): String = name
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
    val p1 by BwPoint(-8,0,0)

    /**
     * The first point of the area which your structures get placed into
     */
    val p2 by BwPoint(8,0,8)

    /**
     * How many structures you want to place. default: 8
     */
    val amount: Int by BwInt(8)

    /**
     * avoids spawning structures in the playspace. default: false
     */
    val avoidCenter: Boolean by BwBoolean(false)


    /**
     * generating the Walls
     */
    override fun generateWalls()  = run()
}

/**
 * create a single Wall between to points
 */
class Wall: WallStructure() {
    /**
     * The StartTime of the wall, relative to the beat count.
     * Should be left at 0 most of the time.
     * Default: 0
     */
    val p1 by BwPoint(0,0,0)

    /**
     * Duration of the Wall in beats
     */
    val p2 by BwPoint(0,0,0)

    /**
     * generating the Walls
     */
    override fun generateWalls()  = run()
}

/**
 * spinning time! make walls spin around the player
 * Also check out noodlehelix, its propably better.
 */
class Helix: WallStructure() {

    /**
     * how many spirals will be created
     */
    val count by BwInt(2)

    /**
     * The radius of the Helix
     */
    val radius by BwDouble(2.0)

    /**
     * does not reflect the actual amount of walls, instead is more of an multiplier (will be changed with version 1.0)
     */
    val amount by BwInt(10)

    /**
     * the start in degree
     */
    val startRotation by BwDouble( 0.0)

    /**
     * describes, how many "Spins" the helix has
     */
    val rotationAmount by BwDouble(1.0)

    /**
     * Point of the center, defaults to 0,2,0
     */
    val center by BwPoint(0, 2, 0)

    /**
     * generating the Walls
     */
    override fun generateWalls()  = run()
}

class NoodleHelix: WallStructure(){
    /**
     * how many spirals will be created
     */
    val count by BwInt(2)

    /**
     * The radius of the Helix
     */
    val radius by BwDouble(2)

    /**
     * the endradius. default: null (normal radius)
     */
    val endRadius:Double by BwDouble("radius")
    /**
     *  the amount of walls created. Default: 8*scaleZ
     */
    val amount by BwInt("8*scaleZ)")

    /**
     * spins every wall additionally this amount
     */
    val localRotationOffset by BwDouble(0)
    /**
     * the start in degree
     */
    val startRotation by BwDouble(0)

    /**
     * describes in degree, how many "Spins" the helix has. default: 360
     */
    val rotationAmount by BwDouble(360)

    /**
     * Point of the center, defaults to 0,2,0
     */
    val center by BwPoint(0,2,0)

    /**
     * generating the Walls
     */
    override fun generateWalls()  = run()
}


/**
 * Draws a wall of line between the 2 provided Points
 */
class Line: WallStructure(){

    /**
     * how many walls will be created. Default: 6 * duration
     */
    val amount by BwInt(8)

    /**
     * The startPoint
     */
    val p1 by BwPoint(0, 0, 0)

    /**
     * the End Point
     */
    val p2 by BwPoint(0, 0, 0)

    /**
     * generating the Walls
     */
    override fun generateWalls() = run()
}


/**
 * random curves in a given cubic. Always starts at p1 and ends at p2.
 */
class RandomCurve: WallStructure(){

    /**
     * first Point that controls the cubic, in which section walls are created. defaults to a random side
     */
    val p1: Point by BwPoint(1,0,0)

    /**
     * second Point that crontrols in which section walls are created. z must be higher than p1
     */
    val p2: Point by BwPoint(4,4,4)

    /**
     * the amount of Walls per beat
     */
    var amount: Int = 8

    /**
     * generating the Walls
     */
    override fun generateWalls()  = run()
}


//                                               _                _      ____               _
//   __ _    ___   _ __     ___   _ __    __ _  | |_    ___    __| |    / ___|   ___     __| |   ___
//  / _` |  / _ \ | '_ \   / _ \ | '__|  / _` | | __|  / _ \  / _` |   | |      / _ \   / _` |  / _ \
// | (_| | |  __/ | | | | |  __/ | |    | (_| | | |_  |  __/ | (_| |   | |___  | (_) | | (_| | |  __/
//  \__, |  \___| |_| |_|  \___| |_|     \__,_|  \__|  \___|  \__,_|    \____|  \___/   \__,_|  \___|
//  |___/

/**
 * Creates a continues line with up to 31 Points. this one does not have a typo in the name :)
 */
class ContinuousCurve : WallStructure(){
    /**
     * dont touch
     */
    val creationAmount = 32

    /**
     * The amount of Walls per beat
     */
    val amount by BwInt(8)

    /**
     * The 1 Point. use this to set an exact Point the wall will go through
     */
    val p1 by BwPointOrNull()

    /**
     * The ControllPoint for the 1 Point. Use this to guide the curve to his direction
     */
    val c1: Point? by BwPointOrNull()

    /**
     * The 2 Point. use this to set an exact Point the wall will go through
     */
    val p2: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 2 Point. Use this to guide the curve to his direction
     */
    val c2: Point? by BwPointOrNull()

    /**
     * The 3 Point. use this to set an exact Point the wall will go through
     */
    val p3: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 3 Point. Use this to guide the curve to his direction
     */
    val c3: Point? by BwPointOrNull()

    /**
     * The 4 Point. use this to set an exact Point the wall will go through
     */
    val p4: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 4 Point. Use this to guide the curve to his direction
     */
    val c4: Point? by BwPointOrNull()

    /**
     * The 5 Point. use this to set an exact Point the wall will go through
     */
    val p5: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 5 Point. Use this to guide the curve to his direction
     */
    val c5: Point? by BwPointOrNull()

    /**
     * The 6 Point. use this to set an exact Point the wall will go through
     */
    val p6: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 6 Point. Use this to guide the curve to his direction
     */
    val c6: Point? by BwPointOrNull()

    /**
     * The 7 Point. use this to set an exact Point the wall will go through
     */
    val p7: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 7 Point. Use this to guide the curve to his direction
     */
    val c7: Point? by BwPointOrNull()

    /**
     * The 8 Point. use this to set an exact Point the wall will go through
     */
    val p8: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 8 Point. Use this to guide the curve to his direction
     */
    val c8: Point? by BwPointOrNull()

    /**
     * The 9 Point. use this to set an exact Point the wall will go through
     */
    val p9: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 9 Point. Use this to guide the curve to his direction
     */
    val c9: Point? by BwPointOrNull()

    /**
     * The 10 Point. use this to set an exact Point the wall will go through
     */
    val p10: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 10 Point. Use this to guide the curve to his direction
     */
    val c10: Point? by BwPointOrNull()

    /**
     * The 11 Point. use this to set an exact Point the wall will go through
     */
    val p11: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 11 Point. Use this to guide the curve to his direction
     */
    val c11: Point? by BwPointOrNull()

    /**
     * The 12 Point. use this to set an exact Point the wall will go through
     */
    val p12: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 12 Point. Use this to guide the curve to his direction
     */
    val c12: Point? by BwPointOrNull()

    /**
     * The 13 Point. use this to set an exact Point the wall will go through
     */
    val p13: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 13 Point. Use this to guide the curve to his direction
     */
    val c13: Point? by BwPointOrNull()

    /**
     * The 14 Point. use this to set an exact Point the wall will go through
     */
    val p14: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 14 Point. Use this to guide the curve to his direction
     */
    val c14: Point? by BwPointOrNull()

    /**
     * The 15 Point. use this to set an exact Point the wall will go through
     */
    val p15: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 15 Point. Use this to guide the curve to his direction
     */
    val c15: Point? by BwPointOrNull()

    /**
     * The 16 Point. use this to set an exact Point the wall will go through
     */
    val p16: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 16 Point. Use this to guide the curve to his direction
     */
    val c16: Point? by BwPointOrNull()

    /**
     * The 17 Point. use this to set an exact Point the wall will go through
     */
    val p17: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 17 Point. Use this to guide the curve to his direction
     */
    val c17: Point? by BwPointOrNull()

    /**
     * The 18 Point. use this to set an exact Point the wall will go through
     */
    val p18: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 18 Point. Use this to guide the curve to his direction
     */
    val c18: Point? by BwPointOrNull()

    /**
     * The 19 Point. use this to set an exact Point the wall will go through
     */
    val p19: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 19 Point. Use this to guide the curve to his direction
     */
    val c19: Point? by BwPointOrNull()

    /**
     * The 20 Point. use this to set an exact Point the wall will go through
     */
    val p20: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 20 Point. Use this to guide the curve to his direction
     */
    val c20: Point? by BwPointOrNull()

    /**
     * The 21 Point. use this to set an exact Point the wall will go through
     */
    val p21: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 21 Point. Use this to guide the curve to his direction
     */
    val c21: Point? by BwPointOrNull()

    /**
     * The 22 Point. use this to set an exact Point the wall will go through
     */
    val p22: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 22 Point. Use this to guide the curve to his direction
     */
    val c22: Point? by BwPointOrNull()

    /**
     * The 23 Point. use this to set an exact Point the wall will go through
     */
    val p23: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 23 Point. Use this to guide the curve to his direction
     */
    val c23: Point? by BwPointOrNull()

    /**
     * The 24 Point. use this to set an exact Point the wall will go through
     */
    val p24: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 24 Point. Use this to guide the curve to his direction
     */
    val c24: Point? by BwPointOrNull()

    /**
     * The 25 Point. use this to set an exact Point the wall will go through
     */
    val p25: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 25 Point. Use this to guide the curve to his direction
     */
    val c25: Point? by BwPointOrNull()

    /**
     * The 26 Point. use this to set an exact Point the wall will go through
     */
    val p26: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 26 Point. Use this to guide the curve to his direction
     */
    val c26: Point? by BwPointOrNull()

    /**
     * The 27 Point. use this to set an exact Point the wall will go through
     */
    val p27: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 27 Point. Use this to guide the curve to his direction
     */
    val c27: Point? by BwPointOrNull()

    /**
     * The 28 Point. use this to set an exact Point the wall will go through
     */
    val p28: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 28 Point. Use this to guide the curve to his direction
     */
    val c28: Point? by BwPointOrNull()

    /**
     * The 29 Point. use this to set an exact Point the wall will go through
     */
    val p29: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 29 Point. Use this to guide the curve to his direction
     */
    val c29: Point? by BwPointOrNull()

    /**
     * The 30 Point. use this to set an exact Point the wall will go through
     */
    val p30: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 30 Point. Use this to guide the curve to his direction
     */
    val c30: Point? by BwPointOrNull()

    /**
     * The 31 Point. use this to set an exact Point the wall will go through
     */
    val p31: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 31 Point. Use this to guide the curve to his direction
     */
    val c31: Point? by BwPointOrNull()

    /**
     * The 32 Point. use this to set an exact Point the wall will go through
     */
    val p32: Point? by BwPointOrNull()

    /**
     * The ControllPoint for the 32 Point. Use this to guide the curve to his direction
     */
    val c32: Point? by BwPointOrNull()

    /**
     * generating the Walls
     */
     override fun generateWalls()  = run() 
}


/**
 * This Structure is used for internal testing does not do anything for you
 */
class TestStructure: WallStructure(){
    val testDouble by BwDouble()
    val testDoubleOrNull by BwDoubleOrNull()
    val testInt by BwInt()
    val testIntOrNull by BwIntOrNull()
    val testPoint by BwPoint()
    val nonBwProperty = 10

    override fun generateWalls(): List<SpookyWall>  = listOf(SpookyWall(0,1,1,1,0,0))
}
