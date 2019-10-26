package assetFile

import com.github.spookyghost.beatwalls.errorExit
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import java.io.File

class AssetFile(
    @SerializedName("Version")
    val version: Double = 1.0,
    @SerializedName("last Song")
    var currentSong:String,
    @SerializedName("Saved Structures")
    val savedStructures: ArrayList<SavedWallStructure> = arrayListOf()
) {
    @Transient
    private val logger = KotlinLogging.logger {}
}

fun readAssetFile(): AssetFile {
    val f = File(System.getProperty("java.class.path"))
    val dir = f.absoluteFile.parentFile
    val file = File(dir,"BeatWallAsset.json")
    if(!file.exists()){
        errorExit { "No Asset File detected" }
    }
    val json =file.readText()
    return Gson().fromJson(json,AssetFile::class.java)
}



