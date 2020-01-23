@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package structure

import assetFile.findProperty
import assetFile.readProperty
import mu.KotlinLogging
import java.io.Serializable
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


private val logger = KotlinLogging.logger {}

//    _   __                           __   _____ __                  __
//   / | / /___  _________ ___  ____ _/ /  / ___// /________  _______/ /___  __________  _____
//  /  |/ / __ \/ ___/ __ `__ \/ __ `/ /   \__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// / /|  / /_/ / /  / / / / / / /_/ / /   ___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///_/ |_/\____/_/  /_/ /_/ /_/\__,_/_/   /____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/

sealed class WallStructure:Serializable
{
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
     * mirrors the SpookyWall:
     *
     *  0 -> dont mirror,
     *  1-> mirror to the other side,
     *  2-> mirror to the other side and duplicate,
     *  3-> mirror horizontal on y=2
     *  4-> mirror horizontal and duplicate
     *  5-> mirror on the center of x=0, y=2
     *  6-> mirror on the center and duplicate
     *  7-> mirror horizontal and on the other side and duplicate all 4
     *  8-> mirror on the center and on the other side and duplicate all 4
     */
    //todo mirror 6 and than mirror that
    var mirror: Int = Default.mirror

    /**
     * times the SpookyWall by adding the njsOffset, default: true
     */
    var time: Boolean = Default.time

    /**
     * change The StartTime of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeStartTime: (() -> Double)? = Default.changeStartTime

    /**
     * change The Duration of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeDuration: (() -> Double)? = Default.changeDuration

    /**
     * change The Height of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeHeight: (() -> Double)? = Default.changeHeight

    /**
     * change The StartHeight of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeStartHeight: (() -> Double)? = Default.changeStartHeight

    /**
     * change The StartRow of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeStartRow: (() -> Double)? = Default.changeStartRow

    /**
     * change the Width of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    var changeWidth: (() -> Double)? = Default.changeWidth

    /**
     * multiplies the StartTime of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleStartTime: (() -> Double)? = Default.scaleStartTime

    /**
     * multiplies the Duration of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleDuration: (() -> Double)? = Default.scaleDuration

    /**
     * multiplies the Height of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleHeight: (() -> Double)? = Default.scaleHeight

    /**
     * multiplies the StartHeight of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleStartHeight: (() -> Double)? = Default.scaleStartHeight

    /**
     * multiplies the StartRow of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleStartRow: (() -> Double)? = Default.scaleStartRow

    /**
     * multiplies the Width of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var scaleWidth: (() -> Double)? = Default.scaleWidth

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addStartTime: (() -> Double)? = Default.addStartTime

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addDuration: (() -> Double)? = Default.addDuration

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addHeight: (() -> Double)? = Default.addHeight

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addStartHeight: (() -> Double)? = Default.addStartHeight

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addStartRow: (() -> Double)? = Default.addStartRow

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    var addWidth: (() -> Double)? = Default.addWidth

    /**
     * increases or decreases the duration of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    var fitStartTime: (() -> Double)? = Default.fitStartTime

    /**
     * increases or decreases the StartTime of all walls until they have the the specific duration. Random possible with random(min,max). default: null (does nothing)
     */
    var fitDuration: (() -> Double)? = Default.fitDuration

    /**
     * increases or decreases the StartHeight of all walls until they have the the specific Height. Random possible with random(min,max). default: null (does nothing)
     */
    var fitHeight: (() -> Double)? = Default.fitHeight

    /**
     * increases or decreases the height of all walls until they have the the specific startHeight. Random possible with random(min,max). default: null (does nothing)
     */
    var fitStartHeight: (() -> Double)? = Default.fitStartHeight

    /**
     * increases or decreases the width of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    var fitStartRow: (() -> Double)? = Default.fitStartRow

    /**
     * increases or decreases the StartRow of all walls until they have the the specific Width. Random possible with random(min,max). default: null (does nothing)
     */
    var fitWidth: (() -> Double)? = Default.fitWidth

    /**
     * scales the Duration and startTime, (duration only for positive duration).
     * This is useful for making a structure, that is one beat long longer or shorter
     */
    var scale: Double? = Default.scale

    /**
     * reverses the WallStructure on the Starttime/duration
     */
    var reverse: Boolean = Default.reverse

    /**
     * basically mirrors the Wallstructure in itself on the x-Achsis if set to true
     */
    var reverseX: Boolean = Default.reverseX

    /**
     * basically mirrors the Wallstructure in itself on the x-Achsis if set to true
     */
    var reverseY: Boolean = Default.reverseY

    /**
     * speeds up the wallstructure over time. the duration of the whole structure. Remains. \n
     *
     * value 0-1 start is slower, speed up over time, \n
     *
     * 1-100 start is faster, slow down over time \n
     *
     * The closer the value is to 1, the more stale it gets.
     */
    var speeder: Double? = Default.speeder

    /**
     * how often you want to repeat the Structure
     */
    var repeat: Int = Default.repeat

    /**
     * The Gap between each Repeat
     */
    var repeatAddZ: Double = Default.repeatAddZ

    /**
     * shifts each repeat in x
     */
    var repeatAddX: Double = Default.repeatAddX

    /**
     * shifts each repeated Structure in y
     */
    var repeatAddY: Double = Default.repeatAddY

    /**
     * adds this value to the Duration to each repeated Structure
     */
    var repeatAddDuration: Double = Default.repeatAddDuration

    /**
     * adds this value to the Height to each repeated Structure
     */
    var repeatAddHeight: Double = Default.repeatAddHeight

    /**
     * adds this value to the Width to each repeated Structure
     */
    var repeatAddWidth: Double = Default.repeatAddWidth

    /**
     * adds this value to the StartRow to each repeated Structure
     */
    var repeatAddStartRow: Double = Default.repeatAddStartRow

    /**
     * adds this value to the StartHeight to each repeated Structure
     */
    var repeatAddStartHeight: Double = Default.repeatAddStartHeight
    /**
     * adds this value to the StartTime to each repeated Structure
     */
    var repeatAddStartTime: Double = Default.repeatAddStartTime

    /**
     * some Wallstructures use Random walls. This is the seed for them
     */
    var seed: Int = Default.seed ?: Random.nextInt()

    companion object Default {
        var mirror: Int = 0

        var time: Boolean = true

        var changeStartTime: (() -> Double)? = null

        var changeDuration: (() -> Double)? = null

        var changeHeight: (() -> Double)? = null

        var changeStartHeight: (() -> Double)? = null

        var changeStartRow: (() -> Double)? = null

        var changeWidth: (() -> Double)? = null

        var scaleStartTime: (() -> Double)? = null

        var scaleDuration: (() -> Double)? = null

        var scaleHeight: (() -> Double)? = null

        var scaleStartHeight: (() -> Double)? = null

        var scaleStartRow: (() -> Double)? = null

        var scaleWidth: (() -> Double)? = null

        var addStartTime: (() -> Double)? = null

        var addDuration: (() -> Double)? = null

        var addHeight: (() -> Double)? = null

        var addStartHeight: (() -> Double)? = null

        var addStartRow: (() -> Double)? = null

        var addWidth: (() -> Double)? = null

        var fitStartTime: (() -> Double)? = null

        var fitDuration: (() -> Double)? = null

        var fitHeight: (() -> Double)? = null

        var fitStartHeight: (() -> Double)? = null

        var fitStartRow: (() -> Double)? = null

        var fitWidth: (() -> Double)? = null

        var scale: Double? = null

        var reverse: Boolean = false

        var reverseX: Boolean = false

        var reverseY: Boolean = false

        var speeder: Double? = null

        //todo remove repeat and build loop

        var repeat: Int = 1

        var repeatAddZ: Double = 1.0

        var repeatAddX: Double = 0.0

        var repeatAddY: Double = 0.0

        var repeatAddWidth: Double = 0.0

        var repeatAddStartRow: Double = 0.0

        var repeatAddStartHeight: Double = 0.0

        var repeatAddHeight: Double = 0.0

        var repeatAddStartTime: Double = 0.0

        var repeatAddDuration: Double = 0.0

        var seed: Int? = null

    }

    open fun run(){}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WallStructure

        if (spookyWalls != other.spookyWalls) return false
        if (beat != other.beat) return false
        if (mirror != other.mirror) return false
        if (time != other.time) return false
        if (changeStartTime != other.changeStartTime) return false
        if (changeDuration != other.changeDuration) return false
        if (changeHeight != other.changeHeight) return false
        if (changeStartHeight != other.changeStartHeight) return false
        if (changeStartRow != other.changeStartRow) return false
        if (changeWidth != other.changeWidth) return false
        if (scaleStartTime != other.scaleStartTime) return false
        if (scaleDuration != other.scaleDuration) return false
        if (scaleHeight != other.scaleHeight) return false
        if (scaleStartHeight != other.scaleStartHeight) return false
        if (scaleStartRow != other.scaleStartRow) return false
        if (scaleWidth != other.scaleWidth) return false
        if (addStartTime != other.addStartTime) return false
        if (addDuration != other.addDuration) return false
        if (addHeight != other.addHeight) return false
        if (addStartHeight != other.addStartHeight) return false
        if (addStartRow != other.addStartRow) return false
        if (addWidth != other.addWidth) return false
        if (scale != other.scale) return false
        if (repeat != other.repeat) return false
        if (repeatAddZ != other.repeatAddZ) return false
        if (repeatAddX != other.repeatAddX) return false
        if (repeatAddY != other.repeatAddY) return false

        return true
    }

    override fun hashCode(): Int {
        var result = spookyWalls.hashCode()
        result = 31 * result + beat.hashCode()
        result = 31 * result + mirror
        result = 31 * result + time.hashCode()
        result = 31 * result + (changeStartTime?.hashCode() ?: 0)
        result = 31 * result + (changeDuration?.hashCode() ?: 0)
        result = 31 * result + (changeHeight?.hashCode() ?: 0)
        result = 31 * result + (changeStartHeight?.hashCode() ?: 0)
        result = 31 * result + (changeStartRow?.hashCode() ?: 0)
        result = 31 * result + (changeWidth?.hashCode() ?: 0)
        result = 31 * result + (scaleStartTime?.hashCode() ?: 0)
        result = 31 * result + (scaleDuration?.hashCode() ?: 0)
        result = 31 * result + (scaleHeight?.hashCode() ?: 0)
        result = 31 * result + (scaleStartHeight?.hashCode() ?: 0)
        result = 31 * result + (scaleStartRow?.hashCode() ?: 0)
        result = 31 * result + (scaleWidth?.hashCode() ?: 0)
        result = 31 * result + (addStartTime?.hashCode() ?: 0)
        result = 31 * result + (addDuration?.hashCode() ?: 0)
        result = 31 * result + (addHeight?.hashCode() ?: 0)
        result = 31 * result + (addStartHeight?.hashCode() ?: 0)
        result = 31 * result + (addStartRow?.hashCode() ?: 0)
        result = 31 * result + (addWidth?.hashCode() ?: 0)
        result = 31 * result + (scale?.hashCode() ?: 0)
        result = 31 * result + repeat
        result = 31 * result + repeatAddZ.hashCode()
        result = 31 * result + repeatAddX.hashCode()
        result = 31 * result + repeatAddY.hashCode()
        return result
    }
}


/**
 * dont touch
 */
object EmptyWallStructure:WallStructure()

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
    var amount: Int? = null

    /**
     * controls one corner of the Area
     */
    var p1 = Point(-6,0,0)

    /**
     * controls the other corner of the Area
     */
    var p2 = Point(6,5,1)


    override fun run() {
        val sx = min(p1.x, p2.x)
        val ex = max(p1.x, p2.x).coerceAtLeast(sx + 0.0000001)
        val sy = min(p1.y, p2.y)
        val ey = max(p1.y, p2.y).coerceAtLeast(sy + 0.0000001)
        val sz = min(p1.z, p2.z)
        val ez = max(p1.z, p2.z).coerceAtLeast(sz + 0.0000001)
        val r = Random(seed)
        amount = amount ?: (8 * (ez - sz)).toInt()
        repeat(amount!!) {

            val w = SpookyWall(
                startRow = r.nextDouble(sx, ex),
                duration = 0.0,
                width = 0.0,
                height = 0.0,
                startHeight = r.nextDouble(sy, ey),
                startTime = sz + (it.toDouble() / amount!! * (ez - sz))
            )
            add(w)
        }
    }
}

/**
 * Draw a curve of Walls. This uses BezierCurve. You can imagine it like a line between point 1 and point 4, that gets pulled upon by the controlpoints. Maybe this link can help (the dots are the Points) https://www.desmos.com/calculator/cahqdxeshd
 */
class Curve:WallStructure(){
    /**
     * the start Point of the Curve
     */
    var p1: Point = Point(0,0,0)
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
    var p4: Point = Point(0,0,0)
    /**
     * amount of Walls
     */
    var amount: Int = 8
    override fun run() {
        add(curve(p1, p2?:p1,p3?: p4, p4,amount))
    }
}
/**
 * Draw a steady curve of Walls. that is exactly 1 beat long
 * */
class SteadyCurve:WallStructure(){
    /**
     * the start Point of the Curve
     */
    var p1: Point = Point(0,0,0)
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
    var p4: Point = Point(0,0,1)
    /**
     * amount of Walls
     */
    var amount: Int = 8
    override fun run() {
        p1=p1.copy(z=0.0)
        p2= p2?.copy(z=0.3333)
        p3= p3?.copy(z=0.6666)
        p4=p4.copy(z=1.0)
        add(curve(p1, p2?:p1,p3?: p4, p4,amount))
    }
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

    override fun run() {
        for(w in structures){
            val l = w.walls()
            l.forEach { it.startTime+=(w.beat) }
            add(l)
        }
    }
}

/**
 * create a single Wall
 */
class Wall: WallStructure() {
    var startTime = 0.0
    var duration = 1.0
    var startHeight = 0.0
    var height = 0.0
    var startRow = 0.0
    var width = 0.0

    override fun run() {
        add(SpookyWall(
            startRow = startRow,
            duration = duration,
            width = width,
            height = height,
            startHeight = startHeight,
            startTime = startTime
        ))
    }
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
    var center = Point(0,2,0)

    override fun run() {
        add(circle(count = count, fineTuning = amount, heightOffset = center.y, radius = radius,startRotation = startRotation,rotationCount = rotationAmount,helix = true))
    }
}

/**
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

    override fun run() {
        TODO()
    }
}


/**
 * Draws a wall of line between the 2 provided Points
 */
class Line: WallStructure(){
    /**
     * how many walls will be created. When left empty, will figure out a decent amount depending on the angle
     */
    var amount: Int? = null
    /**
     * The startPoint
     */
    var p1 = Point(0,0,0)
    /**
     * the End Point
     */
    var p2 = Point(0,0,1)
    override fun run() {
        add(line(p1,p2,amount))
        super.run()
    }
}

/**
 * place random blocks around the player
 */
class RandomBlocks: WallStructure(){
    var duration = 4
    var amount= 8
    var wallDuration = 1.0
    override fun run() {
        repeat(amount){
            add(createBlock(it.toDouble()/amount*duration,wallDuration))
        }
    }
    private fun createBlock(st:Double ,d:Double): SpookyWall {
        val r = Random(seed)
        val sr = r.nextDouble(-20.0, 20.0)
        val w = sr * r.nextDouble()
        val sh = r.nextDouble(5.0)
        val h = sr * r.nextDouble(0.2)
        return SpookyWall(sr, d, w, h, sh, st)


    }
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
    var p1: Point = if(randomSideChooser) Point(1,0,0) else Point(-1,0,0)

    /**
     * second Point that crontrols in which section walls are created. z must be higher than p1
     */
    var p2: Point = if(randomSideChooser) Point(4,0,1) else Point(-4,4,1)
    /**
     * the amount of Walls per beat
     */
    var amount: Int = 8

    override fun run() {
        val r = Random(seed)
        val mult: Double
        if((p2.z-p1.z) < 1){
            mult = 1 / (p2.z-p1.z)
            p2  = p2.copy(z=p1.z+1)
        }else{
            mult = 1.0
        }
        var tp3 = randomTimedPoint(r, -0.33 * mult)
        var tp4 = p1
        for(i in p1.z.toInt() until p2.z.toInt()) {
            val tp1 = tp4.copy()
            val tp2 = tp4.mirrored(tp3)
            tp3 = randomTimedPoint(r, i + 0.66 * mult)
            tp4 = if (i + 1 == p2.z.toInt())
                p2.copy(z = p2.z + 1)
            else
                randomTimedPoint(r, i + 1.0 * mult)
            add(curve(tp1, tp2, tp3, tp4, amount))
        }
    }

    private fun randomTimedPoint(r: Random, z: Double): Point {
        val minx = min(p1.x, p2.x)
        val maxX = max(p1.x, p2.x).coerceAtLeast(minx + 0.1)
        val minY = min(p1.y, p2.y)
        val maxY = max(p1.y, p2.y).coerceAtLeast(minY + 0.1)
        val x = r.nextDouble(minx, maxX)
        val y = r.nextDouble(minY, maxY)
        return Point(x, y, z)
    }
}

/**
 * Helix Curve lets you define 1/4 of a curve around the center. The program creates the rest of the helix
 */

class HelixCurve: WallStructure() {
    /**
     * the start Point of the Curve
     */
    var p1: Point = Point(0,0,0)
    /**
     * the first Controllpoint, defaults to the startPoint
     */
    var p2: Point = Point(4.0,0.0,0.33)
    /**
     * second ControlPoint, defaults to the end point
     */
    var p3: Point = Point(4.0,0.0,0.66)
    /**
     * The EndPoint of the Curve
     */
    var p4: Point = Point(4,2,1)

    /**
     * the amount of walls per Helix
     */
    var amount: Int = 8
    /**
     * how many helix spines will be Created. Only 2 or 4 allowed
     */
    var count: Int = 4

    override fun run() {
        val center = Point(0,2,0)
        add(curve(p1,p2,p3,p4,amount))
        add(curve(center.mirroredNoZ(p1),center.mirroredNoZ(p2), center.mirroredNoZ(p3), center.mirroredNoZ(p4), amount))
        if(count == 4)
            TODO()
    }
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

    override fun run() {
        for(i in 1 until creationAmount){
            val point = readPoint("p$i")
            val controlPoint = readPoint("c$i")
            val nextPoint = readPoint("p${i +1}")
            val nextControlPoint = readPoint("c${i+1}")
            val nextNextPoint = try { readPoint("p${i+2}") } catch (e:Exception){ null }
            if(point!=null && nextPoint!=null && controlPoint!=null && nextControlPoint!= null) {
                val tempP1 = point
                val tempP2 = controlPoint.copy(z = point.z + (1/3.0) * (nextPoint.z - point.z))
                val tempP3 = calcP3(point, nextPoint, nextControlPoint, nextNextPoint)
                val tempP4 = nextPoint
                val amount = ((tempP4.z - tempP1.z) * amount).toInt()
                add(curve(tempP1, tempP2, tempP3, tempP4, amount))
            }
        }
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

    override fun run() {
        for(i in 1 until creationAmount){
            val point = readPoint("p$i")
            val controlPoint = readPoint("c$i")
            val nextPoint = readPoint("p${i +1}")
            val nextControlPoint = readPoint("c${i+1}")
            val nextNextPoint = try { readPoint("p${i+2}") } catch (e:Exception){ null }
            if(point!=null && nextPoint!=null && controlPoint!=null && nextControlPoint!= null) {
                val tempP1 = point
                val tempP2 = controlPoint.copy(z = point.z + (1/3.0) * (nextPoint.z - point.z))
                val tempP3 = calcP3(point, nextPoint, nextControlPoint, nextNextPoint)
                val tempP4 = nextPoint
                val amount = ((tempP4.z - tempP1.z) * amount).toInt()
                add(curve(tempP1, tempP2, tempP3, tempP4, amount))
            }
        }
    }
}

fun calcP3(point: Point, nextPoint: Point, nextControlPoint:Point, nextNextPoint: Point?): Point {
    //todo something is still not right
    val defaultOffset = point.z + (nextPoint.z-point.z)
    val nextControlPointZ = nextPoint.z + (1/3.0) * ((nextNextPoint?.z ?: defaultOffset) - nextPoint.z)
    val nextCp = nextControlPoint.copy(z = nextControlPointZ)
    val cp = nextPoint.mirrored(nextCp)
    val z = point.z+0.66*(nextPoint.z-point.z)
    val m = z-cp.z
    val x = cp.x + m*(point.x-cp.x)
    val y = cp.y + m*(point.y-cp.y)
    return Point(x,y,z)
}

fun WallStructure.readPoint(name:String): Point? =
    this.readProperty(findProperty(this,name)) as Point?

@Suppress("DEPRECATION")
fun ContinuesCurve.generateProperties(): String {
    var s = ""
    for(it in 1 .. creationAmount){
        s+="""
            
            
            /**
            * The $it Point. use this to set an exact Point the wall will go through
            */
            var p$it: Point? = null
            
            /** 
            * The ControllPoint for the $it Point. Use this to guide the curve to his direction
            */
            var c$it: Point? = null
        """.trimIndent()
    }
    return s
}
