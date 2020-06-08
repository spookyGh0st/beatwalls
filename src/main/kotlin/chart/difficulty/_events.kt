package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _events (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_type") var _type : Int,
    @SerializedName("_value") var _value : Int,
    @SerializedName("_customData") var _eventCustomData : _eventCustomData? = null
)