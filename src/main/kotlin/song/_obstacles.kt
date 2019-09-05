package song

import com.google.gson.annotations.SerializedName
import structures.Wall

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

    fun toWall():Wall{
        val startTime = this._time
        val duration = this._duration
        val startRow =  getLineIndex(_lineIndex)
        val height = getHeight(_type)
        val startHeight = getStartHeight(_type)
        val width = getWidth(_width)

        return Wall(duration,height,startHeight,startRow, width,startTime)
    }

    private fun getHeight(i:Int):Double{
        if(i < 500){
            TODO()
        }
        TODO()
    }
    private fun getStartHeight(i:Int):Double{
        val a:Double
        if(i<500) return 0.0
        TODO()
    }

    private fun getLineIndex(i:Int):Double =
        if(i>=0)
            (i.toDouble() - 1000)/1000.0 -2.0
        else
            (i.toDouble() +1000)/1000 - 2.0

    private fun getWidth(i:Int):Double =
        (i.toDouble() -1000 / 1000)
}
