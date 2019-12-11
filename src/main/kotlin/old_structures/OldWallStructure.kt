package old_structures

import structure.*
import kotlin.math.*
import kotlin.random.Random


sealed class OldWallStructure  {
    abstract val name: String

    abstract val mirror: Boolean

    abstract val spookyWallList: ArrayList<SpookyWall>

    abstract fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall>
}


/** RandomBox Object - when called, creates a random box with the given amount per tick and the given ticks per beat */
object RandomBox: OldWallStructure() {
    override val name = "RandomBox"
    override val mirror = false
    override val spookyWallList = arrayListOf<SpookyWall>()
    override fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall> {
        val list = arrayListOf<SpookyWall>()
        val amountPerTick = oldParameters.customParameters.getIntOrElse(0,4)
        val amountOfTicks = oldParameters.customParameters.getIntOrElse(1,4)
        val wallAmountPerWall = oldParameters.customParameters.getIntOrElse(2,8)

        val allSpookyWalls: ArrayList<SpookyWall> = getBoxList(wallAmountPerWall)

        for(start in 0 until amountOfTicks){
            val tempList = ArrayList(allSpookyWalls.map { it.copy() })
            repeat(amountPerTick){
                val w = tempList.random()
                w.startTime = start.toDouble()/amountOfTicks
                list.add(w)
                tempList.remove(w)
            }
        }
        return list
    }
}/** RandomBoxLine Object - when called, creates a random box with the given amount per tick and the given ticks per beat */
object RandomBoxLine: OldWallStructure() {
    override val name = "RandomBoxLine"
    override val mirror = false
    override val spookyWallList = arrayListOf<SpookyWall>()
    override fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall> {
        val list = arrayListOf<SpookyWall>()
        val amountPerTick = oldParameters.customParameters.getIntOrElse(0,8)
        val amountOfTicks = oldParameters.customParameters.getIntOrElse(1,4)
        val wallAmountPerWall = oldParameters.customParameters.getIntOrElse(2,32)

        val allSpookyWalls: ArrayList<SpookyWall> = getBoxList(wallAmountPerWall)

        for(start in 0 until amountOfTicks){
            val tempList = ArrayList(allSpookyWalls.map { it.copy() })
            repeat(amountPerTick){
                val w = tempList.random()
                w.startTime = start.toDouble()/amountOfTicks
                w.height = 0.0
                w.width = 0.0
                list.add(w)
                tempList.remove(w)
            }
        }
        return list
    }
}

/** gets very small noise in the area -4 .. 4 */
object RandomNoise: OldWallStructure() {
    override val mirror = false
    override val name = "RandomNoise"
    override val spookyWallList: ArrayList<SpookyWall> = arrayListOf()
    override fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall> {
        spookyWallList.clear()
        val intensity = try { oldParameters.customParameters[0].toInt() } catch (e:Exception){ 5 }
        repeat(intensity){
            val tempO = SpookyWall(
                startRow = Random.nextDouble(-4.0, 4.0),
                duration = 0.01,
                width = 0.01,
                height = 0.01,
                startHeight = Random.nextDouble(4.0),
                startTime = Random.nextDouble()
            )
            spookyWallList.add(tempO)
        }
        return spookyWallList
    }
}

/** gets very small noise in the area -30 .. 30 */
object BroadRandomNoise: OldWallStructure() {
    override val mirror = false
    override val name = "BroadRandomNoise"
    override val spookyWallList: ArrayList<SpookyWall> = arrayListOf()
    override fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall> {
        spookyWallList.clear()
        val intensity = try { oldParameters.customParameters[0].toInt() } catch (e:Exception){ 5 }
        repeat(intensity){
            val tempO = SpookyWall(
                startRow = Random.nextDouble(-50.0, 50.0),
                duration = 0.01,
                width = 0.01,
                height = 0.01,
                startHeight = Random.nextDouble(4.0),
                startTime = Random.nextDouble()
            )
            spookyWallList.add(tempO)
        }
        return spookyWallList
    }
}

/** random blocks to the right and left */
object RandomBlocks: OldWallStructure() {
    override val mirror = false
    override val name = "RandomBlocks"
    override val spookyWallList: ArrayList<SpookyWall> = arrayListOf()
    override fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall> {
        spookyWallList.clear()
        val duration = oldParameters.customParameters.getDoubleOrElse(0,4.0)
        for(i in 0 until duration.toInt()){
            spookyWallList.add(
                SpookyWall(
                    Random.nextDouble(10.0, 20.0),
                    Random.nextDouble(0.5),
                    Random.nextDouble(2.0),
                    Random.nextDouble(2.0),
                    0.0,
                    i.toDouble()
                )
            )
            spookyWallList.add(
                SpookyWall(
                    Random.nextDouble(-20.0, -10.0),
                    Random.nextDouble(0.5),
                    Random.nextDouble(2.0),
                    Random.nextDouble(2.0),
                    0.0,
                    i.toDouble()
                )
            )
        }
        return spookyWallList
    }
}

/** random blocks to the right and left */
object RandomFastBlocks: OldWallStructure() {
    override val mirror = false
    override val name = "RandomFastBlocks"
    override val spookyWallList: ArrayList<SpookyWall> = arrayListOf()
    override fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall> {
        spookyWallList.clear()
        val duration = oldParameters.customParameters.getDoubleOrElse(0,4.0)
        for(i in 0 until duration.toInt()){
            spookyWallList.add(
                SpookyWall(
                    Random.nextDouble(10.0, 20.0),
                    -2.0,
                    Random.nextDouble(2.0),
                    Random.nextDouble(2.0),
                    0.0,
                    i.toDouble()
                )
            )
            spookyWallList.add(
                SpookyWall(
                    Random.nextDouble(-20.0, -10.0),
                    -2.0,
                    Random.nextDouble(2.0),
                    Random.nextDouble(2.0),
                    0.0,
                    i.toDouble()
                )
            )
        }
        return spookyWallList
    }
}

/** LaneShifter Object - when called, creates an array Lines between the 4 given Points */
object LineShifter: OldWallStructure() {
    override val name = "LineShifter"
    override val mirror = false
    override val spookyWallList = arrayListOf<SpookyWall>()
    override fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall> {
        val list = arrayListOf<SpookyWall>()
        val amount = oldParameters.customParameters.getIntOrElse(0,4)
        val p1x1 =oldParameters.customParameters.getDoubleOrElse(1,-4.0)
        val p1y1 =oldParameters.customParameters.getDoubleOrElse(2,-4.0)
        val p1x2 =oldParameters.customParameters.getDoubleOrElse(3,-4.0)
        val p1y2 =oldParameters.customParameters.getDoubleOrElse(4,-4.0)
        val p2x1 =oldParameters.customParameters.getDoubleOrElse(5,-4.0)
        val p2y1 =oldParameters.customParameters.getDoubleOrElse(6,-4.0)
        val p2x2 =oldParameters.customParameters.getDoubleOrElse(7,-4.0)
        val p2y2 =oldParameters.customParameters.getDoubleOrElse(8,-4.0)
        var tempx1 = p1x1
        var tempx2 = p1x2
        var tempy1 =p1y1
        var tempy2 = p1y2

        for (i in 0 until amount){
            list.addAll(
                line(
                    tempx1,
                    tempx2,
                    tempy1,
                    tempy2,
                    i.toDouble() / amount,
                    i.toDouble() / amount,
                    null,
                    1.0 / amount
                )
            )
            tempx1 += (p2x1-p1x1)/amount
            tempx2 += (p2x2-p1x2)/amount
            tempy1 += (p2y1-p1y1)/amount
            tempy2 += (p2y2-p1y2)/amount
        }
        return list
    }
}


object SideWave: OldWallStructure(){
    override val name = "SideWave"
    override val mirror = true
    override val spookyWallList: ArrayList<SpookyWall> = arrayListOf()
    override fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall> {
        val list = arrayListOf<SpookyWall>()
        val max = oldParameters.customParameters.getIntOrElse(0,8)
        for(i in 0 until (max)){
            val y = i/max*(2* PI)
            val nY = (i+1)/max*(2* PI)

            list.add(
                SpookyWall(
                    duration = 1 / max.toDouble(),
                    height = abs(cos(nY) - cos(y)),
                    startHeight = 1 - cos(y),
                    startRow = 3.0,
                    width = 0.5,
                    startTime = i / max.toDouble()
                )
            )
        }
        return list
    }
}

