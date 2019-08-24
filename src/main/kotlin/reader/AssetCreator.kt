package reader

import structures.CustomWallStructure
import structures.MyObstacle
import kotlin.math.*

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
                circle()
            ),
            CustomWallStructure(
                "Ring",
                false,
                circle(pDuration = 0.05)
            ),
            CustomWallStructure(
                "fence",
                true,
                fence()
            )
        )
    )
    //add helixes
    a.addAll(helix())

    return a
}

fun helix():ArrayList<CustomWallStructure>{
    val list = arrayListOf<CustomWallStructure>()
    list.add(CustomWallStructure("Helix", false, circle(helix = true)))
    for(i in 2..20){
        list.add(CustomWallStructure("Helix$i",false, circle(count = i,helix = true)))
    }
    return list
}

fun main(){

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

fun circle(count:Int = 1,radius:Double = 1.9, fineTuning:Int = 10,pDuration:Double = 1.0, helix:Boolean = false):ArrayList<MyObstacle>{

    val list = arrayListOf<MyObstacle>()
    val max = 2.0* PI *fineTuning

    var x: Double
    var y: Double
    var nX:Double
    var nY:Double

    var width: Double
    var height: Double
    var startRow: Double
    var startHeight: Double

    var startTime:Double
    var duration:Double

    for(o in 0 until count){
        val offset = round((o*2.0* PI *fineTuning) /count) //todo borked
        for (i in 0..round(max).toInt()){
            x = radius * cos((i+offset)/fineTuning)
            y = radius * sin((i+offset)/fineTuning)

            nX = radius * cos(((i+offset)+1)/fineTuning)
            nY = radius * sin(((i+offset)+1)/fineTuning)

            startRow = x + (nX - x)
            width = abs(nX -x )
            startHeight = if(y>=0) y else nY
            startHeight+=2
            height = abs(nY-y)

            duration = if(helix) 1.0/max else pDuration
            startTime = if(helix) i/max else 0.0
            list.add(MyObstacle(duration,height,startHeight,startRow,width,startTime))
        }
    }
    return list
}

