@file:Suppress("ClassName")

package chart.song

import com.google.gson.annotations.SerializedName

data class _difficultyBeatmaps(

    @SerializedName("_difficulty") val _difficulty: String,
    @SerializedName("_difficultyRank") val _difficultyRank: Int,
    @SerializedName("_beatmapFilename") val _beatmapFilename: String,

    @SerializedName("_noteJumpMovementSpeed") val _noteJumpMovementSpeed: Double,
    @SerializedName("_noteJumpStartBeatOffset") val _noteJumpStartBeatOffset: Double,

    @SerializedName("_customData") val _customData: _customData

)