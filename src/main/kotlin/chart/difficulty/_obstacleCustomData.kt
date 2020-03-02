package chart.difficulty

import com.google.gson.annotations.SerializedName

@Suppress("ClassName")
data class _obstacleCustomData(
    @SerializedName("_posX") val _posX: Double?,
    @SerializedName("_posY") val _posY: Double?,
    @SerializedName("_width") val _width: Double?,
    @SerializedName("_height") val _height: Double?,
    @SerializedName("_color") val _color: List<Double>?,
    @SerializedName("_rotation") val _rotation: List<Double>?,
    @SerializedName("_localRotation") val _localRotation: List<Double>?,
    @SerializedName("track") var track: String?
)
