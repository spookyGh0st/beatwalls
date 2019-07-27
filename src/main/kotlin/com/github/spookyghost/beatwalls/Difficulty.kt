package com.github.spookyghost.beatwalls
import com.google.gson.annotations.SerializedName

data class Difficulty (

    @SerializedName("_version") var _version : String,
    @SerializedName("_BPMChanges") val _BPMChanges : ArrayList<_BPMChanges>,
    @SerializedName("_bookmarks") val _bookmarks : ArrayList<_bookmarks>,
    @SerializedName("_events") var _events : ArrayList<_events>,
    @SerializedName("_notes") var _notes : ArrayList<_notes>,
    @SerializedName("_obstacles") var _obstacles : ArrayList<_obstacles>
)

data class _BPMChanges (

    @SerializedName("_BPM") val _BPM : Double,
    @SerializedName("_time") val _time : Double,
    @SerializedName("_beatsPerBar") val _beatsPerBar : Int,
    @SerializedName("_metronomeOffset") val _metronomeOffset : Int
)

data class _bookmarks (

    @SerializedName("_time") val _time : Double,
    @SerializedName("_name") val _name : String
)

data class _events (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_type") var _type : Int,
    @SerializedName("_value") val _value : Int
)

data class _notes (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_lineIndex") val _lineIndex : Int,
    @SerializedName("_lineLayer") val _lineLayer : Int,
    @SerializedName("_type") val _type : Int,
    @SerializedName("_cutDirection") val _cutDirection : Int
)

data class _obstacles (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_lineIndex") val _lineIndex : Int,
    @SerializedName("_type") val _type : Int,
    @SerializedName("_duration") var _duration : Double,
    @SerializedName("_width") val _width : Int
)

fun Difficulty.containsCommand(string: String) = this._bookmarks.any { it._name.contains("/$string") }

fun Difficulty.createWalls(bpm:Double, spawnDistance:Int){
    _bookmarks.forEach { it ->

        val tempBpm =_BPMChanges.findLast{ bpmChanges -> bpmChanges._time <= it._time }?._BPM ?: bpm
        println("bookmarktim: ${it._time} last bpm change time: ")

        val list = arrayListOf<_obstacles>()

        val timeOffset = it._time

        it.forEachBSCommand("bw"){
            println(it)
            list.addAll(WallStructureManager.get(it))
        }

        println("tempbpm: $tempBpm bpm: $bpm ")

        list.forEach {
            it.adjust(bpm, tempBpm,timeOffset, spawnDistance)
            _obstacles.add(it)
        }
    }
}



fun _obstacles.adjust(bpm:Double,newBpm:Double, timeOffset:Double, spawnDistance: Int){
    if(this._duration < 0) this._time += spawnDistance //todo broken right now. maybe adjust to BPM?
    this._duration = this._duration /newBpm*bpm
    this._time =this._time/newBpm *bpm + timeOffset
}




inline fun _bookmarks.forEachBSCommand(command:String, action: (ArrayList<String>)-> Unit) {
    val regex = """(?<=/$command\s)(\w*)(\s(\w|\.|-)+)*""".toRegex()
    regex.findAll(this._name).forEach {
        action(ArrayList(it.value.split(" ")))
    }
}

