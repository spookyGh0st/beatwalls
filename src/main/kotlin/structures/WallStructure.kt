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
        val z2 = parameters.customParameters.getDoubleOrElse(5,0.0)

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


/** A function to getWallList a circle of walls or a helix, probably should have splitted those up */
fun circle(
    count:Int = 1, //how many spirals
    radius:Double = 1.9, //how big
    fineTuning:Int = 10, //how many walls
    startRotation:Double = 0.0, //startRotation offset
    rotationCount:Double = 1.0, //how many rotations
    heightOffset:Double = 2.0, //height of the center
    speedChange: Double? = null, //speedChange, speed up or slowDown
    wallDuration:Double? = null, //the default duration
    helix:Boolean = false, //if its a helix or a circle
    reverse:Boolean = false //if its reversed
):ArrayList<Wall>{
    val list = arrayListOf<Wall>()
    val max = 2.0* PI *fineTuning*rotationCount

    var x: Double
    var y: Double
    var nX:Double
    var nY:Double

    var width: Double
    var height: Double
    var startRow: Double
    var startHeight: Double

    var startTime: Double
    var duration:Double

    for(o in 0 .. count){
        //the offset controls the starting point
        val offset = round((o*2.0* PI *fineTuning) /count) + startRotation/360*(2* PI)
        var lastStartTime = 0.0
        for (j in 0 until round(max).toInt()){
            val i = if(!reverse) j else (max-j).toInt()
            x = radius * cos((i+offset)/fineTuning)
            y = radius * sin((i+offset)/fineTuning)

            nX = radius * cos(((i+offset)+1)/fineTuning)
            nY = radius * sin(((i+offset)+1)/fineTuning)

            startRow = x + (nX - x)
            width = abs(nX -x ).coerceAtLeast(0.001)
            startHeight = y + heightOffset
            height = abs(nY-y).coerceAtLeast(0.001)

            //sets the duration to, 1: the given duration, 2: if its a helix the duration to the next wall 3: the defaultDuration: 1.0

            duration = wallDuration?: if(helix){
                if (speedChange==null){
                    1.0/max
                }else{
                    ((j+1)/max).pow(1.0/speedChange) - ((j)/max).pow(1.0/speedChange)
                }
            }else{
                1.0
            }
            val tempDuration =
                if (speedChange==null){
                    1.0/max
                }else{
                    ((j+1)/max).pow(1.0/speedChange) - ((j)/max).pow(1.0/speedChange)
                }

            //changes the startTime, and then saves it to lastStartTime
            startTime = if(helix) lastStartTime+tempDuration else 0.0
            lastStartTime = startTime

            //adds the Obstacle
            list.add(Wall(startRow, duration, width, height, startHeight, startTime))
        }
    }
    return list
}

/** Draws a line between 2 coordinates */
fun line(px1:Double, px2: Double, py1:Double, py2: Double, pz1: Double, pz2: Double, defaultAmount: Int? = null, defaultDuration: Double? = null): ArrayList<Wall>{

    //swap values if y2 < y1  - this functions goes from bottom to top
    var x1 = px1
    var x2 = px2
    var y1 = py1
    var y2 = py2
    var z1 = pz1
    var z2 = pz2

    val hyp = sqrt(abs(y2-y1).pow(2) + abs(x2-x1).pow(2))
    val sin = abs(y2 -y1)/ hyp
    val cos = abs(x2-x1) / hyp

    val hyp2 = sqrt(abs(y2-y1).pow(2) + abs(z2-z1).pow(2))
    val sin2 = abs(y2 -y1)/ hyp2
    val cos2 = abs(z2-z1) / hyp2

    val amount = defaultAmount?: ((sin * cos + sin2 * cos2).pow(2)* 100+1).toInt()
    val list = arrayListOf<Wall>()

    if(z2<z1){
        x1 = x2.also { x2 = x1 }
        y1 = y2.also { y2 = y1 }
        z1 = z2.also { z2 = z1 }
    }

    //setting the solid values
    val w = (abs(x2-x1)/amount)
    val width = w
    val h = (abs(y2-y1)/amount)
    val height = h
    val d = (abs(z2-z1)/amount)
    val duration = d

    for(i in 0 until amount){
        //setting the dynamic values
        val startHeight =
            if(y2 > y1)
                y1 + i* h
            else
                y1 - (i+1) * h
        val startRow =
            if(x2 > x1)
                x1 + i * w
            else
                x1 - (i+1) * w
        val startTime = z1 + i*d

        //adding the obstacle
        val myD = defaultDuration ?: duration
        list.add(Wall(startRow, myD, width, height, startHeight, startTime))
    }
    return list
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

fun ArrayList<String>.getIntOrElse(index: Int, defaultValue: Int):Int =
    try { this[index].toInt() } catch (e:Exception){ defaultValue }

fun ArrayList<String>.getDoubleOrElse(index: Int, defaultValue: Double): Double =
    try { this[index].toDouble() } catch (e:Exception){ defaultValue }

fun ArrayList<String>.getBooleanOrElse(index: Int, defaultValue: Boolean): Boolean =
    try {
        this[index].toInt() == 1
    } catch (e:Exception){ defaultValue }

