package com.github.spookyghost.beatwalls
import com.google.gson.annotations.SerializedName
import java.io.File

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

        val tempBpm =_BPMChanges.findLast{ bpmChanges -> bpmChanges._time < it._time }?._BPM ?: bpm

        val bpmMultiplier =  bpm / tempBpm

        val list = arrayListOf<_obstacles>()

        val offset = it._time

        it.forEachCommand("bw"){
            println(it)

            val parameter = it.split(" ")

            when (parameter.first().toLowerCase()) {

                //TODO make that better, auto set command, find a better way for the options. maybe nullable?
                //TODO find a way to set default parameters, when no parameters are given
            }
        }

        list.forEach {
            it.adjust(bpmMultiplier,offset)
            _obstacles.add(it)
        }
    }
}

fun _obstacles.adjust(bpmMultiplier:Double,offset:Double){
    this._duration *=bpmMultiplier
    this._time =this._time*bpmMultiplier + offset
}

inline fun _bookmarks.forEachCommand(command:String, action: (String)-> Unit) {
    val regex = """(?<=/$command\s)(\w*)(\s(\w|\.)+)*""".toRegex()
    regex.findAll(this._name).forEach {
        action(it.value)
    }
}

fun File.isDifficulty() =
    this.isFile && (this.name == "Easy.dat" || this.name == "Normal.dat" || this.name == "Hard.dat" || this.name == "Expert.dat" || this.name == "ExpertPlus.dat" || this.name == "Test.dat") //TODO remove test.dat

fun File.isMap() =
    this.isDirectory && this.list()?.contains("info.dat")?:false
