package parameter

import com.google.gson.annotations.SerializedName

data class Command(
    @SerializedName("beat")
    val beatStartTime: Double,
    @SerializedName("structureList")
    val command: String
)