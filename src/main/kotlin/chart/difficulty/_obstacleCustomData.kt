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
    @SerializedName("_position") var _position: PointDefinition? = null,
    @SerializedName("_rotation") var _rotation: PointDefinition? = null,
    @SerializedName("_scale") var _scale: PointDefinition? = null,
    @SerializedName("_localRotation") var _localRotation: PointDefinition? = null,
    @SerializedName("_definitePosition") var _definitePosition: PointDefinition? = null,
    @SerializedName("_dissolve") var _dissolve: PointDefinition? = null,
    @SerializedName("_dissolveArrow") var _dissolveArrow: PointDefinition? = null
)

typealias PointDefinition=List<List<Double>>
