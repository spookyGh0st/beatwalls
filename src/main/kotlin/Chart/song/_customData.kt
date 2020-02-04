@file:Suppress("ClassName")

package Chart.song

import com.google.gson.annotations.SerializedName

data class _customData(

    @SerializedName("_difficultyLabel") val _difficultyLabel: String,

    @SerializedName("_editorOffset") val _editorOffset: Int,
    @SerializedName("_editorOldOffset") val _editorOldOffset: Int,

    @SerializedName("_colorLeft") val _colorLeft: _beatmapColor,
    @SerializedName("_colorRight") val _colorRight: _beatmapColor,

    @SerializedName("_warnings") val _warnings: List<String>,
    @SerializedName("_information") val _information: List<String>,
    @SerializedName("_suggestions") val _suggestions: List<String>,
    @SerializedName("_requirements") val _requirements: ArrayList<String>
)