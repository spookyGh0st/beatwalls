package structures

import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import song._obstacles
import java.lang.Exception
import kotlin.math.abs
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



object RandomLines: WallStructure{
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
                myObstacleList.add(MyObstacle(1.0/j,0.05,0.0,x, 0.0,j.toDouble()/intensity))

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

object Text: WallStructure {
    override val name: String = "text"
    override val mirror: Boolean = false
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()
}

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

