package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _customEventCustomData(
    @SerializedName("name") var name: String?,
    @SerializedName("offset") var offset: List<Double>? = null,
    @SerializedName("angle") var angle: Double? = null,
    @SerializedName("axis") var axis: List<Double>? = null,
    @SerializedName("duration") var duration: Double? = null,
    @SerializedName("easing") var easing: String? = null,
    @SerializedName("start") var start: _customEventCustomDataPosition? = null,
    @SerializedName("end") var end: _customEventCustomDataPosition? = null,
    @SerializedName("value") var value: Any? = null
)
data class _customEventCustomDataPosition(
    @SerializedName("offset") var offset: List<Double>?,
    @SerializedName("angle") var angle: Double?,
    @SerializedName("axis") var axis: List<Double>?
)
