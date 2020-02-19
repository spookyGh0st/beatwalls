package structure.helperClasses

import chart.difficulty._obstacleCustomData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import chart.difficulty._obstacles
import java.io.Serializable
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


data class SpookyWall(
    @Expose
    @SerializedName("startRow") var startRow: Double,
    @Expose
    @SerializedName("duration") var duration: Double,
    @Expose
    @SerializedName("width") var width: Double,
    @Expose
    @SerializedName("height") var height: Double,
    @Expose
    @SerializedName("startHeight") var startHeight: Double,
    @Expose
    @SerializedName("startTime") var startTime: Double,
    @Expose
    @SerializedName("color") var color: Color? = null
):Serializable{
    val trueMaxPoint
        get() = Point(
            max(startRow, startRow + width),
            max(startHeight, startHeight + height),
            max(startTime, startTime + duration)
        )
    val trueLowestPoint
        get() = Point(
            min(startRow, startRow + width),
            min(startHeight, startHeight + height),
            startTime
        )
    /**Changes the MyObstacle Type to an _obstacle Type */
    fun to_obstacle(hjd: Double): _obstacles {
        //first, so it adjust the startRow
        val tempWidth = calculateWidth()
        val tempLineIndex = calculateLineIndex()

        //other parameters
        val tempStartTime = startTime.coerceAtLeast(0.001).toFloat()
        val tempType = type()

        val tempDuration = calculateDuration(hjd)

        val customData: _obstacleCustomData = customData()

        return _obstacles(
            tempStartTime,
            tempLineIndex,
            tempType,
            tempDuration,
            tempWidth,
            customData
        )
    }

    private fun calculateDuration(hjd: Double): Float {
        val tempDuration = if (duration < 0.0001 && duration > -0.0001) 0.0001 else duration
        return tempDuration.coerceAtLeast(-1.5 * hjd).toFloat()
    }

    /**returns th _obstacle value of the width*/
    private fun calculateWidth():Int{
        //makes sure its not 0 width
        width = if(width > -0.01 && width < 0.01) 0.01 else width

        //calculate the width
        return if( width >= 0.0)
            (width * 1000 +1000).toInt()
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

        val wallH= if(height >-0.01 && height <0.01) 0.01 else abs(height)

        val startH = if(height >0) startHeight else startHeight + height

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

    private fun customData()= when {
        color != null -> _obstacleCustomData(color!!.red, color!!.green, color!!.blue)
        else -> _obstacleCustomData(null, null, null)
    }

    /**returns the mirrored obstacle */
    fun mirror() {
        startRow = -startRow
        width = -width
    }
    fun verticalMirror() {
        startHeight = 2 + (2-startHeight)
        height = -height
    }
    fun pointMirror() {
        mirror()
        verticalMirror()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpookyWall) return false
        if (startRow != other.startRow) return false
        if (duration != other.duration) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (startHeight != other.startHeight) return false
        if (startTime != other.startTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startRow.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + startHeight.hashCode()
        result = 31 * result + startTime.hashCode()
        return result
    }
}

