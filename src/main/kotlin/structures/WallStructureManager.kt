package structures


import mu.KotlinLogging
import song._obstacles
private val logger = KotlinLogging.logger {}

object WallStructureManager
{
    private val wallStructuresList = arrayListOf<WallStructure>()

    fun loadManager(list:ArrayList<CustomWallStructure>) {
        with(wallStructuresList){
            add(Text)
            add(RandomLines)
            add(RandomNoise)
            add(BroadRandomNoise)
            add(RandomSideLines)
            add(RandomBlocks)
            add(RandomFastBlocks)
            add(Helix)
            add(ReverseHelix)
            add(MirroredHelix)
            add(FastHelix)
            add(HyperHelix)
            add(EmptyHelix)
            add(StairWay)
            add(Line)
            add(MirroredLine)
            add(CyanLine)
            addAll(list)
        }
    }

    fun getObstacleList(parameters: Parameters):ArrayList<_obstacles>{
        val list = getWallList(parameters).map { it.to_obstacle() }
        return ArrayList(list)
    }


    fun getWallList(parameters: Parameters): ArrayList<Wall> {

        //all the variables
        val list = arrayListOf<Wall>()
        val count = parameters.repeatCount
        val gap = parameters.repeatGap

        //gets repeat right
        for (i in 0..count) {

            //gets the structure with the given name, or just an empty arrayListOf<_obstacles>
            val structure = wallStructuresList.findLast {
                it.name.toLowerCase() == parameters.name.toLowerCase()
            }?:CustomWallStructure("",false, arrayListOf())
            val wallList= structure.getWallList(parameters)
            val adjustedList = wallList.map { it.adjustParameters(parameters) }.toMutableList()

            if (structure.mirror) {
                val mirroredList = adjustedList.map { it.mirror() }
                adjustedList += mirroredList
            }


            list.addAll(adjustedList)
            parameters.startTime += gap
        }
        logger.info { "added ${parameters.name}" }

        return list
    }
}