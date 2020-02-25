package chart.difficulty

import com.google.gson.annotations.SerializedName

@Suppress("ClassName")
data class _obstacleCustomData(
    @SerializedName("_color") val _color: List<Double>?,
    @SerializedName("track") var track: String?
)
