package chart.song

import com.google.gson.annotations.SerializedName

data class InfoCustomData(

    @SerializedName("_contributors") val _contributors: List<_contributors>,
    @SerializedName("_customEnvironment") val _customEnvironment: String,
    @SerializedName("_customEnvironmentHash") val _customEnvironmentHash: String
)