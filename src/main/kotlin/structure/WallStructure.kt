@file:Suppress("unused")

package structure

import mu.KotlinLogging
import java.io.*
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
     * saved Walls
     */
    private var walls: ArrayList<Wall> = arrayListOf()
    /**
     * the Beat, dont change that, it will get overwritten anyway
     */
    var beat: Double = 0.0

    /**
     * mirrors the Wall:
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
     * times the Wall by adding the njsOffset
     */
    var time: Boolean = false

    /**
     * changes the Duration to the given value
     */
    var changeDuration: Double? = null

    /**
     * how often you want to repeat the Structure
     */
    var repeat: Int = 1

    /**
     * The Gap between each Repeat
     */
    var repeatGap: Int = 1

    /**
     * shifts each repeat in x
     */
    var repeatShiftX: Double = 0.0


    /**
     * shifts each repeated Structure in y
     */
    var repeatShiftY: Double = 0.0

    fun walls(): ArrayList<Wall> {
        repeat()
        run()
        adjustValues()
        mirror()
        return walls
    }

    private fun mirror(){
        var otherWalls: ArrayList<Wall> = arrayListOf()
         when(mirror){
             1->walls.forEach { it.mirror() }
             2-> {otherWalls = copyWalls();walls.forEach { it.mirror() }}
             3->walls.forEach {it.verticalMirror()}
             4-> {otherWalls = copyWalls();walls.forEach { it.verticalMirror() }}
             5->walls.forEach {it.pointMirror()}
             6-> {otherWalls = copyWalls();walls.forEach { it.pointMirror() }}
             7-> {otherWalls = copyWalls()
                 walls.forEach { it.verticalMirror() }
                 walls.addAll(otherWalls)
                 otherWalls =copyWalls()
                 walls.forEach { it.mirror()}}
             8-> {otherWalls =copyWalls()
                 walls.forEach { it.pointMirror() }
                 walls.addAll(otherWalls)
                 otherWalls = copyWalls()
                 walls.forEach { it.mirror()}}
        }
        walls.addAll(otherWalls)
    }

    private fun repeat(){
        val tempWalls  = arrayListOf<Wall>()
        for (i in 1 until repeat){
            val temp = this.deepCopy()
            temp.run()
            temp.walls.forEach {
                it.startTime+=repeatGap*i
                it.startRow += repeatShiftX*i
                it.startHeight += repeatShiftY*i
            }
            tempWalls.addAll(temp.walls)
        }
        add(tempWalls)
    }

    private fun copyWalls() :ArrayList<Wall> = ArrayList((walls.map { it.copy() }))

    private fun adjustValues(){
        if (changeDuration!=null)
            walls.forEach { it.duration = changeDuration as Double }
    }


    protected open fun run(){}
    fun add(w:Wall){
        walls.add(w)
    }
    fun add(w:Collection<Wall>){
        walls.addAll(w)
    }
    fun deepCopy():WallStructure = deepCopyBySer(this)

    /**
     * generated Funktions
     */

    override fun toString(): String {
        return "WallStructure(walls=$walls, beat=$beat, mirror=$mirror, time=$time, changeDuration=$changeDuration, repeat=$repeat, repeatGap=$repeatGap, repeatShiftX=$repeatShiftX, repeatShiftY=$repeatShiftY)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WallStructure

        if (walls != other.walls) return false
        if (beat != other.beat) return false
        if (mirror != other.mirror) return false
        if (time != other.time) return false
        if (changeDuration != other.changeDuration) return false
        if (repeat != other.repeat) return false
        if (repeatGap != other.repeatGap) return false
        if (repeatShiftX != other.repeatShiftX) return false
        if (repeatShiftY != other.repeatShiftY) return false

        return true
    }

    override fun hashCode(): Int {
        var result = walls.hashCode()
        result = 31 * result + beat.hashCode()
        result = 31 * result + mirror
        result = 31 * result + time.hashCode()
        result = 31 * result + (changeDuration?.hashCode() ?: 0)
        result = 31 * result + repeat
        result = 31 * result + repeatGap
        result = 31 * result + repeatShiftX.hashCode()
        result = 31 * result + repeatShiftY.hashCode()
        return result
    }
}

/**
 * workaround for deep copy
 */
private fun <T : Serializable> deepCopyBySer(obj: T): T {
    val baos = ByteArrayOutputStream()
    val oos  = ObjectOutputStream(baos)
    oos.writeObject(obj)
    oos.close()
    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois  = ObjectInputStream(bais)
    @Suppress("unchecked_cast")
    return ois.readObject() as T
}

class CustomWallStructure(val name:String): WallStructure()

object EmptyWallStructure:WallStructure(){
}


//   _____                 _       __   _       __      _________ __                  __
//  / ___/____  ___  _____(_)___ _/ /  | |     / /___ _/ / / ___// /________  _______/ /___  __________  _____
//  \__ \/ __ \/ _ \/ ___/ / __ `/ /   | | /| / / __ `/ / /\__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// ___/ / /_/ /  __/ /__/ / /_/ / /    | |/ |/ / /_/ / / /___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///____/ .___/\___/\___/_/\__,_/_/     |__/|__/\__,_/_/_//____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/
//    /_/

class TestWallStructure(val test:Boolean = false): WallStructure()

class RandomNoise:WallStructure(){
    /**
     * the amount of the created Walls
     */
    var amount: Int  = 10
    override fun run() {
        repeat(amount){
            val w = Wall(
                startRow = Random.nextDouble(-6.0,6.0),
                duration = 0.0,
                width = 0.0,
                height = 0.0,
                startHeight = Random.nextDouble(5.0),
                startTime = it.toDouble()/amount
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
    var sp: Point = Point(0,0,0)
    /**
     * The EndPoint of the Curve
     */
    var ep: Point = Point(0,0,0)
    /**
     * the first Controllpoint
     */
    var cp1: Point = sp.copy()
    /**
     * second ControlPoint
     */
    var cp2: Point = ep.copy()
    /**
     * amount of Walls
     */
    var amount: Int = 8
    override fun run() {
        add(curve(sp,cp1,cp2,ep,amount))
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
     * The name of Different Structures. Separated by comma (example: structures: Floor, Ceiling)
     * You can also define Parameters of the first Structure
     * These get loaded in Order, So if your reference defined Structures, those must be listed before that
     * The Beat Value gets every time, so it should be 0 most of the time
     */
    var structures = listOf<WallStructure>()
}

fun main (){
    val w = CustomWallStructure("test1")
    w.walls().add(Wall(1.1,1.1,1.1,1.1,1.1,1.1))
    val w2 = w.deepCopy()
    w.walls().forEach { it.startRow+=2 }
    println(w.walls().first().startRow)
    println(w2.walls().first().startRow)
}
