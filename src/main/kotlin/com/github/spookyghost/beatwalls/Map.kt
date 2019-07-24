package com.github.spookyghost.beatwalls

import com.google.gson.annotations.SerializedName
import java.io.File
import java.nio.file.Paths

class Map(file:File) {
    val info: Info = readInfo(Paths.get(file.toString(),"info.dat").toFile())
    val difficultyList =  arrayListOf<Difficulty>()
    init {
        for(i in info._difficultyBeatmapSets){
            for(j in i._difficultyBeatmaps){
                val diffPath = Paths.get(file.toString(),(j._beatmapFilename))
                difficultyList.add(readDifficulty(File(diffPath.toUri())))
            }
        }
    }
}



data class Info (

    @SerializedName("_version") val _version : String,
    @SerializedName("_songName") val _songName : String,
    @SerializedName("_songSubName") val _songSubName : String,
    @SerializedName("_songAuthorName") val _songAuthorName : String,
    @SerializedName("_levelAuthorName") val _levelAuthorName : String,

    @SerializedName("_beatsPerMinute") val _beatsPerMinute : Double,
    @SerializedName("_songTimeOffset") val _songTimeOffset : Double,
    @SerializedName("_shuffle") val _shuffle : Double,
    @SerializedName("_shufflePeriod") val _shufflePeriod : Double,

    @SerializedName("_previewStartTime") val _previewStartTime : Double,
    @SerializedName("_previewDuration") val _previewDuration : Double,

    @SerializedName("_songFilename") val _songFilename : String,
    @SerializedName("_coverImageFilename") val _coverImageFilename : String,

    @SerializedName("_environmentName") val _environmentName : String,

    @SerializedName("_customData") val _customData : _customData,

    @SerializedName("_difficultyBeatmapSets") val _difficultyBeatmapSets : List<_difficultyBeatmapSets>
)

data class _customData (

    @SerializedName("_difficultyLabel") val _difficultyLabel : String,

    @SerializedName("_editorOffset") val _editorOffset : Int,
    @SerializedName("_editorOldOffset") val _editorOldOffset : Int,

    //TODO ADD COLOR

    @SerializedName("_warnings") val _warnings : List<String>,
    @SerializedName("_information") val _information : List<String>,
    @SerializedName("_suggestions") val _suggestions : List<String>,
    @SerializedName("_requirements") val _requirements : List<String>
)


data class _difficultyBeatmaps (

    @SerializedName("_difficulty") val _difficulty : String,
    @SerializedName("_difficultyRank") val _difficultyRank : Int,
    @SerializedName("_beatmapFilename") val _beatmapFilename : String,

    @SerializedName("_noteJumpMovementSpeed") val _noteJumpMovementSpeed : Double,
    @SerializedName("_noteJumpStartBeatOffset") val _noteJumpStartBeatOffset : Int,

    @SerializedName("_customData") val _customData : _customData

)

data class _difficultyBeatmapSets (

    @SerializedName("_beatmapCharacteristicName") val _beatmapCharacteristicName : String,
    @SerializedName("_difficultyBeatmaps") val _difficultyBeatmaps : List<_difficultyBeatmaps>
)


//TODO ADD MAPPING EXTENSION AUTOMATIC