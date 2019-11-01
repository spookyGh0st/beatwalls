package difficulty
import assetFile.MetaData
import com.github.spookyghost.beatwalls.errorExit
import com.github.spookyghost.beatwalls.readPath
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import structure.Define
import structure.Save
import structure.WallStructure
import structure.walls
import java.io.File

private val logger = KotlinLogging.logger {}

data class Difficulty (

    @SerializedName("_version") val _version : String,
    @SerializedName("_events") val _events : ArrayList<_events>,
    @SerializedName("_notes") val _notes : ArrayList<_notes>,
    @SerializedName("_obstacles") val _obstacles : ArrayList<_obstacles>,
    @SerializedName("_customData") val _customData : _customData
){
    fun createWalls(list: ArrayList<WallStructure>, metaData: MetaData){
        this._obstacles.removeAll(getOldObstacle())
        val tempObst = mutableListOf<_obstacles>()
        for(w in list){
            if (w is Save )
                continue
            if (w is Define && !w.isTopLevel){
                continue
            }

            val walls = w.walls()
            walls.forEach { it.startTime+=w.beat }
            walls.forEach { it.adjustToBPM(metaData.bpm, this) }
            walls.forEach { it.startTime+=metaData.offset }

            // adds the njsOffset if time is true
            if(w.time)
                walls.forEach { it.startTime+=metaData.hjd }

            val obstacles = walls.map { it.to_obstacle() }
            this._obstacles.addAll(obstacles)
            tempObst.addAll(obstacles)
        }
        writeOldObstacle(tempObst.toTypedArray())
    }
}

fun getOldObstacle(): Array<_obstacles>{
    val file = readOldObstacleFile()
    return try {
        val json = file.readText()
        Gson().fromJson(json,Array<_obstacles>::class.java)!!
    }catch (e:Exception){
        arrayOf()
    }
}

fun writeOldObstacle(l:Array<_obstacles>){
    val file = readOldObstacleFile()
    try {
        val json = Gson().toJson(l)
        file.writeText(json)
    }catch (e:Exception){
        errorExit(e) { "Failed to write Old Obstacles" }
    }
}

fun writeDifficulty(diff: Difficulty){
    try {
        val file = getDifficultyFile()
        val json = Gson().toJson(diff)
        file.writeText(json)
        logger.info { "written difficulty file to $file" }
    }catch (e:Exception){
        errorExit(e) { "Failed to write difficulty" }
    }
}

fun readDifficulty(): Difficulty {
    val json = getDifficultyFile().readText()
    return try {
        Gson().fromJson(json, Difficulty::class.java)
    }catch (e:Exception){
        errorExit(e) { "Failed to read in the  Was there a change in the version or something?" }
        Gson().fromJson(json, Difficulty::class.java)
    }
}

fun getDifficultyFile(): File = try {
    val file = readPath()
    val name = file.nameWithoutExtension + ".dat"
    val directory = file.parentFile
    File(directory,name)
}catch (e:Exception){
    errorExit(e) { "Failed to read in the AssetFile" }
    File("")
}

fun readOldObstacleFile(): File = try {
    val file = readPath()
    val name = file.nameWithoutExtension + ".oldObst"
    val directory = file.parentFile
    File(directory,name)
}catch (e:Exception){
    errorExit(e) { "Failed to read in the oldObst" }
    File("")

}
