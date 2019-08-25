package structures

import song._obstacles

object WallStructureManager
{
    private val wallStructuresList = arrayListOf<WallStructure>()

    fun loadManager(list:ArrayList<CustomWallStructure>) {
        with(wallStructuresList){
            add(Text)
            add(RandomLines)
            add(RandomNoise)
            add(Helix)
            add(FastHelix)
            add(EmptyHelix)
            addAll(list)
        }
    }


    fun get(parameters: Parameters): ArrayList<_obstacles> {

        //all the variables
        val list = arrayListOf<_obstacles>()
        val count = parameters.repeatCount
        val gap = parameters.repeatGap

        //gets repeat right
        for (i in 0..count) {
            parameters.startTime = parameters.startTime + gap * i

            //gets the structure with the given name, or just an empty arrayListOf<_obstacles>
            val structure = wallStructuresList.find {
                it.name.toLowerCase() == parameters.name.toLowerCase()
            }?.getObstacleList(parameters) ?: arrayListOf()

            list.addAll(structure)
        }

        return list
    }
}