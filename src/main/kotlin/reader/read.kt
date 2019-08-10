package reader

import song.Difficulty
import song.Info
import structures.CustomWallStructure
import structures.AssetsBase
import structures.MyObstacle
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import mu.KotlinLogging
import java.io.*
import java.nio.file.Paths
import kotlin.math.*

private val logger = KotlinLogging.logger {}

fun readInfo(f: File):Info{
    val reader = BufferedReader(FileReader(f))
    val json = reader.readText()
    reader.close()
    return Gson().fromJson(json,Info::class.java)
}

fun readDifficulty(f:File): Difficulty {
    val reader = BufferedReader(FileReader(f))
    val json = reader.readText()
    reader.close()
    return Gson().fromJson(json, Difficulty::class.java)
}

fun readAssets():ArrayList<CustomWallStructure>{
    val file =  Paths.get(System.getProperty("user.dir"),"BeatwallAssets.json").toFile()
    if(!file.exists()) {
        writeAssets(listOf())
    }
    val reader = BufferedReader(FileReader(file))
    val json = reader.readText()
    val base = Gson().fromJson(json, AssetsBase::class.java)
    reader.close()
    return ArrayList(base.customWallStructure)
}



fun File.isDifficulty() =
    this.isFile && (this.name == "Easy.dat" || this.name == "Normal.dat" || this.name == "Hard.dat" || this.name == "Expert.dat" || this.name == "ExpertPlus.dat" )

fun File.isSong() =
    this.isDirectory && this.list()?.contains("info.dat")?:false



fun writeInfo(info: Info, file: File){
    try {
        val json = Gson().toJson(info)
        logger.info { "prepared to write info.json" }
        val writer = BufferedWriter(FileWriter(file))
        writer.write(json)
        writer.close()
    }catch (e:Exception){
        logger.error { "Failed to write info.json" }
        println(e)
    }
}

fun writeDifficulty(pair: Pair<Difficulty,File>){
    try {
        val text = Gson().toJson(pair.component1())
        val writer = BufferedWriter(FileWriter(pair.component2()))
        writer.write(text)
        writer.close()
    }catch (e:Exception){
        logger.error { "Failed to write Difficulty" }
    }
}

fun writeAssets(customWallStructureList:List<CustomWallStructure>){
    try {
        val list = customWallStructureList.toMutableList()
        if( list.isEmpty()){
            logger.info { "Created default Assets" }
            list+= createAssets()
        }
        val base = AssetsBase(list)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(base)
        val writer = BufferedWriter(FileWriter(Paths.get(System.getProperty("user.dir"),"BeatwallAssets.json").toFile()))
        writer.write(json)
        writer.close()
    }catch (e:Exception){
        logger.error { "Failed to write Assets" }
    }
}

fun createAssets():List<CustomWallStructure> = listOf(
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
    CustomWallStructure("fineStairwayUp", true, stairwayUp(30.0)),
    CustomWallStructure("fineStairwayDown", true, stairwayDown(30.0)),
    CustomWallStructure("roughStairwayUp",true, stairwayUp(5.0)),
    CustomWallStructure("roughStairwayDown",true, stairwayDown(5.0)),
    CustomWallStructure("Tube",false, circle()),
    CustomWallStructure("Helix",false, circle(helix = true)),
    CustomWallStructure("Ring",false, circle(pDuration = 0.1)),
    CustomWallStructure("Helix2",false, circle(count = 2,helix = true)),
    CustomWallStructure("Helix3",false, circle(count = 3,helix = true)),
    CustomWallStructure("Helix4",false, circle(count = 4,helix = true))
)

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

fun circle(count:Int = 1,radius:Double = 1.9, fineTuning:Int = 30,pDuration:Double = 1.0, helix:Boolean = false):ArrayList<MyObstacle>{

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
        val offset = round((o*2.0* PI*fineTuning) /count) //todo borked
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

