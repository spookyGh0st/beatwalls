package model.difficulty

import com.google.gson.annotations.SerializedName


data class _customEvents (
    @SerializedName("_time") var _time: Float,
    @SerializedName("_type") var _type: String,
    @SerializedName("_data") var _data: _customEventData
)

data class _customEventData(
    @SerializedName("_track") val _track: String? = null,
    @SerializedName("_duration") val _duration: Double? = null,
    @SerializedName("_position") val _position: PointDefinition? = null,
    @SerializedName("_rotation") val _rotation: PointDefinition? = null,
    @SerializedName("_scale") val _scale: PointDefinition? = null,
    @SerializedName("_localRotation") val _localRotation: PointDefinition? = null,
    @SerializedName("_definitePosition") val _definitePosition: PointDefinition? = null,
    @SerializedName("_dissolve") val _dissolve: PointDefinition? = null,
    @SerializedName("_dissolveArrow") val _dissolveArrow: PointDefinition? = null
)

typealias PointDefinition=List<List<Double>>

