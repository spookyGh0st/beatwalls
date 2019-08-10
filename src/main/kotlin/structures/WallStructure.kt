package structures

import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import song._obstacles
private val logger = KotlinLogging.logger {}

interface WallStructure {
    val name: String

    val mirror: Boolean

    val myObstacle: ArrayList<MyObstacle>

    fun getObstacleList(parameters: Parameters): ArrayList<_obstacles> {
        return adjustObstacles(parameters)
    }
    fun adjustObstacles(parameters: Parameters):ArrayList<_obstacles>{
        val list = arrayListOf<_obstacles>()
        myObstacle.forEach {
                val a = it.adjustParameters(parameters)
                list.add(a.to_obstacle())
                if(mirror) list.add(a.mirror().to_obstacle())
        }
        logger.info { "added ${parameters.name}" }
        return list
    }
}




object Text: WallStructure {
    override val name: String = "text"
    override val mirror: Boolean = false
    override val myObstacle: ArrayList<MyObstacle> = arrayListOf()
}

data class AssetsBase (

    @SerializedName("customWallStructure") val customWallStructure : List<CustomWallStructure>
)

data class CustomWallStructure(

    @SerializedName("name")
    override val name: String,

    @SerializedName("mirror")
    override val mirror: Boolean,

    @SerializedName("myObstacle")
    override val myObstacle: ArrayList<MyObstacle>

    ): WallStructure


