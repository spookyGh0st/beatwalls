@file:Suppress("ClassName")

package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _customEvents (
    @SerializedName("_time") var _time: Float,
    @SerializedName("_type") var _type: String,
    @SerializedName("_data") var _data: _customEventCustomData?
)
