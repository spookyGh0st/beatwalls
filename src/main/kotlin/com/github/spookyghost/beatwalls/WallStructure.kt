package com.github.spookyghost.beatwalls

import com.google.gson.annotations.SerializedName

interface WallStructure {
    val name: String

    val mirror: Boolean

    val myObstacle: ArrayList<MyObstacle>

    fun getObstacleList(pString: ArrayList<String>): ArrayList<_obstacles> {
        val parameters = getDefaultParameter(pString)
        val list = arrayListOf<_obstacles>()
        myObstacle.forEach {
            val a = it.copy()
            a.adjust(parameters)
            if(mirror) list.add(a.mirror().to_obstacle())
            list.add(a.to_obstacle())
        }
        return list
    }
}




object Text:WallStructure{
    override val name: String = "text"
    override val mirror: Boolean = false
    override val myObstacle: ArrayList<MyObstacle> = arrayListOf()

    override fun getObstacleList(pString: ArrayList<String>): ArrayList<_obstacles> {
        myObstacle.clear()
        val list = arrayListOf<_obstacles>()
        myObstacle.forEach { list.add(it.to_obstacle()) }
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
}


private fun getDefaultParameter(parameters: ArrayList<String>): ArrayList<Double> {
    val doubleArrayList =   arrayListOf<Double>()
    (parameters.subList(1, parameters.lastIndex+1)).forEach {
        doubleArrayList.add(it.toDoubleOrNull() ?: 0.0)
    }
    while(doubleArrayList.size<6) { doubleArrayList.add(0.0) }
    if(doubleArrayList.size < 7) doubleArrayList.add((1.0))
    return doubleArrayList
}
