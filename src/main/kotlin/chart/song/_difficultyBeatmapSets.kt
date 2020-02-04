@file:Suppress("ClassName")

package chart.song

import com.google.gson.annotations.SerializedName

data class _difficultyBeatmapSets(

    @SerializedName("_beatmapCharacteristicName") val _beatmapCharacteristicName: String,
    @SerializedName("_difficultyBeatmaps") val _difficultyBeatmaps: List<_difficultyBeatmaps>
)