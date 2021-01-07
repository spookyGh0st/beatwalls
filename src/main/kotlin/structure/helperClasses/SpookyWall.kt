package structure.helperClasses

import beatwalls.GlobalConfig
import chart.difficulty._noteCustomData
import chart.difficulty._notes
import chart.difficulty._obstacleCustomData
import chart.difficulty._obstacles
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.math.max
import kotlin.math.min

data class SpookyWall(
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
    @SerializedName("color") var color: Color? = null,
    @Expose
    @SerializedName("rotation") var rotation: List<Double> = arrayListOf(0.0, 0.0, 0.0),
    @Expose
    @SerializedName("localRotation") var localRotation: List<Double> = arrayListOf(0.0, 0.0, 0.0),
    @Expose
    @SerializedName("track") var track: String? = null,
    @Expose
    @SerializedName("bomb") var bomb: Boolean = false,
    @Expose
    @SerializedName("noteJumpStartBeat") var noteJumpStartBeat: Double? = null,
    @Expose
    @SerializedName("noteJumpStartBeatOffset") var noteJumpStartBeatOffset: Double? = null
// TODO add NE elements
): BwElement{

    constructor(
        startRow: Int=0,
        duration: Int=0,
        width: Int=0,
        height: Int=0,
        startHeight: Int=0,
        startTime: Int=0,
        color: Color?=null
    ):this(
        startRow.toDouble(),
        duration.toDouble(),
        width.toDouble(),
        height.toDouble(),
        startHeight.toDouble(),
        startTime.toDouble(),
        color)
    val trueMaxPoint
        get() = Point(
            max(startRow, startRow + width),
            max(startHeight, startHeight + height),
            max(startTime, startTime + duration)
        )
    val trueMinPoint
        get() = Point(
            min(startRow, startRow + width),
            min(startHeight, startHeight + height),
            startTime
        )
    /**Changes the MyObstacle Type to an _obstacle Type */
    fun to_obstacle(): _obstacles {
        val obs = toValidWall()
        val tempStartTime = obs.startTime.toFloat()
        val tempDuration = obs.duration.toFloat()

        var tempLineIndex = obs.calculateLineIndex()
        var tempWidth = obs.calculateWidth()


        var tempType = obs.type()

        if(GlobalConfig.neValues && !bomb){
            tempType = 0
            tempWidth = 0
            tempLineIndex = 0
        }

        val customData: _obstacleCustomData? = obs.customData()

        return _obstacles(
            tempStartTime,
            tempLineIndex,
            tempType,
            tempDuration,
            tempWidth,
            customData
        )
    }
    fun toBomb(): _notes {
        val obs = toValidWall()
        val tempStartTime = obs.startTime.toFloat()
        val tempDuration = obs.duration.toFloat()

        val tempLineIndex = obs.calculateLineIndex()
        val tempWidth = obs.calculateWidth()

        val tempType = obs.type()

        val cd: _obstacleCustomData? = obs.customData()!!
        val x = (cd?._position?.get(0) ?: 0.0) + (cd?._scale?.get(0) ?: 0.0) /2
        val y = (cd?._position?.get(1) ?: 0.0) + (cd?._scale?.get(1) ?: 0.0) /2
        val ncd = _noteCustomData(
            _position = listOf(x,y),
            _color = cd?._color,
            _rotation = cd?._rotation,
            track = cd?._track)

        return _notes(
            _time = tempStartTime.toDouble(),
            _lineIndex = 0,
            _lineLayer  = 0,
            _type = 3,
            _cutDirection = 1,
            _noteCustomData = ncd
        )
    }

    fun toValidWall(): SpookyWall {
        val t = this.copy()
        if(t.width< 0){
            t.startRow += t.width
            t.width *= -1
        }
        if(height < 0){
            t.startHeight += t.height
            t.height *= -1
        }
        t.width = t.width.coerceAtLeast(minValue)
        t.height = t.height.coerceAtLeast(minValue)

        t.localRotation = t.localRotation.map { it % 360 }
        t.rotation = t.rotation.map { it % 360 }

        if (t.duration in -0.0001 .. 0.0001)
            t.duration = 0.0001
        t.duration = t.duration.coerceAtLeast(-2.0 * GlobalConfig.hjsDuration)
        t.startTime = t.startTime.coerceAtLeast(minValue)
        return t
    }

    /**returns th _obstacle value of the width*/
    private fun calculateWidth():Int =
            (width * 1000 +1000).toInt()

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
        var tWallH: Int = (((1.0 / 3.0) * (height * 0.6)) * 1000).toInt()
        tWallH = when {
            tWallH>4000 -> 4000
            tWallH<0 -> 0
            else -> tWallH
        }

        var tStartH: Int = (250 * (startHeight * 0.6)).toInt()
        tStartH = when {
            tStartH>999 -> 999
            tStartH<0 -> 0
            else -> tStartH
        }
        return  (tWallH * 1000 + tStartH+4001)
    }

    private fun customData(): _obstacleCustomData? {
        val cdColor =when {
            color != null -> listOf(color!!.red, color!!.green, color!!.blue)
            else -> null }

        val tRotation = if (rotation.all { it == 0.0 }) null else rotation.toList()

        val tLocalRotation = if (localRotation.all { it == 0.0 }) null else localRotation.toList()

        return when {
            GlobalConfig.neValues -> _obstacleCustomData(
                _position = listOf(startRow, startHeight),
                _scale = listOf(width, height),
                _color = cdColor,
                _localRotation = tLocalRotation,
                _rotation = tRotation,
                _track =  track,
                _noteJumpMovementSpeed = noteJumpStartBeat,
                _noteJumpStartBeatOffset = noteJumpStartBeatOffset
            )
            track != null || color != null || tRotation != null -> _obstacleCustomData(
                _color = cdColor,
                _rotation = tRotation,
                _localRotation = null,
                _track = track
            )
            else ->
                null
        }
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

internal const val minValue = 0.005



