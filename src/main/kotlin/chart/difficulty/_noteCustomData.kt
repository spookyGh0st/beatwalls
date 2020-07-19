package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _noteCustomData(
    @SerializedName("scale") var scale: Double? = null,
    @SerializedName("sineMovement") var sineMovement: sineMovement? = null,
    @SerializedName("_track") var track: String? = null,
    @SerializedName("_position") val _position: List<Double>? = null,
    @SerializedName("_color") val _color: List<Double>? = null,
    @SerializedName("_rotation") val _rotation: Double? = null,
    @SerializedName("_flip") val _flip: Double? = null,
    @SerializedName("_noteJumpStartBeat") var _noteJumpStartBeat: Double? = null,
    @SerializedName("_noteJumpStartBeatOffset") var _noteJumpStartBeatOffset: Double? = null

)

data class sineMovement(
    @SerializedName("amplitude") var amplitude: Double?,
    @SerializedName("frequency") var frequency: Double?,
    @SerializedName("track") var _track: String?
)
