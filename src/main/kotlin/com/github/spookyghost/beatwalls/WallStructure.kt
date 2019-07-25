package com.github.spookyghost.beatwalls

import com.google.gson.annotations.SerializedName

interface WallStructure {
    val name: String

    val mirror: Boolean

    val myObstacle: ArrayList<MyObstacle>


    fun getObstacleList(parameters: ArrayList<Double>): ArrayList<_obstacles> {
        val list = arrayListOf<_obstacles>()
        myObstacle.forEach {
            val a = it.copy()
            a.adjust(parameters)
            list.add(a.to_obstacle())
        }
        return list
    }
}

data class Json4Kotlin_Base (

    @SerializedName("customWallStructure") val customWallStructure : List<CustomWallStructure>
)

data class CustomWallStructure(

    @SerializedName("name")
    override val name: String,

    @SerializedName("mirror")
    override val mirror: Boolean,

    @SerializedName("myObstacle")
    override val myObstacle: ArrayList<MyObstacle>

    ):WallStructure{
    /** Adds the mirrored obstacle, if mirrow is enabled*/
    init {
        if(mirror){
            myObstacle.forEach { myObstacle.add(it.mirror()) }
        }
    }
}


