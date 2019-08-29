package structures

import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import song._obstacles
import java.lang.Exception
import kotlin.math.*
import kotlin.random.Random

private val logger = KotlinLogging.logger {}

interface WallStructure {
    val name: String

    val mirror: Boolean

    val myObstacleList: ArrayList<MyObstacle>

    fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        return adjustObstacles(parameters)
    }
    fun adjustObstacles(parameters: Parameters):ArrayList<_obstacles>{
        val list = arrayListOf<_obstacles>()
        myObstacleList.forEach {
                val a = it.adjustParameters(parameters)
                list.add(a.to_obstacle())
                if(mirror) list.add(a.mirror().to_obstacle())
        }
        logger.info { "added ${parameters.name}" }
        return list
    }
}

/** gets helix with fixed duration */
object Helix: WallStructure{
    override val name: String = "helix"
    override val mirror: Boolean = false
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val amount = parameters.customParameters.getIntOrElse(0,1)
        val intensity = parameters.customParameters.getIntOrElse(1,10)
        val start = parameters.customParameters.getDoubleOrElse(2,0.0)
        myObstacleList.addAll( circle(pOffset = start,fineTuning = intensity, count = amount,helix = true))
        return super.getObstacleList(parameters)
    }
}

/** gets helix with fixed duration */
object MirroredHelix: WallStructure{
    override val name: String = "MirroredHelix"
    override val mirror: Boolean = true
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val amount = parameters.customParameters.getIntOrElse(0,1)
        val start = parameters.customParameters.getDoubleOrElse(1,0.0)
        myObstacleList.addAll( circle(pOffset = start, count = amount,helix = true))
        return super.getObstacleList(parameters)
    }
}

/** gets helix with walls with the duration 0*/
object EmptyHelix: WallStructure{
    override val name: String = "EmptyHelix"
    override val mirror: Boolean = false
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val amount = parameters.customParameters.getIntOrElse(0,1)
        val start = parameters.customParameters.getDoubleOrElse(1,0.0)
        myObstacleList.addAll( circle(pDuration = 0.0,pOffset = start, count = amount,helix = true))
        return super.getObstacleList(parameters)
    }
}

/** gets helix with fast walls */
object FastHelix: WallStructure{
    override val name: String = "FastHelix"
    override val mirror: Boolean = false
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val amount = parameters.customParameters.getIntOrElse(0,1)
        val start = parameters.customParameters.getDoubleOrElse(1,0.0)
        myObstacleList.addAll( circle(pOffset = start,pDuration = -2.0, count = amount,helix = true))
        parameters.startTime+=2
        return super.getObstacleList(parameters)
    }
}

/** gets helix with Hyper walls */
object HyperHelix: WallStructure{
    override val name: String = "HyperHelix"
    override val mirror: Boolean = false
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val amount = parameters.customParameters.getIntOrElse(0,1)
        val start = parameters.customParameters.getDoubleOrElse(1,0.0)
        myObstacleList.addAll( circle(pOffset = start,pDuration = -4.0, count = amount,helix = true))
        //parameters.startTime+=2
        return super.getObstacleList(parameters)
    }
}

/** creates normal stairways */
object StairWay: WallStructure{
    override val name = "StairWay"
    override val mirror  = true
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val amount = parameters.customParameters.getIntOrElse(0,4)
        val min  = parameters.customParameters.getDoubleOrElse(1,0.0)
        val max = parameters.customParameters.getDoubleOrElse(2,4.0)
        for(i in 0 until amount){

            val height = abs(max-min)/amount
            val startHeight = if(min<=max)
                min + i* height
            else
                min - (i+1)*height

            myObstacleList.add( MyObstacle(1.0/amount,height,startHeight,4.0,0.5,i.toDouble()/amount))
        }
        return super.getObstacleList(parameters)
    }
}

object Spiral:WallStructure{
    override val name = "Spiral"
    override val mirror  = true
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        val max = 360
        val list = arrayListOf<_obstacles>()
        for (i in 0 until max){
            parameters.customParameters.clear()
            parameters.customParameters.add("$i")
            parameters.customParameters.add("2")
            parameters.startTime = i.toDouble() / max
            val l = (CyanLine.getObstacleList(parameters))
            list.addAll(l)
        }
        return list
    }
}

/** draws a line given a centerpoint, an angle and a radius */
object CyanLine: WallStructure{
    override val name = "CyanLine"
    override val mirror  = false
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val degree = parameters.customParameters.getDoubleOrElse(0,0.0)
        val length = parameters.customParameters.getDoubleOrElse(1,1.0)
        val cx = parameters.customParameters.getDoubleOrElse(2,0.0)
        val cy = parameters.customParameters.getDoubleOrElse(3,1.0)

        val dgr = degree / 360 * (2* PI)
        val defaultAmount = ((cos(dgr)*sin(dgr)).pow(2)*200 +1).toInt()

        val amount = parameters.customParameters.getIntOrElse(4, defaultAmount)

        val x1 = (cx + cos(dgr))*length
        val x2 = (cx - cos(dgr))*length
        val y1 = (cy + sin(dgr))*length
        val y2 = (cy - sin(dgr))*length

        myObstacleList.addAll(
            line(x1,x2,y1,y2,0.0,0.0,amount)
        )
        return adjustObstacles(parameters)
    }
}

/** Line */
object Line: WallStructure{
    override val name = "Line"
    override val mirror  = false
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()

        //all parameters
        val x1 = parameters.customParameters.getDoubleOrElse(0,-2.0)
        val x2 = parameters.customParameters.getDoubleOrElse(1,2.0)
        val y1  = parameters.customParameters.getDoubleOrElse(2,0.0)
        val y2 = parameters.customParameters.getDoubleOrElse(3,0.0)
        val z1 = parameters.customParameters.getDoubleOrElse(4,0.0)
        val z2 = parameters.customParameters.getDoubleOrElse(5,0.0)

        val amount = parameters.customParameters.getOrNull(6)?.toInt()
        val duration = parameters.customParameters.getOrNull(7)?.toDouble()

        myObstacleList.addAll(line(x1,x2,y1,y2,z1,z2,amount,duration))

        return adjustObstacles(parameters)
    }
}

/** mirroredLine */
object MirroredLine: WallStructure{
    override val name = "MirroredLine"
    override val mirror  = true
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()

        //all parameters
        val x1 = parameters.customParameters.getDoubleOrElse(0,-2.0)
        val x2 = parameters.customParameters.getDoubleOrElse(1,2.0)
        val y1  = parameters.customParameters.getDoubleOrElse(2,0.0)
        val y2 = parameters.customParameters.getDoubleOrElse(3,0.0)
        val z1 = parameters.customParameters.getDoubleOrElse(4,0.0)
        val z2 = parameters.customParameters.getDoubleOrElse(5,0.0)

        val amount = parameters.customParameters.getOrNull(6)?.toInt()
        val duration = parameters.customParameters.getOrNull(7)?.toDouble()

        myObstacleList.addAll(line(x1,x2,y1,y2,z1,z2,amount,duration))

        return adjustObstacles(parameters)
    }
}

/** gets very small noise in the area -4 .. 4 */
object RandomNoise: WallStructure{
    override val mirror = false
    override val name = "RandomNoise"
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val intensity = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 5 }
        repeat(intensity){
            val tempO = MyObstacle(
                duration = 0.01,
                height = 0.01,
                startHeight = Random.nextDouble(4.0),
                startRow = Random.nextDouble(-4.0,4.0),
                width = 0.01,
                startTime = Random.nextDouble()
            )
            myObstacleList.add(tempO)
        }
        return super.getObstacleList(parameters)
    }
}

/** gets very small noise in the area -30 .. 30 */
object BroadRandomNoise: WallStructure{
    override val mirror = false
    override val name = "BroadRandomNoise"
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val intensity = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 5 }
        repeat(intensity){
            val tempO = MyObstacle(
                duration = 0.01,
                height = 0.01,
                startHeight = Random.nextDouble(4.0),
                startRow = Random.nextDouble(-50.0,50.0),
                width = 0.01,
                startTime = Random.nextDouble()
            )
            myObstacleList.add(tempO)
        }
        return super.getObstacleList(parameters)
    }
}

/** random blocks to the right and left */
object RandomBlocks: WallStructure{
    override val mirror = false
    override val name = "RandomBlocks"
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val duration = parameters.customParameters.getDoubleOrElse(0,4.0)
        for(i in 0 until duration.toInt()){
            myObstacleList.add(MyObstacle(Random.nextDouble(0.5), Random.nextDouble(2.0), 0.0,Random.nextDouble(10.0, 20.0), Random.nextDouble(2.0),i.toDouble())    )
            myObstacleList.add(MyObstacle(Random.nextDouble(0.5), Random.nextDouble(2.0), 0.0,Random.nextDouble(-20.0, -10.0), Random.nextDouble(2.0),i.toDouble())    )
        }
        return adjustObstacles(parameters)
    }
}

/** random blocks to the right and left */
object RandomFastBlocks: WallStructure{
    override val mirror = false
    override val name = "RandomFastBlocks"
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val duration = parameters.customParameters.getDoubleOrElse(0,4.0)
        for(i in 0 until duration.toInt()){
            myObstacleList.add(MyObstacle(-2.0, Random.nextDouble(2.0), 0.0,Random.nextDouble(10.0, 20.0), Random.nextDouble(2.0),i.toDouble()+2)    )
            myObstacleList.add(MyObstacle(-2.0, Random.nextDouble(2.0), 0.0,Random.nextDouble(-20.0, -10.0), Random.nextDouble(2.0),i.toDouble()+2)    )
        }
        return adjustObstacles(parameters)
    }
}

/** gets randomLines, default on the floor */
object RandomLines: WallStructure{
    //todo TEST
    override val mirror: Boolean = false
    override val name: String = "randomLines"
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        //getting the variables or the default values
        val count = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 1 }
        val intensity = try { parameters.customParameters[1].toInt() } catch (e:Exception){ 4 }
        myObstacleList.clear()

        var x:Double
        for(i in 1..count){
            //adjusting the starting x, splitting it evenly among the count
            x = Random.nextDouble(-4.0 , 4.0)

            //for each wall intensity
            for(j in 1..intensity){
                myObstacleList.add(MyObstacle(1.0/intensity,0.05,0.0,x, 0.05,j.toDouble()/intensity))

                //randomly changes lines, adjusts x when doing so
                if (Random.nextInt(0, sqrt(count.toDouble()).roundToInt()) == 0){
                    val nX = Random.nextDouble(-4.0,4.0)
                    val stRow = if(nX > x) x else nX
                    val stWidth = nX-x
                    val stTime = j.toDouble()/intensity + 1.0/intensity
                    myObstacleList.add(MyObstacle(0.0005,0.05,0.0,stRow,stWidth,stTime))
                    x = nX
                }
            }
        }
        return super.getObstacleList(parameters)
    }
}

/** gets random side walls, default on the floor */
object RandomSideLines: WallStructure{
    //todo TEST
    override val mirror: Boolean = true
    override val name: String = "randomSideLines"
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        //getting the variables or the default values
        val count = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 1 }
        val intensity = try { parameters.customParameters[1].toInt() } catch (e:Exception){ 4 }
        myObstacleList.clear()

        var x:Double
        for(i in 1..count){
            //adjusting the starting x, splitting it evenly among the count
            x = Random.nextDouble(0.0 , 4.0)

            //for each wall intensity
            for(j in 1..intensity){
                myObstacleList.add(MyObstacle(1.0/intensity,0.05,x,4.0, 0.05,j.toDouble()/intensity))

                //randomly changes lines, adjusts x when doing so
                if (Random.nextInt(0, sqrt(count.toDouble()).roundToInt()) == 0){
                    val nX = Random.nextDouble(0.0,4.0)
                    val stHeight = if(nX > x) x else nX
                    val height = abs(nX-x)
                    val stTime = j.toDouble()/intensity + 1.0/intensity
                    myObstacleList.add(MyObstacle(0.0005,height,stHeight,4.0,0.05,stTime))
                    x = nX
                }
            }
        }
        return super.getObstacleList(parameters)
    }
}

/** gets a random box containing random lines floor, ceiling, side */
object RandomBox: WallStructure{
    override val mirror = false
    override val name = "RandomBox"
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        val duration = parameters.customParameters.getIntOrElse(0,4)
        val list = arrayListOf<_obstacles>()
        list.addAll(RandomLines.getObstacleList(Parameters("RandomLines -- 8 ${duration*2} -- $duration")))
        list.addAll(RandomLines.getObstacleList(Parameters("RandomLines -- 8 ${duration*2} -- $duration 0 0 0 0 4")))
        list.addAll(RandomSideLines.getObstacleList(Parameters("RandomSideLines -- 8 ${duration*2} -- $duration")))
        return list
    }
}

/** gets text */
object Text: WallStructure {
    override val name: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val mirror: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val myObstacleList: ArrayList<MyObstacle>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}

/** A function to get a circle of walls or a helix, probably should have splitted those up */
fun circle(count:Int = 1,radius:Double = 1.9, fineTuning:Int = 10,pOffset:Double = 0.0, pDuration:Double? = null, helix:Boolean = false):ArrayList<MyObstacle>{
    val list = arrayListOf<MyObstacle>()
    val max = 2.0* PI *fineTuning

    var x: Double
    var y: Double
    var nX:Double
    var nY:Double

    var width: Double
    var height: Double
    var startRow: Double
    var startHeight: Double

    var startTime:Double
    var duration:Double

    for(o in 0 until count){
        val offset = round((o*2.0* PI *fineTuning) /count) + pOffset
        for (i in 0..round(max).toInt()){
            x = radius * cos((i+offset)/fineTuning)
            y = radius * sin((i+offset)/fineTuning)

            nX = radius * cos(((i+offset)+1)/fineTuning)
            nY = radius * sin(((i+offset)+1)/fineTuning)

            startRow = x + (nX - x)
            width = abs(nX -x )
            startHeight = y
            startHeight+=2
            height = abs(nY-y)

            //sets the duration to, 1: the given duration, 2: if its a helix the duration to the next wall 3: the default for a circle: 0.005
            duration = pDuration?: if(helix) 1.0/max else pDuration?: 0.005
            startTime = if(helix) i/max else 0.0
            list.add(MyObstacle(duration,height,startHeight,startRow,width,startTime))
        }
    }
    return list
}

/** Draws a line between 2 coordinates */
fun line(px1:Double, px2: Double, py1:Double, py2: Double, pz1: Double, pz2: Double, defaultAmount: Int? = null, defaultDuration: Double? = null): ArrayList<MyObstacle>{

    //swap values if y2 < y1  - this functions goes from bottom to top
    var x1 = px1
    var x2 = px2
    var y1 = py1
    var y2 = py2
    var z1 = pz1
    var z2 = pz2

    val hyp = sqrt(abs(y2-y1).pow(2) + abs(x2-x1).pow(2)).coerceAtLeast(0.00001)
    val sin = abs(y2 -y1)/ hyp
    val cos = abs(x2-x1) / hyp

    val hyp2 = sqrt(abs(y2-y1).pow(2) + abs(z2-z1).pow(2)).coerceAtLeast(0.00001)
    val sin2 = abs(y2 -y1)/ hyp2
    val cos2 = abs(z2-z1) / hyp2

    val amount = defaultAmount?: ((sin * cos + sin2 * cos2).pow(2)* 100+1).toInt()
    val list = arrayListOf<MyObstacle>()

    if(y2<y1){
        x1 = x2.also { x2 = x1 }
        y1 = y2.also { y2 = y1 }
        z1 = z2.also { z2 = z1 }
    }

    //setting the solid values
    val w = (abs(x2-x1)/amount)
    val width = w.coerceAtLeast(0.01)
    val h = (abs(y2-y1)/amount)
    val height = h.coerceAtLeast(0.01)
    val d = (abs(z2-z1)/amount)
    val duration = d.coerceAtLeast(0.001)

    for(i in 0 until amount){
        //setting the dynamic values
        val startHeight = y1 + i* h
        val startRow =
            if(x2 > x1)
                x1 + i * w
            else
                x1 - (i+1) * w
        val startTime = z1 + i*d

        //adding the obstacle
        val myD = defaultDuration ?: duration
        list.add(MyObstacle(myD,height,startHeight,startRow,width,startTime))
    }
    return list
}

/** the default customwallstructure the asset file uses */
data class CustomWallStructure(

    @SerializedName("name")
    override val name: String,

    @SerializedName("mirror")
    override val mirror: Boolean,

    @SerializedName("myObstacle")
    override val myObstacleList: ArrayList<MyObstacle>

    ): WallStructure

data class AssetsBase (

    @SerializedName("customWallStructure") val customWallStructure : List<CustomWallStructure>
)

fun ArrayList<String>.getIntOrElse(index: Int, defaultValue: Int):Int =
    try { this[index].toInt() } catch (e:Exception){ defaultValue }

fun ArrayList<String>.getDoubleOrElse(index: Int, defaultValue: Double): Double =
    try { this[index].toDouble() } catch (e:Exception){ defaultValue }
