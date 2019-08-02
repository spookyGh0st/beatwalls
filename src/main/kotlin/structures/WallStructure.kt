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
            do{
                val a = it.adjustParameters(parameters)
                list.add(a.to_obstacle())
                if(mirror) list.add(a.mirror().to_obstacle())
                parameters.startTime += parameters.repeatGap
                parameters.repeatCount --
            }while(parameters.repeatCount>=0)
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


private fun getDefaultParameter(parameters: ArrayList<String>): ArrayList<Double> {
    val doubleArrayList =   arrayListOf<Double>()
    (parameters.subList(1, parameters.lastIndex+1)).forEach {
        doubleArrayList.add(it.toDoubleOrNull() ?: 0.0)
    }
    if(doubleArrayList.size<1) doubleArrayList.add(1.0)
    while(doubleArrayList.size<8) { doubleArrayList.add(0.0) }
    return doubleArrayList
}
