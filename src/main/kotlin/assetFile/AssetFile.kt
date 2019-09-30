package assetFile

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import structures.CustomWallStructure
import structures.Parameter.Command
import java.io.*
import java.nio.file.Paths
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}


object AssetFile {
    @SerializedName("Version")
    val version: Double = 1.0
    @SerializedName("Song Path")
    val currentSong: String = ""
    @SerializedName("NJS Offset")
    val njsOffset: Double = 2.0
    @SerializedName("Wall Structure List")
    val customWallStructure: List<CustomWallStructure> = listOf()
    @SerializedName("Mixed Structure List")
    val mixedWallStructure: List<MixedStructure> = listOf()

    private val file: File = Paths.get(File("").absoluteFile.path, "Asset.json").toFile()

    init {
    }

    private fun readAssetFile() {
        try {
            if(!file.exists()) {
                writeAssetFile()
            }
            val reader = BufferedReader(FileReader(file))
            val json = reader.readText()
            Gson().fromJson(json, this::class.java)
            reader.close()
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
            logger.info { "AssetFile not found, trying to create Default File" }
            val list = customWallStructure.toMutableList()
            if( list.isEmpty()){
            }
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = gson.toJson(AssetFile)
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

data class MixedStructure(
    @SerializedName("Name") val name : String,
    @SerializedName("Wall List") val customWallStructure: List<CustomWallStructure>,
    @SerializedName("Command List") val command: List<Command>
)
fun main(){
    AssetFile.writeAssetFile()
    println(AssetFile.version)

}


