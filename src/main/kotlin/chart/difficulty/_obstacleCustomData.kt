package chart.difficulty

import com.google.gson.annotations.SerializedName

@Suppress("ClassName")
data class _obstacleCustomData(
    @SerializedName("_posX") val _posX: Double? = null,
    @SerializedName("_posY") val _posY: Double? = null,
    @SerializedName("_width") val _width: Double? = null,
    @SerializedName("_height") val _height: Double? = null,
    @SerializedName("_color") val _color: List<Double>? = null,
    @SerializedName("_rotation") val _rotation: List<Double>? = null,
    @SerializedName("_localRotation") val _localRotation: List<Double>? = null,
    @SerializedName("track") var track: String? = null
)
