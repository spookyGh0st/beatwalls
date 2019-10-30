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
    }
}


