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
        println("LIST: $list")
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
        val text = pString.subList(1,pString.lastIndex)
        println(text)
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
    while(doubleArrayList.size<5) { doubleArrayList.add(0.0) }
    println(doubleArrayList)
    return doubleArrayList
}
