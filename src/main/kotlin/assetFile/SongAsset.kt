package assetFile

import com.github.spookyghost.beatwalls.errorExit
import com.google.gson.GsonBuilder
import mu.KotlinLogging
import structure.WallStructure
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

class SongAsset(val path:String = "", val list: ArrayList<WallStructure> = arrayListOf()){
    private val logger = KotlinLogging.logger {}
    fun create() {
        val f = File(System.getProperty("java.class.path"))
        val dir = f.absoluteFile.parentFile
        val tPath = dir.toString()
        File(tPath, "SongAssets").mkdirs()
        val file = Paths.get(tPath, "SongAssets", "${Paths.get(path).fileName}.json").toFile()
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = gson.toJson(this)
            if(file.exists()){
                logger.info { "File already exist" }
                return
            }
            val writer = BufferedWriter(FileWriter(file))
            writer.write(json)
            writer.close()
            if (!file.exists())
                throw Exception("File not found")
            logger.info { "saved AssetFile at ${file.absolutePath}" }
        }catch (e:Exception){
            errorExit(e) { "Failed to save Assets" }
        }
    }
}