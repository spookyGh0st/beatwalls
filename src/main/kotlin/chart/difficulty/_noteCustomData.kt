package chart.difficulty

import com.google.gson.annotations.SerializedName

@Suppress("ClassName")
data class _noteCustomData(
    var _position: List<Double>? = null,
    val _flip: Double? = null,
    val _cutDirection: Double? = null,
    val _color: List<Double>? = null,
    var _rotation: List<Double>? = null,
    val _localRotation: List<Double>? = null,
    var _track: String? = null,
    var _noteJumpMovementSpeed: Double? = null,
    var _noteJumpStartBeatOffset: Double? = null,
    var _fake: Boolean? = null,
    var _interactable: Boolean? = null,
    var _disableNoteGravity: Boolean? = null,
    var _animation: _Animation? = null,
)

