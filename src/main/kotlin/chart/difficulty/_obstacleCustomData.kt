package chart.difficulty

import com.google.gson.annotations.SerializedName

@Suppress("ClassName")
data class _obstacleCustomData(
    @SerializedName("_position") val _position: List<Double>? = null,
    @SerializedName("_scale") val _scale: List<Double>? = null,
    @SerializedName("_color") val _color: List<Double>? = null,
    @SerializedName("_rotation") val _rotation: List<Double>? = null,
    @SerializedName("_localRotation") val _localRotation: List<Double>? = null,
    @SerializedName("_track") var _track: String? = null,
    @SerializedName("_noteJumpStartBeat") var _noteJumpStartBeat: Double? = null,
    @SerializedName("_noteJumpStartBeatOffset") var _noteJumpStartBeatOffset: Double? = null,
    @SerializedName("_animation") var _animation: _objectAnimation? = null
)

data class _objectAnimation(
    @SerializedName("_position") val _position: PointDefinition? = null,
    @SerializedName("_rotation") val _rotation: PointDefinition? = null,
    @SerializedName("_scale") val _scale: PointDefinition? = null,
    @SerializedName("_localRotation") val _localRotation: PointDefinition? = null,
    @SerializedName("_definitePosition") val _definitePosition: PointDefinition? = null,
    @SerializedName("_dissolve") val _dissolve: PointDefinition? = null,
    @SerializedName("_dissolveArrow") val _dissolveArrow: PointDefinition? = null
)

typealias PointDefinition=List<List<Double>>
