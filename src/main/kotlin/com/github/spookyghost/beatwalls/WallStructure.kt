package com.github.spookyghost.beatwalls

interface WallStructure {
    val name: String

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

    override val name: String,
    val mirror: Boolean,
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
    override val myObstacleList: ArrayList<MyObstacle> = arrayListOf()

    override fun getObstacleList(parameters: ArrayList<Double>): ArrayList<_obstacles> {
        TODO()
    }
    override val name: String = "Floor"
}
