package assetFile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**Song Class the File saves */
data class SavedSongData(
    @Expose
    @SerializedName("Song Path")
    val SongPath: String,
    @Expose
    @SerializedName("NJS Offset")
    val njsOffset: Double
)