package assetFile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import parameter.Command
import structure.CustomWallStructure

/** A Combination of a WallList and A CommandList */
data class MixedStructure(
    @Expose
    @SerializedName("Name") val name : String,
    @Expose
    @SerializedName("Wall List") val customWallStructure: List<CustomWallStructure>,
    @Expose
    @SerializedName("Command List") val command: List<Command>
)