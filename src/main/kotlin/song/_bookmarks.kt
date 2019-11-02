package song

import com.google.gson.annotations.SerializedName
import old_structures.OldParameters
import parameter.Command

data class _bookmarks (

    @SerializedName("_time") val _time : Double,
    @SerializedName("_name") val _name : String
){
    fun getCommandList(commandName:String): ArrayList<String> {
        val regex = """(?<=/$commandName )(.*)(?=\s/)""".toRegex()
        val list = arrayListOf<String>()
        val string = this._name + " /"
        regex.findAll(string).forEach {
            val b = it.value
            val txt = it.value.removePrefix(commandName).removePrefix(" ")
            list.add(txt)
        }
        return list
    }
    fun toCommandList(commandPreFix: String): List<Command> {
        val l = getCommandList(commandPreFix)
        return l.map { Command(_time,it) }
    }
}

