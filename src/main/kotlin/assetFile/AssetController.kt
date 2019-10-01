package assetFile

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import mu.KotlinLogging
import java.io.*
import java.nio.file.Paths
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

object AssetController{
    private val file: File = Paths.get(File("").absoluteFile.path, "Asset.json").toFile()
    fun readAssetFile(): AssetFile {
        try {

            if(!file.exists()) {
                logger.info { "AssetFile not found, trying to create Default File" }
                writeAssetFile()
            }
            val reader = BufferedReader(FileReader(file))
            val json = reader.readText()
            val asset = Gson().fromJson(json, AssetFile::class.java)
            reader.close()
            return asset!!
        }catch (e:Exception){
            logger.error { "Failed to Readfile" }
            logger.error { e.message }
            println("Press Enter to Exit")
            readLine()
            exitProcess(1)
        }
    }

    fun writeAssetFile(){
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = gson.toJson(AssetFile())
            val writer = BufferedWriter(FileWriter(file))
            writer.write(json)
            writer.close()
            if (!file.exists())
                throw FileNotFoundException()
            logger.info { "Created default AssetFile at ${file.absolutePath}" }
        }catch (e:Exception){
            logger.error { "Failed to write Assets" }
            logger.error { e.message }
        }

    }
}
fun main() {
    val a = AssetController.readAssetFile()
    println(a.version)
}

