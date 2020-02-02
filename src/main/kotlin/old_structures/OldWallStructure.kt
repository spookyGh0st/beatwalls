package old_structures

import structure.*


sealed class OldWallStructure  {
    abstract val name: String

    abstract val mirror: Boolean

    abstract val spookyWallList: ArrayList<SpookyWall>

    abstract fun getWallList(oldParameters: OldParameters): ArrayList<SpookyWall>
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

