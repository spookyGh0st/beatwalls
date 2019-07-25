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

    fun get(parameters: ArrayList<String>) =
        wallStructuresList.find {
            it.name == parameters.first().toLowerCase()
        }?.getObstacleList(parameters) ?: arrayListOf()


    /** The default parameter are duration - height - startHeight - startRow - width */
}