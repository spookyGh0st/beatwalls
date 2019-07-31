package structures

import com.google.gson.annotations.SerializedName
import song._obstacles


data class MyObstacle(
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
        val tempWidth = getWidth()
        val tempLineIndex = getLineIndex()

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
    private fun getWidth():Int{
        return if( width >= 0.0)
            (width* 1000 +1000).toInt()
        else{
            startRow -= width
            (width*1000+1000).toInt()
        }
    }


    /**Return the _obstacle value of the startRow*/
    private fun getLineIndex():Int {
        val i = startRow +2
        return if( i >= 0.0)
            (i* 1000 +1000).toInt()
        else
            (i*1000-1000).toInt()
    }


    /**returns the mirrored obstacle */
    fun mirror()=
        MyObstacle(duration, height, startHeight, -startRow - width, width, startTime)


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


    fun adjustParameters(defaultParameters: DefaultParameters){

        //Adding all the values
        duration += defaultParameters.duration
        height += defaultParameters.wallHeight
        startHeight += defaultParameters.wallStartHeight
        startRow += defaultParameters.startRow
        width  += defaultParameters.width
        startTime += defaultParameters.startTime

        //adjusting the scale
        duration *= defaultParameters.scale
        startTime *= defaultParameters.scale
    }
}


/**returns the type given heigt and startheight */
private fun type(wallH: Double, startH: Double):Int {
    val tWallH:Int = when {
        wallH>6 -> 4000
        wallH<0 -> 0
        else -> (((1.0/3.0)*wallH)*1000).toInt()
    }
    val tStartH:Int = when{
        startH>=8 -> 999
        startH<0 -> 0
        else -> (250*startH).toInt()
    }
    return  (tWallH * 1000 + tStartH+4001)
}


