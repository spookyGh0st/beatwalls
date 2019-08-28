package reader

import structures.CustomWallStructure
import structures.MyObstacle
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt

fun createAssets():List<CustomWallStructure> {
    val a = arrayListOf<CustomWallStructure>()
    a.addAll(
        arrayListOf(
            CustomWallStructure(
                "Default",
                false,
                arrayListOf(MyObstacle(1.0, 0.0, 0.0, 0.0, 0.0, 0.0))
            ),
            CustomWallStructure(
                "Floor",
                false,
                arrayListOf(MyObstacle(1.0, 0.1, 0.0, -2.0, 4.0, 0.0))
            ),
            CustomWallStructure(
                "Ceiling",
                false,
                arrayListOf(MyObstacle(1.0, 0.1, 3.0, -2.0, 4.0, 0.0))
            ),
            CustomWallStructure(
                "Pillar",
                true,
                arrayListOf(MyObstacle(0.3, 12.0, 0.0, 7.7, 0.3, 0.0))
            ),
            CustomWallStructure(
                "Cathedral",
                true,
                arrayListOf(MyObstacle(1.0, 12.0, 0.0, 8.0, 1.0, 0.0))
            ),
            CustomWallStructure(
                "SplittedFloor",
                true,
                arrayListOf(MyObstacle(1.0, 0.1, 0.0, 0.0, 2.0, 0.0))
            ),
            CustomWallStructure(
                "SplittedCeiling",
                true,
                arrayListOf(MyObstacle(1.0, 0.1, 3.0, 0.0, 2.0, 0.0))
            ),
            CustomWallStructure(
                "smallPillar",
                true,
                arrayListOf(MyObstacle(0.05,2.0,0.0,2.0,0.05,0.0))
            ),
            CustomWallStructure(
                "Tube",
                false,
                structures.circle()
            ),
            CustomWallStructure(
                "Ring",
                false,
                structures.circle(pDuration = 0.05)
            ),
            CustomWallStructure(
                "fence",
                true,
                fence()
            ),
            CustomWallStructure(
                "sideWave",
                true,
                sideWave()
            ),
            CustomWallStructure(
                "FastSideWalls",
                true,
                arrayListOf(MyObstacle(-2.0,4.0,0.0,4.5,0.5,2.0))
            )
        )
    )
    return a
}


fun sideWave(): ArrayList<MyObstacle> {
    val list = arrayListOf<MyObstacle>()
    val max = 20.0
    for(i in 0 until (max).roundToInt()){
        val y = i/max*(2* PI)
        val nY = (i+1)/max*(2* PI)

        list.add(
            MyObstacle(
               duration =  1 / max,
                height = abs(cos(nY)- cos(y)),
                startHeight = 1-cos(y),
                startRow = 3.0,
                width = 0.5,
                startTime = i/max
            )
        )
        print("")
    }
    return list
}

/** default fence */
fun fence(): ArrayList<MyObstacle> {
    val list = arrayListOf<MyObstacle>()
    list.addAll(
        arrayListOf(
            MyObstacle(
                1.0,
                0.005,
                0.0,
                4.0,
                0.005,
                0.0
            ),
            MyObstacle(
                1.0,
                0.005,
                2.0,
                4.0,
                0.005,
                0.0
            ),
            MyObstacle(
                0.005,
                2.0,
                0.0,
                4.0,
                0.005,
                0.0
            ),
            MyObstacle(
                0.005,
                2.0,
                0.0,
                4.0,
                0.005,
                0.995
            )
        )
    )
    return list
}
