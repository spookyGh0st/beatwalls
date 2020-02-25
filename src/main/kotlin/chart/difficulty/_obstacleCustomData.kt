package chart.difficulty

import com.google.gson.annotations.SerializedName

@Suppress("ClassName")
data class _obstacleCustomData(
    @SerializedName("_startRow") val _startRow: Double?,
    @SerializedName("_startHeight") val _startHeight: Double?,
    @SerializedName("_width") val _width: Double?,
    @SerializedName("_height") val _height: Double?,
    @SerializedName("_color") val _color: List<Double>?,
    @SerializedName("track") var track: String?
)
