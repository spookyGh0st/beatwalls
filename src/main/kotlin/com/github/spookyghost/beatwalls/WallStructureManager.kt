package com.github.spookyghost.beatwalls

object WallStructureManager
{
    private val wallStructuresList = arrayListOf<WallStructure>()

    fun loadManager(list:ArrayList<CustomWallStructure>) {
        with(wallStructuresList){
            add(Text)
            addAll(list)
        }
    }

    fun get(parameters: ArrayList<String>): ArrayList<_obstacles> {
        return wallStructuresList.find {
            it.name.toLowerCase() == parameters.first().toLowerCase()
        }?.getObstacleList(parameters) ?: arrayListOf()
    }
}