package parameter

import com.google.gson.annotations.SerializedName

data class Command(
    @SerializedName("beat")
    val beatStartTime: Double,
    @SerializedName("command")
    val command: String
)