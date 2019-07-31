package song
import com.google.gson.annotations.SerializedName
import structures.WallStructureManager

data class Difficulty (

    @SerializedName("_version") var _version : String,
    @SerializedName("_BPMChanges") val _BPMChanges : ArrayList<_BPMChanges>,
    @SerializedName("_bookmarks") val _bookmarks : ArrayList<_bookmarks>,
    @SerializedName("_events") var _events : ArrayList<_events>,
    @SerializedName("_notes") var _notes : ArrayList<_notes>,
    @SerializedName("_obstacles") var _obstacles : ArrayList<_obstacles>
)

fun Difficulty.containsCommand(string: String) = this._bookmarks.any { it._name.contains("/$string") }

/*
fun Difficulty.createWalls(bpm: Double){
    _bookmarks.forEach { it ->

        val tempBpm =_BPMChanges.findLast{ bpmChanges -> bpmChanges._time <= it._time }?._BPM ?: bpm

        val list = arrayListOf<_obstacles>()

        val timeOffset = it._time

        it.forEachBSCommand("bw"){
            list.addAll(WallStructureManager.get(it))
        }


        list.forEach {
            it.adjust(bpm, tempBpm, timeOffset)
            _obstacles.add(it)
        }
    }
}
 */





