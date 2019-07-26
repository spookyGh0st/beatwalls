package com.github.spookyghost.beatwalls

import com.google.gson.annotations.SerializedName


data class MyObstacle(
    @SerializedName("duration") var duration : Double,
    @SerializedName("height") var height : Double,
    @SerializedName("startHeight") var startHeight : Double,
    @SerializedName("startRow") var startRow : Double,
    @SerializedName("width") var width : Double,
    @SerializedName("startTime") var startTime : Double

)

/**Changes the MyObstacle Type to an _obstacle Type */
fun MyObstacle.to_obstacle():_obstacles {
    val tempWidth = getWidth()
   return  _obstacles(startTime, startRow.toLineIndex(),type(height,startHeight),  duration, tempWidth)
}

/**overwrites the values if the parameter types are not null */
fun MyObstacle.adjust(a:ArrayList<Double>){
    println("duration $duration")
    duration +=  a[0]
    height += a[1]
    startHeight += a[2]
    startRow += a[3]
    width += a[4]
}

/**returns the mirrored obstacle */
fun MyObstacle.mirror()=
    MyObstacle(duration,height,startHeight,-startRow-width,width,startTime)

/**Return the _obstacle value of the startRow*/
private fun Double.toLineIndex():Int {
    val i = this +2
    println(i)
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
private fun type(wallH: Double, startH: Double) = (wallH * 1000 + startH+4001).toInt()



