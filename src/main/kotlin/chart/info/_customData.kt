@file:Suppress("ClassName")

package chart.info

data class _customData(
    val _difficultyLabel: String?,

    val _editorOffset: Int?,
    val _editorOldOffset: Int?,

    val _colorLeft: _beatmapColor?,
    val _colorRight: _beatmapColor?,
    val _envColorLeft: _beatmapColor?,
    val _envColorRight: _beatmapColor?,
    val _envColorLeftBoost: _beatmapColor?,
    val _envColorRightBoost: _beatmapColor?,
    val _obstacleColor: _beatmapColor?,

    val _warnings: List<String>?,
    val _information: List<String>?,
    val _suggestions: List<String>?,
    val _requirements: ArrayList<String>?
)