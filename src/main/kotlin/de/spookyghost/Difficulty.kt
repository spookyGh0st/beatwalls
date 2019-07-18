package de.spookyghost
import com.google.gson.annotations.SerializedName

data class Difficulty (

    @SerializedName("_version") var _version : String,
    @SerializedName("_BPMChanges") val _BPMChanges : List<_BPMChanges>,
    @SerializedName("_bookmarks") val _bookmarks : List<_bookmarks>,
    @SerializedName("_events") var _events : List<_events>,
    @SerializedName("_notes") var _notes : List<_notes>,
    @SerializedName("_obstacles") var _obstacles : List<_obstacles>
)

data class _BPMChanges (

    @SerializedName("_BPM") val _BPM : Double,
    @SerializedName("_time") val _time : Double,
    @SerializedName("_beatsPerBar") val _beatsPerBar : Int,
    @SerializedName("_metronomeOffset") val _metronomeOffset : Int
)

data class _bookmarks (

    @SerializedName("_time") val _time : Double,
    @SerializedName("_name") val _name : String
)

data class _events (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_type") var _type : Int,
    @SerializedName("_value") val _value : Int
)

data class _notes (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_lineIndex") val _lineIndex : Int,
    @SerializedName("_lineLayer") val _lineLayer : Int,
    @SerializedName("_type") val _type : Int,
    @SerializedName("_cutDirection") val _cutDirection : Int
)

data class _obstacles (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_lineIndex") val _lineIndex : Int,
    @SerializedName("_type") val _type : Int,
    @SerializedName("_duration") var _duration : Double,
    @SerializedName("_width") val _width : Int
)