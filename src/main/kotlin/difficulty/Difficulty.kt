package difficulty
import assetFile.MetaData
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import structure.Define
import structure.WallStructure

private val logger = KotlinLogging.logger {}

data class Difficulty (

    @SerializedName("_version") val _version : String,
    @SerializedName("_events") val _events : ArrayList<_events>,
    @SerializedName("_notes") val _notes : ArrayList<_notes>,
    @SerializedName("_obstacles") val _obstacles : ArrayList<_obstacles>,
    @SerializedName("_customData") val _customData : _customData
) {
    lateinit var metaData:MetaData
    fun createWalls(list: ArrayList<WallStructure>, metaData: MetaData) {
        this.metaData = metaData
        this._obstacles.removeAll(getOldObstacle())

        val tempObst = mutableListOf<_obstacles>()
        for (w in list) {
            if (w is Define && !w.isTopLevel) {
                continue
            }
            val obstacles = BpmAdjuster(this,metaData).generate(w)
            this._obstacles.addAll(obstacles)
            tempObst.addAll(obstacles)
        }
        writeOldObstacle(tempObst.toTypedArray())
    }
}

