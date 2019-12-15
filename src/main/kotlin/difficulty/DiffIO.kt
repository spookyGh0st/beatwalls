package difficulty

import com.github.spookyghost.beatwalls.errorExit
import com.github.spookyghost.beatwalls.readPath
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import mu.KotlinLogging
import java.io.File
import java.math.BigDecimal


private val logger = KotlinLogging.logger {}

fun writeDifficulty(diff: Difficulty){
    try {
        val file = getDifficultyFile()
        val json = Gson().toJson(diff)
        //todo remove scientific notations
        //todo adjust for mm return values
        //https://stackoverflow.com/questions/11119094/switch-off-scientific-notation-in-gson-double-serialization#18892735
        file.writeText(json)
        logger.info { "written difficulty file to $file" }
    }catch (e:Exception){
        errorExit(e) { "Failed to write difficulty" }
    }
}

fun difficultyGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(Double::class.java,
        JsonSerializer<Double?> { src, _, _ ->
            val value = BigDecimal.valueOf(src!!)
            JsonPrimitive(value)
        })
    return gsonBuilder.create()
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

