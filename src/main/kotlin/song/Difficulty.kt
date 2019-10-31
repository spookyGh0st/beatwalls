package song
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

    @SerializedName("_version") var _version : String,
    @SerializedName("_BPMChanges") val _BPMChanges : ArrayList<_BPMChanges>,
    @SerializedName("_bookmarks") val _bookmarks : ArrayList<_bookmarks>,
    @SerializedName("_events") var _events : ArrayList<_events>,
    @SerializedName("_notes") var _notes : ArrayList<_notes>,
    @SerializedName("_obstacles") var _obstacles : ArrayList<_obstacles>
){
    fun createWalls(list: ArrayList<WallStructure>, metaData: MetaData){
        this._obstacles.clear()
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
        }
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
        errorExit(e) { "Failed to read in the Difficulty. Was there a change in the version or something?" }
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
