package assetFile

import com.google.gson.annotations.SerializedName
import structures.CustomWallStructure
import structures.Parameter

/** A Combination of a WallList and A CommandList */
data class MixedStructure(
    @SerializedName("Name") val name : String,
    @SerializedName("Wall List") val customWallStructure: List<CustomWallStructure>,
    @SerializedName("Command List") val command: List<Parameter.Command>
)