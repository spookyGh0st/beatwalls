package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _noteCustomData(
    @SerializedName("scale") var scale: Double?,
    @SerializedName("sineMovement") var sineMovement: sineMovement?,
    @SerializedName("track") var track: String?
)

data class sineMovement(
    @SerializedName("amplitude") var amplitude: Double?,
    @SerializedName("frequency") var frequency: Double?,
    @SerializedName("track") var track: String?
)
