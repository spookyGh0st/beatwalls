package reader

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import song.Difficulty
import song.Info
import song._obstacles
import old_structures.CustomOldWallStructure
import java.io.*
import java.nio.file.Paths

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

fun readOldAssets(f:File):ArrayList<_obstacles>{
    val reader = BufferedReader(FileReader(f))
    val json = reader.readText()
    reader.close()
    val base = Gson().fromJson(json, Array<_obstacles>::class.java)
    val list = base.map { it }
    return ArrayList(ArrayList(list))
}

data class AssetsBase (
    @SerializedName("WallStructureList") val customWallStructure : List<CustomOldWallStructure>
)

fun File.isDifficulty() =
    this.isFile && (this.name == "EasyStandard.dat" || this.name == "NormalStandard.dat" || this.name == "HardStandard.dat" || this.name == "ExpertStandard.dat" || this.name == "ExpertPlusStandard.dat" )

fun File.isSong() =
    this.isDirectory && this.list()?.contains("info.dat")?:false

fun File.isOldAsset() =
    this.isFile && this.name.endsWith(".oldAsset")


fun writeInfo(info: Info, file: File){
    try {
        val json = Gson().toJson(info)
        logger.info { "prepared to write info.json" }
        val writer = BufferedWriter(FileWriter(file))
        writer.write(json)
        writer.close()
    }catch (e:Exception){
        logger.error { "Failed to write info.json" }
    }
}
fun writeCodeGen(text:String){
    try {
        val file =  Paths.get(System.getProperty("user.dir"),"codegen.txt").toFile()
        val writer = BufferedWriter(FileWriter(file))
        writer.write(text)
        writer.close()
    }catch (e:Exception){
        logger.error { "Failed to write info.json" }
    }
}

fun writeDifficulty(diff:Difficulty,file: File){
    try {
        val text = Gson().toJson(diff)
        val writer = BufferedWriter(FileWriter(file))
        writer.write(text)
        writer.close()
        logger.info { "written difficulty file to $file" }
    }catch (e:Exception){
        logger.error { "Failed to write Difficulty" }
        logger.error { e.message }
    }
}

fun writeAssets(customWallStructureList:List<CustomOldWallStructure>){
    try {
        val list = customWallStructureList.toMutableList()
        if( list.isEmpty()){
            logger.info { "Created default Assets" }
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

