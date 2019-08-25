package reader

import structures.CustomWallStructure
import structures.MyObstacle

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
                "fineStairwayUp",
                true, stairwayUp(30.0)
            ),
            CustomWallStructure(
                "fineStairwayDown",
                true,
                stairwayDown(30.0)
            ),
            CustomWallStructure(
                "roughStairwayUp",
                true,
                stairwayUp(5.0)
            ),
            CustomWallStructure(
                "roughStairwayDown",
                true,
                stairwayDown(5.0)
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
            )
        )
    )

    return a
}

fun stairwayUp(max:Double): ArrayList<MyObstacle> {
    val list = arrayListOf<MyObstacle>()
    val maxH = 3.996
    for(i in 0 until max.toInt()){
        list.add(
            MyObstacle(
                1 / max,
                1 / max * maxH,
                i / max * maxH,
                3.0,
                1 / max * maxH,
                i / max
            )
        )
    }
    return list
}

fun stairwayDown(max: Double): ArrayList<MyObstacle> {
    val list = arrayListOf<MyObstacle>()
    val maxH = 3.996
    for(i in 0 until max.toInt()){
        list.add(
            MyObstacle(
                1 / max,
                1 / max * maxH,
                maxH - i / max * maxH,
                3.0,
                1 / max * maxH,
                i / max
            )
        )
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
                3.0,
                0.005,
                0.0
            ),
            MyObstacle(
                1.0,
                0.005,
                2.0,
                3.0,
                0.005,
                0.0
            ),
            MyObstacle(
                0.005,
                2.0,
                0.0,
                3.0,
                0.005,
                0.0
            ),
            MyObstacle(
                0.005,
                2.0,
                0.0,
                3.0,
                0.005,
                0.995
            )
        )
    )
    return list
}
