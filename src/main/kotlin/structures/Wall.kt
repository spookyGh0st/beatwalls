package structures

import com.google.gson.annotations.SerializedName
import song._obstacles
import java.time.Duration
import kotlin.math.abs


data class Wall(
    @SerializedName("duration") var duration : Double,
    @SerializedName("height") var height : Double,
    @SerializedName("startHeight") var startHeight : Double,
    @SerializedName("startRow") var startRow : Double,
    @SerializedName("width") var width : Double,
    @SerializedName("startTime") var startTime : Double
){
    override fun toString(): String {
        var text="Wall("
        text+="\n\t\t\t$duration,"
        text+="\n\t\t\t$height,"
        text+="\n\t\t\t$startHeight,"
        text+="\n\t\t\t$startRow,"
        text+="\n\t\t\t$width,"
        text+="\n\t\t\t$startTime"
        text+="\n\t\t\t)"
        return text
    }


    /**Changes the MyObstacle Type to an _obstacle Type */
    fun to_obstacle(): _obstacles {
        //first, so it adjust the startRow
        val tempWidth = calculateWidth()
        val tempLineIndex = calculateLineIndex()

        //other parameters
        val tempStartTime = startTime.coerceAtLeast(0.001)
        val tempType = type()

        val tempDuration = calculateDuration()

        return _obstacles(
            tempStartTime,
            tempLineIndex,
            tempType,
            tempDuration,
            tempWidth
        )
    }

    private fun calculateDuration(): Double{
        val tempDuration = if (duration<0.0001 && duration>-0.0001) 0.0001 else duration
        return tempDuration.coerceAtLeast(-3.0)
    }

    /**returns th _obstacle value of the width*/
    //TODO until negative width is allowed, this is needed. Once negative width is allowed, do this like lineIndex
    private fun calculateWidth():Int{
        //makes sure its not 0 width
        width = if(width > -0.001 && width < 0.001) 0.001 else width

        //calculate the width
        return if( width >= 0.0)
            (width* 1000 +1000).toInt()
        else{
            startRow += width
            (abs(width)*1000+1000).toInt()
        }
    }


    /**Return the _obstacle value of the startRow*/
    private fun calculateLineIndex():Int {
        val i = startRow +2
        return if( i >= 0.0)
            (i* 1000 +1000).toInt()
        else
            (i*1000-1000).toInt()
    }


    /**returns the type given heigt and startheight */
    private fun type():Int {

        val wallH= if(height>-0.01 && height<0.01) 0.01 else abs(height)

        val startH = if(height>0)startHeight else startHeight-height

        var tWallH:Int = (((1.0/3.0)*(wallH/(4.0/3.0)))*1000).toInt()
        tWallH = when {
            tWallH>4000 -> 4000
            tWallH<0 -> 0
            else -> tWallH
        }

        var tStartH:Int =  (250*(startH/(4.0/3.0))).toInt()
        tStartH = when {
            tStartH>999 -> 999
            tStartH<0 -> 0
            else -> tStartH
        }
        return  (tWallH * 1000 + tStartH+4001)
    }

    /**returns the mirrored obstacle */
    fun mirror()=
        Wall(duration, height, startHeight, -startRow, -width, startTime)

    /**overwrites the values, depending on the given parameters*/
    fun adjustParameters(parameters: Parameters){
        //Adding all the values
        var tempDuration = duration + parameters.duration
        val tempHeight = height + parameters.wallHeight
        val tempStartHeight = startHeight + parameters.wallStartHeight
        val tempStartRow = startRow + parameters.startRow
        val tempWidth = width + parameters.width
        var tempStartTime = startTime + parameters.startTime

        //adjusting the scale
        if(tempDuration>0)  tempDuration *= parameters.scale
        tempStartTime *= parameters.scale

        //adjust the values
        this.duration=tempDuration
        this.height=tempHeight
        this.startHeight=tempStartHeight
        this.startRow=tempStartRow
        this.width=tempWidth
        this.startTime=tempStartTime

    }

    fun adjustToBPM(baseBPM:Double,newBPM:Double,offset:Double){
        startTime *= (newBPM / baseBPM)
        startTime += offset
        if(duration > 0)
            duration*= (newBPM / baseBPM)
    }

}
fun main(){
}




