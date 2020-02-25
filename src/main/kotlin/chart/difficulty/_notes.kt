package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _notes (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_lineIndex") val _lineIndex : Int,
    @SerializedName("_lineLayer") val _lineLayer : Int,
    @SerializedName("_type") val _type : Int,
    @SerializedName("_cutDirection") val _cutDirection : Int,
    @SerializedName("_customData") val _noteCustomData : _noteCustomData?
)

data class _noteCustomData(
    @SerializedName("scale") var scale: Double?,
    @SerializedName("sineMovement") var sineMovement: sineMovement?
)
data class sineMovement(
    @SerializedName("amplitude") var amplitude: Double?,
    @SerializedName("frequency") var frequency: Double?,
    @SerializedName("track") var track: String?
)
