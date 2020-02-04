@file:Suppress("ClassName")

package chart.song

import com.google.gson.annotations.SerializedName

data class _contributors(
    @SerializedName("_role") val _role: String,
    @SerializedName("_name") val _name: String,
    @SerializedName("_iconPath") val _iconPath: String
)