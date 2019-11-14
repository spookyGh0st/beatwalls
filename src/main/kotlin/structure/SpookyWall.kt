package structure

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import difficulty._obstacles
import java.io.Serializable
import kotlin.math.abs
import kotlin.random.Random


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
    @SerializedName("startTime") var startTime: Double
):Serializable{

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
        val tempDuration = if (duration <0.0001 && duration >-0.0001) 0.0001 else duration
        return tempDuration.coerceAtLeast(-3.0)
    }

    /**returns th _obstacle value of the width*/
    //TODO until negative width is allowed, this is needed. Once negative width is allowed, do this like lineIndex
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

    fun fuckUp() =
        SpookyWall(ra(startRow), ra(duration), ra(width), ra(height), ra(startHeight), ra(startTime))

    fun ground(h:Double) =
        SpookyWall(startRow, duration, width, height + (startHeight - h), h, startTime)

    fun sky(h:Double) =
        SpookyWall(startRow, duration, width, (h - startHeight), startHeight, startTime)

    fun extend(a:Double) =
        SpookyWall(startRow, a - startTime, width, height, startHeight, startTime)

    private fun ra(i:Double) = i+Random.nextDouble(-0.2 ,0.2)
    fun fast() = this.copy(duration= -2.0)
    fun hyper() = this.copy(duration = -3.0)

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


    fun scale(s:Double) = this.copy(
        duration= if(duration>0) duration*s else duration,
        startTime = startTime*s)
    fun verticalScale(s:Double) = this.copy(
        startRow= startRow*s,
        height = height*s,
        startHeight= startHeight*s
    )
    fun extendX(a:Double) = this.copy(
        width = a-startRow
    )
    fun extendY(a:Double) = this.copy(
        height = a-startHeight
    )
    fun extendZ(a: Double) = this.copy(
        duration = a - startTime
    )
    fun time(a:Double) = this.copy(
        startTime = startTime+a
    )
    fun repeat(a: Int, o: Double = 1.0): MutableList<SpookyWall> {
        val list = mutableListOf<SpookyWall>()
        for (i in 0 until a){
            list.add(this.copy(startTime= this.startTime + i*o))
        }
        return list
    }
    fun split(a: Int): MutableList<SpookyWall> {
        val list = mutableListOf<SpookyWall>()
        for (i in 0 until a){
            if (this.height>this.width)
                list.add(this.copy(startHeight = startHeight+height* i / a,height = 1.0/a))
            else
                list.add(this.copy(startRow = startRow + width * i / a,width = 1.0/a))
        }
        return list
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

