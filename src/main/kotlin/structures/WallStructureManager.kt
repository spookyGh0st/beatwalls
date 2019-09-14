package structures


import mu.KotlinLogging
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
            }   ?: return list
            val wallList= structure.getWallList(parameters)


            //adjusts the parameter
            wallList.forEach { it.adjustParameters(parameters) }

            //adds the mirrored list
            if (structure.mirror)
                wallList.addAll(wallList.map { it.mirror() })

            //adds the temp list to our List
            list.addAll(wallList)
            parameters.startTime += gap
        }
        logger.info { "added ${parameters.name}" }
        return list
    }
}