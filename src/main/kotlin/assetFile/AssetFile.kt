package assetFile

import com.github.spookyghost.beatwalls.errorExit
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import reader.isSong
import structure.CustomWallStructure
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.nio.file.Paths
import kotlin.system.exitProcess



class AssetFile(
    @SerializedName("Version")
    @Expose
    val version: Double = 1.0,
    @SerializedName("Current Song")
    @Expose
    var currentSong:SavedSongData = SavedSongData("",0.0),
    @SerializedName("Mixed Structure List")
    @Expose
    val mixedWallStructure: ArrayList<MixedStructure> = arrayListOf(),
    @SerializedName("Wall Structure List")
    @Expose
    val customWallStructure: ArrayList<CustomWallStructure> = arrayListOf()
) {
    @Transient
    private val logger = KotlinLogging.logger {}


//    ____        _     _ _        _____                 _   _
//   |  _ \ _   _| |__ | (_) ___  |  ___|   _ _ __   ___| |_(_) ___  _ __  ___
//   | |_) | | | | '_ \| | |/ __| | |_ | | | | '_ \ / __| __| |/ _ \| '_ \/ __|
//   |  __/| |_| | |_) | | | (__  |  _|| |_| | | | | (__| |_| | (_) | | | \__ \
//   |_|    \__,_|_.__/|_|_|\___| |_|   \__,_|_| |_|\___|\__|_|\___/|_| |_|___/

    /** Adding a new Song to the List */
    fun changeSong() {
        val path = readSongPath()
        val njsOffset = getNJSOffset()
        val p = path.absolutePath
        currentSong = SavedSongData(p,njsOffset)
    }

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

    /** Asking for a Path */
    private fun readSongPath(): File {
        println("Please Enter the Path of the Song you want to work on")
        val str = readLine()
        return try {
            val songP = str!!.removePrefix("\"").removeSuffix("\"")
            val f = File(songP).absoluteFile
            if(!f.isSong()){
                throw FileNotFoundException("File is not a Song")
            }
            f
        }catch (e:Exception){
            logger.error { "Failed to Read Song" }
            logger.error { e.message }
            readSongPath()
        }
    }
}



