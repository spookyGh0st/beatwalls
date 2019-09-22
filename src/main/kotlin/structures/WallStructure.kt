package structures

import com.google.gson.annotations.SerializedName
import kotlin.math.*
import kotlin.random.Random


sealed class WallStructure  {
    abstract val name: String

    abstract val mirror: Boolean

    abstract val wallList: ArrayList<Wall>

    abstract fun getWallList(parameters: Parameters): ArrayList<Wall>
}

/** gets helix with fixed duration */
object Helix: WallStructure(){
    override val name: String = "helix"
    override val mirror: Boolean = false
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()
        val count = parameters.customParameters.getIntOrElse(0,1)
        val radius = parameters.customParameters.getDoubleOrElse(1,2.0)
        val fineTuning = parameters.customParameters.getIntOrElse(2,10)
        val startRotation = parameters.customParameters.getDoubleOrElse(3,0.0)
        val rotationCount = parameters.customParameters.getDoubleOrElse(4,1.0)
        val reverse = parameters.customParameters.getBooleanOrElse(5,false)
        val heightOffset = parameters.customParameters.getDoubleOrElse(6,2.0)
        val speedChange = parameters.customParameters.getOrNull(7)?.toDouble()
        val wallDuration = parameters.customParameters.getOrNull(8)?.toDouble()
        wallList.addAll( circle(
            count = count,
            radius = radius,
            fineTuning = fineTuning,
            startRotation = startRotation,
            rotationCount = rotationCount,
            heightOffset = heightOffset,
            speedChange = speedChange,
            wallDuration = wallDuration,
            helix = true,
            reverse = reverse
        ))
        return wallList
    }
}

/** gets CeilingHelix with fixed duration */
object CeilingHelix: WallStructure(){
    override val name: String = "ceilinghelix"
    override val mirror: Boolean = false
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()
        val count = 1
        val radius = 5.0
        val startRotation = 0.0
        val rotationCount = 0.5
        val heightOffset = 0.0
        val fineTuning = parameters.customParameters.getIntOrElse(0,10)
        val reverse = parameters.customParameters.getBooleanOrElse(1,false)
        val speedChange = parameters.customParameters.getOrNull(2)?.toDouble()
        val wallDuration = parameters.customParameters.getOrNull(3)?.toDouble()
        wallList.addAll( circle(
            count = count,
            radius = radius,
            fineTuning = fineTuning,
            startRotation = startRotation,
            rotationCount = rotationCount,
            heightOffset = heightOffset,
            speedChange = speedChange,
            wallDuration = wallDuration,
            helix = true,
            reverse = reverse
        ))
        return wallList
    }
}

/** creates normal stairways */
object StairWay: WallStructure() {
    override val name = "StairWay"
    override val mirror  = true
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val list = arrayListOf<Wall>()
        val amount = parameters.customParameters.getIntOrElse(0,4)
        val min  = parameters.customParameters.getDoubleOrElse(1,0.0)
        val max = parameters.customParameters.getDoubleOrElse(2,4.0)
        for(i in 0 until amount){

            val height = abs(max-min)/amount
            val startHeight = if(min<=max)
                min + i* height
            else
                min - (i+1)*height

            list.add( Wall(4.0, 1.0/amount, 0.5, height, startHeight, i.toDouble()/amount))
        }
        return list
    }
}

/** draws a line given a centerPoint, an angle and a radius */
object CyanLine: WallStructure() {
    override val name = "CyanLine"
    override val mirror  = false
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()
        val degree = parameters.customParameters.getDoubleOrElse(0,0.0)
        val length = parameters.customParameters.getDoubleOrElse(1,1.0)
        val cx = parameters.customParameters.getDoubleOrElse(2,0.0)
        val cy = parameters.customParameters.getDoubleOrElse(3,2.0)

        val dgr = degree / 360 * (2* PI)
        val defaultAmount = ((cos(dgr)*sin(dgr)).pow(2)*200 +1).toInt()

        val amount = parameters.customParameters.getIntOrElse(4, defaultAmount)

        val x1 = (cx + cos(dgr))*length
        val x2 = (cx - cos(dgr))*length
        val y1 = (cy + sin(dgr))*length
        val y2 = (cy - sin(dgr))*length

        wallList.addAll(
            line(x1,x2,y1,y2,0.0,0.0,amount)
        )
        return wallList
    }
}

/** Line */
object Line: WallStructure() {
    override val name = "Line"
    override val mirror  = false
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()

        //all parameters
        val x1 = parameters.customParameters.getDoubleOrElse(0,-2.0)
        val x2 = parameters.customParameters.getDoubleOrElse(1,2.0)
        val y1  = parameters.customParameters.getDoubleOrElse(2,0.0)
        val y2 = parameters.customParameters.getDoubleOrElse(3,0.0)
        val z1 = parameters.customParameters.getDoubleOrElse(4,0.0)
        val z2 = parameters.customParameters.getDoubleOrElse(5,1.0)

        val amount = parameters.customParameters.getOrNull(6)?.toInt()
        val duration = parameters.customParameters.getOrNull(7)?.toDouble()

        wallList.addAll(line(x1,x2,y1,y2,z1,z2,amount,duration))

        return wallList
    }
}

/** mirroredLine */
object MirroredLine: WallStructure() {
    override val name = "MirroredLine"
    override val mirror  = true
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()

        //all parameters
        val x1 = parameters.customParameters.getDoubleOrElse(0,-2.0)
        val x2 = parameters.customParameters.getDoubleOrElse(1,2.0)
        val y1  = parameters.customParameters.getDoubleOrElse(2,0.0)
        val y2 = parameters.customParameters.getDoubleOrElse(3,0.0)
        val z1 = parameters.customParameters.getDoubleOrElse(4,0.0)
        val z2 = parameters.customParameters.getDoubleOrElse(5,0.0)

        val amount = parameters.customParameters.getOrNull(6)?.toInt()
        val duration = parameters.customParameters.getOrNull(7)?.toDouble()

        wallList.addAll(line(x1,x2,y1,y2,z1,z2,amount,duration))

        return wallList
    }
}

/** gets very small noise in the area -4 .. 4 */
object RandomNoise: WallStructure() {
    override val mirror = false
    override val name = "RandomNoise"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()
        val intensity = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 5 }
        repeat(intensity){
            val tempO = Wall(
                startRow = Random.nextDouble(-4.0,4.0),
                duration = 0.01,
                width = 0.01,
                height = 0.01,
                startHeight = Random.nextDouble(4.0),
                startTime = Random.nextDouble()
            )
            wallList.add(tempO)
        }
        return wallList
    }
}

/** gets very small noise in the area -30 .. 30 */
object BroadRandomNoise: WallStructure() {
    override val mirror = false
    override val name = "BroadRandomNoise"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()
        val intensity = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 5 }
        repeat(intensity){
            val tempO = Wall(
                startRow = Random.nextDouble(-50.0,50.0),
                duration = 0.01,
                width = 0.01,
                height = 0.01,
                startHeight = Random.nextDouble(4.0),
                startTime = Random.nextDouble()
            )
            wallList.add(tempO)
        }
        return wallList
    }
}

/** random blocks to the right and left */
object RandomBlocks: WallStructure() {
    override val mirror = false
    override val name = "RandomBlocks"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()
        val duration = parameters.customParameters.getDoubleOrElse(0,4.0)
        for(i in 0 until duration.toInt()){
            wallList.add(Wall(
                Random.nextDouble(10.0, 20.0),
                Random.nextDouble(0.5),
                Random.nextDouble(2.0),
                Random.nextDouble(2.0),
                0.0,
                i.toDouble()
            )    )
            wallList.add(Wall(
                Random.nextDouble(-20.0, -10.0),
                Random.nextDouble(0.5),
                Random.nextDouble(2.0),
                Random.nextDouble(2.0),
                0.0,
                i.toDouble()
            )    )
        }
        return wallList
    }
}

/** random blocks to the right and left */
object RandomFastBlocks: WallStructure() {
    override val mirror = false
    override val name = "RandomFastBlocks"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        wallList.clear()
        val duration = parameters.customParameters.getDoubleOrElse(0,4.0)
        for(i in 0 until duration.toInt()){
            wallList.add(Wall(
                Random.nextDouble(10.0, 20.0),
                -2.0,
                Random.nextDouble(2.0),
                Random.nextDouble(2.0),
                0.0,
                i.toDouble()+2
            )    )
            wallList.add(Wall(
                Random.nextDouble(-20.0, -10.0),
                -2.0,
                Random.nextDouble(2.0),
                Random.nextDouble(2.0),
                0.0,
                i.toDouble()+2
            )    )
        }
        return wallList
    }
}

/** gets randomLines, default on the floor */
object RandomLines: WallStructure() {
    //todo TEST
    override val mirror: Boolean = false
    override val name: String = "randomLines"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        //getting the variables or the default values
        val count = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 1 }
        val intensity = try { parameters.customParameters[1].toInt() } catch (e:Exception){ 4 }
        wallList.clear()

        var x:Double
        for(i in 1..count){
            //adjusting the starting x, splitting it evenly among the count
            x = Random.nextDouble(-4.0 , 4.0)

            //for each wall intensity
            for(j in 1..intensity){
                wallList.add(Wall(x, 1.0/intensity, 0.05, 0.05, 0.0, j.toDouble()/intensity))

                //randomly changes lines, adjusts x when doing so
                if (Random.nextInt(0, sqrt(count.toDouble()).roundToInt()) == 0){
                    val nX = Random.nextDouble(-4.0,4.0)
                    val stRow = if(nX > x) x else nX
                    val stWidth = nX-x
                    val stTime = j.toDouble()/intensity + 1.0/intensity
                    wallList.add(Wall(stRow, 0.0005, stWidth, 0.05, 0.0, stTime))
                    x = nX
                }
            }
        }
        return wallList
    }
}

/** gets random side walls, default on the floor */
object RandomSideLines: WallStructure() {
    //todo TEST
    override val mirror: Boolean = true
    override val name: String = "randomSideLines"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        //getting the variables or the default values
        val count = try { parameters.customParameters[0].toInt() } catch (e:Exception){ 1 }
        val intensity = try { parameters.customParameters[1].toInt() } catch (e:Exception){ 4 }
        wallList.clear()

        var x:Double
        for(i in 1..count){
            //adjusting the starting x, splitting it evenly among the count
            x = Random.nextDouble(0.0 , 4.0)

            //for each wall intensity
            for(j in 1..intensity){
                wallList.add(Wall(4.0, 1.0/intensity, 0.05, 0.05, x, j.toDouble()/intensity))

                //randomly changes lines, adjusts x when doing so
                if (Random.nextInt(0, sqrt(count.toDouble()).roundToInt()) == 0){
                    val nX = Random.nextDouble(0.0,4.0)
                    val stHeight = if(nX > x) x else nX
                    val height = abs(nX-x)
                    val stTime = j.toDouble()/intensity + 1.0/intensity
                    wallList.add(Wall(4.0, 0.0005, 0.05, height, stHeight, stTime))
                    x = nX
                }
            }
        }
        return wallList
    }
}

/** fucks the Wall up */
object FuckUp: WallStructure(){
    override var mirror: Boolean = false
    override val name: String = "FuckUp"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val amount = parameters.customParameters.getIntOrElse(0, 2)
        val tempParameters = Parameters(name = "Splitter",customParameters = arrayListOf("$amount"), innerParameter = parameters.innerParameter)
        val list = WallStructureManager.getWallList(tempParameters)
        return ArrayList(list.map{ it.fuckUp() })
    }
}

/** fucks the Wall up */
object Grounder: WallStructure(){
    override var mirror: Boolean = false
    override val name: String = "Grounder"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val startHeight = parameters.customParameters.getDoubleOrElse(0, 0.0)
        val list = WallStructureManager.getWallList(parameters.innerParameter?: Parameters())
        return ArrayList(list.map{ it.ground(startHeight) })
    }
}
/** Extender Object - when called, extends all walls to a certain beat */
object Extender: WallStructure() {
    override val name = "Extender"
    override val mirror = false
    override val wallList = arrayListOf<Wall>()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val list = WallStructureManager.getWallList(parameters.innerParameter?: Parameters())
        val final = parameters.customParameters.getDoubleOrElse(0,1.0)
        return ArrayList(list.map{ it.extend(final) })
    }
}

/** changeDuration Object - when called, makes all the walls hyper Walls */
object ChangeDuration: WallStructure() {
    override val name = "changeDuration"
    override val mirror = false
    override val wallList = arrayListOf<Wall>()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val list = WallStructureManager.getWallList(parameters.innerParameter?: Parameters())
        val d = parameters.customParameters.getDoubleOrElse(0,-3.0)
        list.forEach { it.duration=d }
        return list
    }
}
/** fucks the Wall up */
object SkyFitter: WallStructure(){
    override var mirror: Boolean = false
    override val name: String = "SkyFitter"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val endHeight = parameters.customParameters.getDoubleOrElse(0, 20.0)
        val list = WallStructureManager.getWallList(parameters.innerParameter?: Parameters())
        return ArrayList(list.map{ it.sky(endHeight) })
    }
}

/** splits the wall into multiple small one */
object Splitter: WallStructure() {
    override var mirror: Boolean = false
    override val name: String = "splitter"
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val list = WallStructureManager.getWallList(parameters.innerParameter?: Parameters())
        val amount = parameters.customParameters.getIntOrElse(0,2)

        val tempList = arrayListOf<Wall>()
        for (wall in list){
            val pTempList= arrayListOf(wall)
            repeat(amount){

                val addList = arrayListOf<Wall>()
                val removeList = arrayListOf<Wall>()

                for(tempWall in pTempList){
                    addList.addAll(tempWall.split())
                    removeList.add(tempWall)
                }

                pTempList+=addList
                pTempList-=removeList
            }
            tempList.addAll(pTempList)
        }
        list.addAll(tempList)
        return list
    }
}

/** gets text */
object Text: WallStructure() {
    override val name: String = "Text"
    override val mirror: Boolean = false
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val list = arrayListOf<Wall>()
        val text = parameters.customParameters.getOrNull(0)?:""
        val gap = parameters.customParameters.getDoubleOrElse(1,2.5)
        val midX = parameters.customParameters.getDoubleOrElse(2,0.0)
        var x=  midX-(text.length-1) * gap / 2 - gap/2
        for(c in text){
            val tempList =WallStructureManager.getWallList(Parameters(name = c.toString()))
            tempList.forEach { it.startRow += x }
            x+=gap
            list.addAll(tempList)
        }
        return list
    }
}

/** LaneShifter Object - when called, creates an array Lines between the 4 given Points */
object LineShifter: WallStructure() {
    override val name = "LineShifter"
    override val mirror = false
    override val wallList = arrayListOf<Wall>()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val list = arrayListOf<Wall>()
        val amount = parameters.customParameters.getIntOrElse(0,4)
        val p1x1 =parameters.customParameters.getDoubleOrElse(1,-4.0)
        val p1y1 =parameters.customParameters.getDoubleOrElse(2,-4.0)
        val p1x2 =parameters.customParameters.getDoubleOrElse(3,-4.0)
        val p1y2 =parameters.customParameters.getDoubleOrElse(4,-4.0)
        val p2x1 =parameters.customParameters.getDoubleOrElse(5,-4.0)
        val p2y1 =parameters.customParameters.getDoubleOrElse(6,-4.0)
        val p2x2 =parameters.customParameters.getDoubleOrElse(7,-4.0)
        val p2y2 =parameters.customParameters.getDoubleOrElse(8,-4.0)
        var tempx1 = p1x1
        var tempx2 = p1x2
        var tempy1 =p1y1
        var tempy2 = p1y2

        for (i in 0 until amount){
            list.addAll(line(tempx1,tempx2,tempy1,tempy2,i.toDouble()/amount,i.toDouble()/amount,null,1.0/amount))
            tempx1 += (p2x1-p1x1)/amount
            tempx2 += (p2x2-p1x2)/amount
            tempy1 += (p2y1-p1y1)/amount
            tempy2 += (p2y2-p1y2)/amount
        }
        return list
    }
}


object SideWave: WallStructure(){
    override val name = "SideWave"
    override val mirror = true
    override val wallList: ArrayList<Wall> = arrayListOf()
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        val list = arrayListOf<Wall>()
        val max = parameters.customParameters.getIntOrElse(0,8)
        for(i in 0 until (max)){
            val y = i/max*(2* PI)
            val nY = (i+1)/max*(2* PI)

            list.add(
                Wall(
                    duration =  1 / max.toDouble(),
                    height = abs(cos(nY)- cos(y)),
                    startHeight = 1-cos(y),
                    startRow = 3.0,
                    width = 0.5,
                    startTime = i/max.toDouble()
                )
            )
        }
        return list
    }
}

/** the default customWallStructure the asset file uses */
data class CustomWallStructure(

    @SerializedName("name")
    override val name: String,

    @SerializedName("mirror")
    override val mirror: Boolean,

    @SerializedName("WallList")
    override val wallList: ArrayList<Wall>

): WallStructure() {
    override fun getWallList(parameters: Parameters): ArrayList<Wall> {
        return ArrayList(wallList.map { it.copy() })
    }

    override fun toString(): String {
        var text="\n\tCustomWallStructure(\n"
        text+="\t\t\"$name\",\n"
        text+="\t\t$mirror,\n"
        text+="\t\tarrayListOf("
        for (wall in wallList){
            text+="\n\t\t$wall,"
        }
        text = text.removeSuffix(",")
        text+="\n\t))"
        return text
    }
}
