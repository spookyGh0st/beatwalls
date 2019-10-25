package assetFile

import com.github.spookyghost.beatwalls.errorExit
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths



class AssetFile(
    @SerializedName("Version")
    val version: Double = 1.0,
    @SerializedName("last Song")
    var currentSong:String,
    @SerializedName("Saved Structures")
    val customWallStructure: ArrayList<SavedWallStructure> = arrayListOf()
) {
    @Transient
    private val logger = KotlinLogging.logger {}

    fun save(){
        val file: File = Paths.get(File("").absoluteFile.path, "Asset.json").toFile()
        try {
            val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create()
            val json = gson.toJson(this)
            val writer = BufferedWriter(FileWriter(file))
            writer.write(json)
            writer.close()
            if (!file.exists())
                throw Exception("File not found")
            logger.info { "saved AssetFile at ${file.absolutePath}" }
        }catch (e:Exception){
            errorExit(e) {"Failed to save Assets"}
        }
    }

//    ____       _            _         _____                 _   _
//   |  _ \ _ __(_)_   ____ _| |_ ___  |  ___|   _ _ __   ___| |_(_) ___  _ __  ___
//   | |_) | '__| \ \ / / _` | __/ _ \ | |_ | | | | '_ \ / __| __| |/ _ \| '_ \/ __|
//   |  __/| |  | |\ V / (_| | ||  __/ |  _|| |_| | | | | (__| |_| | (_) | | | \__ \
//   |_|   |_|  |_| \_/ \__,_|\__\___| |_|   \__,_|_| |_|\___|\__|_|\___/|_| |_|___/


    /** Asking for NJS */
    private fun getNJSOffset(): Double {
        return try {
            println("Enter the NJS Offset for the File (usually 2)")
            val njsInput = readLine()?:"2"
            val njs = njsInput.toDoubleOrNull()?:2.0
            logger.info { "Set the NJS Offset to $njs" }
            njs
        }catch (e:Exception){
            logger.error { "Something went wrong, try again" }
            getNJSOffset()
        }
    }
}



