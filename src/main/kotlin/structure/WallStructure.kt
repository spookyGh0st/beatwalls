package structure

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
interface WallStructure {
/**

  ___                            _              _
 |_ _|_ __ ___  _ __   ___  _ __| |_ __ _ _ __ | |_
  | || '_ ` _ \| '_ \ / _ \| '__| __/ _` | '_ \| __|
  | || | | | | | |_) | (_) | |  | || (_| | | | | |_
 |___|_| |_| |_| .__/ \___/|_|   \__\__,_|_| |_|\__|
               |_|

* The data Class CustomWallStructures will hold only fixed Values given by the AssetFile. You are looking for the SpecialWallStructure File
* */
    val name: String
    val wallList: ArrayList<Wall>

    fun walls(): List<Wall> {
        return wallList.map { it.copy() }
    }
}

/** CustomWallStructure, used to safe static Walls */

data class CustomWallStructure(
    @SerializedName("Name")
    @Expose
    override val name:String,
    @SerializedName("Wall List")
    @Expose
    override var wallList: ArrayList<Wall>
) :WallStructure
