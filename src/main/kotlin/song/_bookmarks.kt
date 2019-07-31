package song

import com.google.gson.annotations.SerializedName
import structures.DefaultParameters

data class _bookmarks (

    @SerializedName("_time") val _time : Double,
    @SerializedName("_name") val _name : String
){
    fun getCommandList(commandName:String):ArrayList<DefaultParameters>{
        val regex = """(?<=$commandName\s)(\w*)(\s(\w|\.|-)+)*""".toRegex()
        val list = arrayListOf<DefaultParameters>()
        regex.findAll(this._name).forEach {
            it.value.removePrefix("/bw ")
            list.add(DefaultParameters(it.value))
        }
        return list
    }
}

inline fun _bookmarks.forEachBSCommand(command:String, action: (ArrayList<String>)-> Unit) {
    val regex = """(?<=/$command\s)(\w*)(\s(\w|\.|-)+)*""".toRegex()
    regex.findAll(this._name).forEach {
        action(ArrayList(it.value.split(" ")))
    }
}
