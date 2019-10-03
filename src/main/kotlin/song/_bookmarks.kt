package song

import com.google.gson.annotations.SerializedName
import old_structures.OldParameters

data class _bookmarks (

    @SerializedName("_time") val _time : Double,
    @SerializedName("_name") val _name : String
){
    fun getCommandList(commandName:String):ArrayList<OldParameters>{
        val regex = """(?<=$commandName\s)(\w*)(\s(\w|\.|-)+)*""".toRegex()
        val list = arrayListOf<OldParameters>()
        regex.findAll(this._name).forEach {
            it.value.removePrefix("/bw ")
            list.add(OldParameters(commandText = it.value))
        }
        return list
    }
}

inline fun _bookmarks.forEachBSCommand(command:String, action: (ArrayList<String>)-> Unit) {
    TODO("DONT REMOVE /BW")
    val regex = """(?<=/$command\s)(\w*)(\s(\w|\.|-)+)*""".toRegex()
    regex.findAll(this._name).forEach {
        action(ArrayList(it.value.split(" ")))
    }
}
