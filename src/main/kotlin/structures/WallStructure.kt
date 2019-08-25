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
        val start = parameters.customParameters.getDoubleOrElse(1,0.0)
        myObstacleList.addAll( circle(pOffset = start, count = amount,helix = true))
        return super.getObstacleList(parameters)
    }
}

/** gets helix with walls with the duration 0*/
object EmptyHelix: WallStructure{
    override val name: String = "helix"
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
        myObstacleList.forEach { it.startTime += 2 }
        return super.getObstacleList(parameters)
    }
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
            startHeight = if(y>=0) y else nY
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

/** gets very small noise in the area -3 .. 3 */
object RandomNoise: WallStructure{
    override val mirror = false
    override val name = "RandomNoise"
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
    override fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        myObstacleList.clear()
        val intensity = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 5 }
        repeat(intensity){
            val tempO = MyObstacle(
                duration = 0.001,
                height = 0.001,
                startHeight = Random.nextDouble(4.0),
                startRow = Random.nextDouble(-4.0,4.0),
                width = 0.0001,
                startTime = Random.nextDouble()
            )
            myObstacleList.add(tempO)
        }
        return super.getObstacleList(parameters)
    }
}

/** gets randomLines, default on the floor */ //todo create the same for floor lines
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
            x = ((count/(i+1.0))-0.5)*4.0

            //for each wall intensity
            for(j in 1..intensity){
                myObstacleList.add(MyObstacle(1.0/intensity,0.05,0.0,x, 0.0,j.toDouble()/intensity))

                //randomly changes lines, adjusts x when doing so
                if (Random.nextInt(0,count) == 0){
                    val nX = Random.nextDouble(-2.0,2.0)
                    val stRow = if(nX>x) x else nX
                    val stWidth = abs(nX-x)
                    val stTime = j/200.0 + 1.0/j
                    myObstacleList.add(MyObstacle(0.0,0.05,0.0,stRow,stWidth,stTime))
                    x = nX
                }
            }
        }
        return super.getObstacleList(parameters)
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
