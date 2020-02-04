package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _obstacles (
    @SerializedName("_time") var _time : Float,
    @SerializedName("_lineIndex") val _lineIndex : Int,
    @SerializedName("_type") val _type : Int,
    @SerializedName("_duration") var _duration : Float,
    @SerializedName("_width") val _width : Int
)
