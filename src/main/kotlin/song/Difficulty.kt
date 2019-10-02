package song
import com.google.gson.annotations.SerializedName

data class Difficulty (

    @SerializedName("_version") var _version : String,
    @SerializedName("_BPMChanges") val _BPMChanges : ArrayList<_BPMChanges>,
    @SerializedName("_bookmarks") val _bookmarks : ArrayList<_bookmarks>,
    @SerializedName("_events") var _events : ArrayList<_events>,
    @SerializedName("_notes") var _notes : ArrayList<_notes>,
    @SerializedName("_obstacles") var _obstacles : ArrayList<_obstacles>
){
    fun containsCommand(string: String) = this._bookmarks.any { it._name.contains("/$string") }
}

