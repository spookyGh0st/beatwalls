@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package structure

import mu.KotlinLogging
import java.io.*
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
    var mirror: Int = 0

    /**
     * times the SpookyWall by adding the njsOffset
     */
    var time: Boolean = false

    //    ___    ____      ____  _____________
    //   /   |  / __ \    / / / / / ___/_  __/
    //  / /| | / / / /_  / / / / /\__ \ / /
    // / ___ |/ /_/ / /_/ / /_/ /___/ // /
    ///_/  |_/_____/\____/\____//____//_/

    //changing the Values
    var changeStartTime: Double? = null

    var changeDuration: Double? = null

    var changeHeight: Double? = null

    var changeStartHeight: Double? = null

    var changeStartRow: Double? = null

    var changeWidth: Double? = null

    // scaling the Values
    var scaleStartTime: Double? = null

    var scaleDuration: Double? = null

    var scaleHeight: Double? = null

    var scaleStartHeight: Double? = null

    var scaleStartRow: Double? = null

    var scaleWidth: Double? = null

    // adding to the Values
    var addStartTime: Double? = null

    var addDuration: Double? = null

    var addHeight: Double? = null

    var addStartHeight: Double? = null

    var addStartRow: Double? = null

    var addWidth: Double? = null

    // fits the height, adjust the correspongig value, that all wall have the same, given value
    // (for example startheight 0 -> sets startheight to 0 and adjust the height, that the max height is the same
    var fitStartTime: Double? = null

    var fitDuration: Double? = null

    var fitHeight: Double? = null

    var fitStartHeight: Double? = null

    var fitStartRow: Double? = null

    var fitWidth: Double? = null

    /**
     * scales the Duration and startTime,
     * does not scale the duration of negative walls
     */
    var scale: Double? = null

    /**
     * reverses the WallStructure
     */
    var reverse: Boolean? = null

    //todo fit to

    //    ____  __________  _________  ______
    //   / __ \/ ____/ __ \/ ____/   |/_  __/
    //  / /_/ / __/ / /_/ / __/ / /| | / /
    // / _, _/ /___/ ____/ /___/ ___ |/ /
    ///_/ |_/_____/_/   /_____/_/  |_/_/

    /**
     * how often you want to repeat the Structure
     */
    var repeat: Int = 1

    /**
     * The Gap between each Repeat
     */
    var repeatAddZ: Double = 1.0

    /**
     * shifts each repeat in x
     */
    var repeatAddX: Double = 0.0

    /**
     * shifts each repeated Structure in y
     */
    var repeatAddY: Double = 0.0

    //todo add repeatPath

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



class CustomWallStructure(val name:String): WallStructure()

object EmptyWallStructure:WallStructure()

//   _____                 _       __   _       __      _________ __                  __
//  / ___/____  ___  _____(_)___ _/ /  | |     / /___ _/ / / ___// /________  _______/ /___  __________  _____
//  \__ \/ __ \/ _ \/ ___/ / __ `/ /   | | /| / / __ `/ / /\__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// ___/ / /_/ /  __/ /__/ / /_/ / /    | |/ |/ / /_/ / / /___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///____/ .___/\___/\___/_/\__,_/_/     |__/|__/\__,_/_/_//____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/
//    /_/

class TestWallStructure(val test:Boolean = false): WallStructure()

/**
 * Random Noise (small mini cubes)
 */
class RandomNoise:WallStructure(){
    /**
     * the amount of the created Walls
     */
    var amount: Int  = 10

    /**
     * controls one corner of the Area
     */
    var sp = Point(-6,0,0)

    /**
     * controls the other corner of the Area
     */
    var ep = Point(6,5,1)


    override fun run() {
        val sx = min(sp.x,ep.x)
        val ex = max(sp.x,ep.x)
        val sy = min(sp.y,ep.y)
        val ey = max(sp.y,ep.y)
        val sz = min(sp.z,ep.z)
        val ez = max(sp.z,ep.z)
        repeat(amount){
            val w = SpookyWall(
                startRow = Random.nextDouble(sx,ex),
                duration = 0.0,
                width = 0.0,
                height = 0.0,
                startHeight = Random.nextDouble(sy,ey),
                startTime = sz + (it.toDouble()/amount * (ez-sz))
            )
            add(w)
        }
    }
}

/**
 * A BezierCurve with 4 ControlPoints
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
 * Saves All WallStructures between the beat and the given name to the BeatWallAsset. Replacs given WallStructue with the given name
 */
class Save:WallStructure(){
    /**
     * the name you want your structure to be saved in
     */
    var name: String = "myStructure"
    /**
     * the duration in between you want to look for walls
     */
    var duration: Double = 1.0
    override fun run() {
    }
}

/**
 * Defines a WallStructure. You can provide multiple internal structures. Does not get saved to BeatWallAsset.json
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
            l.forEach { it.startTime+=w.beat }
            add(l)
        }
    }
}

/**
 * Defines a Single Wall
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

//todo add text
//todo add more random stuff