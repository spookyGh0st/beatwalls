package assetFile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import structure.CustomWallStructure
import structure.Wall
import structure.WallStructure

/** A Combination of a WallList and A CommandList */
data class SavedWallStructure(
    @Expose
    @SerializedName("Name") val name : String,
    @Expose
    @SerializedName("Wall List") val wallList: List<Wall> = listOf(),
    @Expose
    @SerializedName("Structure List") val structureList: List<WallStructure> = listOf()
){
    fun toCustomWallStructure(): CustomWallStructure{
        val w = CustomWallStructure(name)
        w.add(wallList)
        w.add(structureList.flatMap { it.walls() })
        return w
    }
}