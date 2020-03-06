package chart.song

import com.google.gson.annotations.SerializedName
import java.io.File

data class Info(

    @SerializedName("_version") val _version: String,
    @SerializedName("_songName") val _songName: String,
    @SerializedName("_songSubName") val _songSubName: String,
    @SerializedName("_songAuthorName") val _songAuthorName: String,
    @SerializedName("_levelAuthorName") val _levelAuthorName: String,

    @SerializedName("_beatsPerMinute") val _beatsPerMinute: Double,
    @SerializedName("_songTimeOffset") val _songTimeOffset: Double,
    @SerializedName("_shuffle") val _shuffle: Double,
    @SerializedName("_shufflePeriod") val _shufflePeriod: Double,

    @SerializedName("_previewStartTime") val _previewStartTime: Double,
    @SerializedName("_previewDuration") val _previewDuration: Double,

    @SerializedName("_songFilename") val _songFilename: String,
    @SerializedName("_coverImageFilename") val _coverImageFilename: String,

    @SerializedName("_environmentName") val _environmentName: String,

    @SerializedName("_customData") val InfoCustomData: InfoCustomData?,

    @SerializedName("_difficultyBeatmapSets") val _difficultyBeatmapSets: List<_difficultyBeatmapSets>
)

fun File.isSongInfo() =
    this.isDirectory && this.list()?.contains("info.dat") ?: false

