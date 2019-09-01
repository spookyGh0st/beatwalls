package structures

import com.google.gson.annotations.SerializedName
import song._obstacles


data class Wall(
    @SerializedName("duration") var duration : Double,
    @SerializedName("height") var height : Double,
    @SerializedName("startHeight") var startHeight : Double,
    @SerializedName("startRow") var startRow : Double,
    @SerializedName("width") var width : Double,
    @SerializedName("startTime") var startTime : Double
){
    override fun toString(): String {
        return "duration: $duration height: $height startHeight: $startHeight startRow: $startRow width: $width StartTime: $startTime"
    }


    /**Changes the MyObstacle Type to an _obstacle Type */
    fun to_obstacle(): _obstacles {
        //first, so it adjust the startRow
        val tempWidth = calculateWidth()
        val tempLineIndex = calculateLineIndex()

        //other parameters
        val tempStartTime = startTime
        val tempType = type(height, startHeight)
        val tempDuration = duration
        return _obstacles(
            tempStartTime,
            tempLineIndex,
            tempType,
            tempDuration,
            tempWidth
        )
    }


    /**returns th _obstacle value of the width*/
    //TODO until negative width is allowed, this is needed. Once negative width is allowed, do this like lineIndex
    private fun calculateWidth():Int{
        return if( width >= 0.0)
            (width* 1000 +1000).toInt()
        else{
            startRow -= width
            (width*1000+1000).toInt()
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


    /**returns the mirrored obstacle */
    fun mirror()=
        Wall(duration, height, startHeight, -startRow - width, width, startTime)


    /**overwrites the values if the parameter types are not null */
    fun adjust(a:ArrayList<Double>){
        duration +=  a[2]
        height += a[3]
        startHeight += a[4]
        startRow += a[5]
        width += a[6]
        startTime += a[7]
        duration *= a[0]
        startTime *= a[0]
    }


    /**overwrites the values, depending on the given parameters*/
    fun adjustParameters(parameters: Parameters):Wall{
        //Adding all the values
        var tempDuration = duration + parameters.duration
        val tempHeight = height + parameters.wallHeight
        val tempStartHeight = startHeight + parameters.wallStartHeight
        val tempStartRow = startRow + parameters.startRow
        val tempWidth = width + parameters.width
        var tempStartTime = startTime + parameters.startTime

        //adjusting the scale
        if(tempDuration>0)
            tempDuration *= parameters.scale
        tempStartTime *= parameters.scale

        return Wall(tempDuration,tempHeight,tempStartHeight,tempStartRow,tempWidth,tempStartTime)
    }


    /**returns the type given heigt and startheight */
    private fun type(wallH: Double, startH: Double):Int {

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
}
fun main(){
}




