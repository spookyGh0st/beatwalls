package com.github.spookyghost.beatwalls

object WallStructureManager
{
    private val wallStructuresList = arrayListOf<WallStructure>()

    fun loadManager(list:ArrayList<CustomWallStructure>) {
        with(wallStructuresList){
            addAll(list)
        }
    }

    fun get(parameters: ArrayList<String>) =
        wallStructuresList.find {
            it.name == parameters.first().toLowerCase()
        }?.getObstacleList(getDefaultParameter(parameters)) ?: arrayListOf()


    /** The default parameter are duration - height - startHeight - startRow - width */
    private fun getDefaultParameter(parameters: ArrayList<String>): ArrayList<Double> {
        val doubleArrayList =   arrayListOf<Double>()
        (parameters.subList(1, parameters.lastIndex+1)).forEach {
            doubleArrayList.add(it.toDoubleOrNull() ?: 0.0)
        }
        while(doubleArrayList.size<5) { doubleArrayList.add(0.0) }
        println(doubleArrayList)
        return doubleArrayList
    }
}