package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _noteCustomData(
    @SerializedName("_track") var track: String? = null,
    @SerializedName("_position") val _position: List<Double>? = null,
    @SerializedName("_color") var _color: List<Double>? = null,
    @SerializedName("_rotation") val _rotation: Double? = null,
    @SerializedName("_flip") val _flip: Double? = null,
    @SerializedName("_noteJumpStartBeat") var _noteJumpStartBeat: Double? = null,
    @SerializedName("_noteJumpStartBeatOffset") var _noteJumpStartBeatOffset: Double? = null,
    @SerializedName("_animation") var _animation: _objectAnimation? = null
)


