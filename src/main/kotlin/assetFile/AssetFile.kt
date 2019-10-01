package assetFile

import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import structures.CustomWallStructure
import structures.Parameter.Command

private val logger = KotlinLogging.logger {}


class AssetFile {
    @SerializedName("Version")
    val version: Double = 1.0
    @SerializedName("Active Song")
    val SongPath: String = ""
    @SerializedName("SongList")
    val Songs: ArrayList<Song> = arrayListOf()
    @Transient
    val currentSong = Songs.find { it.SongPath == SongPath }

    /**Song Class the File saves */
    data class Song(
        @SerializedName("Song Path")
        val SongPath: String,
        @SerializedName("NJS Offset")
        val njsOffset: Double,
        @SerializedName("Wall Structure List")
        val customWallStructure: List<CustomWallStructure> = arrayListOf(),
        @SerializedName("Mixed Structure List")
        val mixedWallStructure: List<MixedStructure> = arrayListOf()
    )

    /** A Combination of a WallList and A CommandList */
    data class MixedStructure(
        @SerializedName("Name") val name : String,
        @SerializedName("Wall List") val customWallStructure: List<CustomWallStructure>,
        @SerializedName("Command List") val command: List<Command>
    )
}


