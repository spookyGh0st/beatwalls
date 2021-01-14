package chart.info

data class Info(
    val _version: String,
    val _songName: String,
    val _songSubName: String,
    val _songAuthorName: String,
    val _levelAuthorName: String,

    val _beatsPerMinute: Double,
    val _songTimeOffset: Double,
    val _shuffle: Double,
    val _shufflePeriod: Double,

    val _previewStartTime: Double,
    val _previewDuration: Double,

    val _songFilename: String,
    val _coverImageFilename: String,

    val _environmentName: String,
    val _allDirectionsEnvironmentName: String?,
    val _difficultyBeatmapSets: List<_difficultyBeatmapSet>,

    val _customData: InfoCustomData?,
)

