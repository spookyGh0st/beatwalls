package com.github.spookyghost.beatwalls

import com.google.gson.annotations.SerializedName

interface WallStructure {
    val name: String

    val mirror: Boolean

    val myObstacleList: ArrayList<MyObstacle>


    fun getObstacleList(parameters: ArrayList<Double>): ArrayList<_obstacles> {

        val list = arrayListOf<_obstacles>()

        myObstacleList.forEach {
            it.adjust(parameters)
            list.add(it.to_obstacle())
        }
        return list
    }
}

data class CustomWallStructure(

    @SerializedName("name")
    override val name: String,

    @SerializedName("mirror")
    override val mirror: Boolean,

    @SerializedName("myObstacle")
    override val myObstacleList: ArrayList<MyObstacle>

    ):WallStructure{
    /** Adds the mirrored obstacle, if mirrow is enabled*/
    init {
        if(mirror){
            myObstacleList.forEach { myObstacleList.add(it.mirror()) }
        }
    }
}


object Floor: WallStructure{
    override val mirror: Boolean = false

    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()

    override fun getObstacleList(parameters: ArrayList<Double>): ArrayList<_obstacles> {
        TODO()
    }
    override val name: String = "Floor"
}


