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
        arrayListOf(MyObstacle(1.0, 6.0, 0.0, 7.5, 0.5, 0.0))
    ),
    CustomWallStructure(
        "Cathedral",
        true,
        arrayListOf(MyObstacle(1.0, 6.0, 0.0, 8.0, 1.0, 0.0))
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
    CustomWallStructure("roughStairwayDown",true, stairwayDown(5.0))
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

fun circle():ArrayList<MyObstacle>{
    val fineTuning = 5
    var radius =  1.9

    val list = arrayListOf<MyObstacle>()
    val max = 2.0* PI *fineTuning


    var x = 1.0
    var y = 0.0
    var pX = 1.0
    var pY= 0.0
    var ppX:Double
    var ppY:Double

    var nX:Double
    var nY:Double
    var nnX:Double
    var nnY:Double

    var width = 0.0
    var height = 0.0
    var startRow = 0.0
    var startHeight = 0.0

    for (i in 1..round(max).toInt()){
        val j = i.toDouble()/fineTuning
        ppX = pX
        ppY = pY

        pX = x
        pY = y

        x = cos(j )
        y = sin(j)

        nX = cos((i+1).toDouble()/fineTuning)
        nY = sin((i+1).toDouble()/fineTuning)
        nnX =cos((i+2).toDouble()/fineTuning)
        nnY = sin((i+2).toDouble()/fineTuning)


        when{
            x>0 && y>0 -> {
                startRow = x
                width = pX-x
                startHeight = y
                height = nY -y
            }
            x<0 && y>0 -> {}
            x<0 && y<0 -> {}
            x>0 && y<0 -> {}
        }
        println("x: %.4f, y: %.4f\tpX: %.4f pY: %.4f".format(x,y,pX,pY))

    }
    println(round(max))
    TODO()
}
fun main(){
    circle()
}