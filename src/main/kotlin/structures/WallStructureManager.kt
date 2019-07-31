package structures

import song._obstacles

object WallStructureManager
{
    private val wallStructuresList = arrayListOf<WallStructure>()

    fun loadManager(list:ArrayList<CustomWallStructure>) {
        with(wallStructuresList){
            add(Text)
            addAll(list)
        }
    }


    fun get(parameters: Parameters): ArrayList<_obstacles> {
        return wallStructuresList.find {
            it.name.toLowerCase() == parameters.name.toLowerCase()
        }?.getObstacleList(parameters) ?: arrayListOf()
    }
}