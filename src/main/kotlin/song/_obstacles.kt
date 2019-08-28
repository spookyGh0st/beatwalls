package song

import com.google.gson.annotations.SerializedName

data class _obstacles (

    @SerializedName("_time") var _time : Double,
    @SerializedName("_lineIndex") val _lineIndex : Int,
    @SerializedName("_type") val _type : Int,
    @SerializedName("_duration") var _duration : Double,
    @SerializedName("_width") val _width : Int
){

    fun adjustBPM(bpm: Double, newBpm: Double, timeOffset: Double){
        if(this._duration>0) {
            this._duration = this._duration / newBpm * bpm
            this._time = this._time / newBpm * bpm
            this._time += timeOffset
        }else{
            this._time += timeOffset
        }
    }
}
