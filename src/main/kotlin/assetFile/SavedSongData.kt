package assetFile

import com.google.gson.annotations.SerializedName
import structures.CustomWallStructure

/**Song Class the File saves */
data class SavedSongData(
    @SerializedName("Song Path")
    val SongPath: String,
    @SerializedName("NJS Offset")
    val njsOffset: Double,
    @SerializedName("Wall Structure List")
    val customWallStructure: List<CustomWallStructure> = arrayListOf(),
    @SerializedName("Mixed Structure List")
    val mixedWallStructure: List<MixedStructure> = arrayListOf()
)