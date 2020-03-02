package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _customEventCustomData(
    @SerializedName("name") var name: String?,
    @SerializedName("offset") var offset: List<Double>?,
    @SerializedName("angle") var angle: Double?,
    @SerializedName("axis") var axis: List<Double>?,
    @SerializedName("duration") var duration: Double?,
    @SerializedName("start") var start: _customEventCustomDataPosition?,
    @SerializedName("end") var end: _customEventCustomDataPosition?,
    @SerializedName("value") var value: Any?
)
data class _customEventCustomDataPosition(
    @SerializedName("offset") var offset: List<Double>?,
    @SerializedName("angle") var angle: Double?,
    @SerializedName("axis") var axis: List<Double>?
)
