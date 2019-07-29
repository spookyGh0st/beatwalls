package com.github.spookyghost.beatwalls

import com.google.gson.annotations.SerializedName


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
}


/**Changes the MyObstacle Type to an _obstacle Type */
fun MyObstacle.to_obstacle():_obstacles {
    //first, so it adjust the startRow
    val tempWidth = getWidth()
    //other parameters
    val tempStartTime = startTime
    val tempLineIndex = startRow.toLineIndex()
    val tempType = type(height,startHeight)
    val tempDuration = duration
   return  _obstacles(tempStartTime,tempLineIndex,tempType,tempDuration,tempWidth)
}

/**overwrites the values if the parameter types are not null */
fun MyObstacle.adjust(a:ArrayList<Double>){
    //println("Parameters: $a")
    //println("OLD Obstacle $this")
    duration +=  a[0]
    height += a[1]
    startHeight += a[2]
    startRow += a[3]
    width += a[4]
    startTime += a[5]


    duration *= a[6]
    startTime *= a[6]
    //println("NEW Obstacle: $this")
}

/**returns the mirrored obstacle */
fun MyObstacle.mirror()=
    MyObstacle(duration,height,startHeight,-startRow-width,width,startTime)

/**Return the _obstacle value of the startRow*/
private fun Double.toLineIndex():Int {
    val i = this +2
    return if( i >= 0.0)
        (i* 1000 +1000).toInt()
    else
        (i*1000-1000).toInt()
}

/**returns th _obstacle value of the width*/
//TODO until negative width is allowed, this is kind of broken
private fun MyObstacle.getWidth():Int{
    return if( width >= 0.0)
        (width* 1000 +1000).toInt()
    else{
        startRow -= width
        (width*1000+1000).toInt()
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


