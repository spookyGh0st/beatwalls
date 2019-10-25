package assetFile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import structure.Wall
import structure.WallStructure

/** A Combination of a WallList and A CommandList */
data class SavedWallStructure(
    @Expose
    @SerializedName("Name") val name : String,
    @Expose
    @SerializedName("Wall List") val customWallStructure: List<Wall>,
    @Expose
    @SerializedName("Structure List List") val command: List<WallStructure>
){
    fun toCustomWallStructure(): WallStructure{
        TODO()
    }
}