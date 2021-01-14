@file:Suppress("ClassName")

package chart.info

import com.google.gson.annotations.SerializedName

data class _beatmapColor(
    @SerializedName("r") val r: Double,
    @SerializedName("g") val g: Double,
    @SerializedName("b") val b: Double
)