package structure.helperClasses

import beatwalls.GlobalConfig
import chart.difficulty._noteCustomData
import chart.difficulty._notes
import chart.difficulty._obstacleCustomData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import chart.difficulty._obstacles
import java.io.Serializable
import kotlin.math.max
import kotlin.math.min


data class SpookyWall(
    @Expose
    @SerializedName("x") var x: Double,
    @Expose
    @SerializedName("duration") var duration: Double,
    @Expose
    @SerializedName("width") var width: Double,
    @Expose
    @SerializedName("height") var height: Double,
    @Expose
    @SerializedName("y") var y: Double,
    @Expose
    @SerializedName("z") var z: Double,
    @Expose
    @SerializedName("color") var color: Color? = null,
    @Expose
    @SerializedName("rotationX") var rotationX: Double = 0.0,
    @Expose
    @SerializedName("rotationY") var rotationY: Double = 0.0,
    @Expose
    @SerializedName("rotationZ") var rotationZ: Double = 0.0,
    @Expose
    @SerializedName("localRotX") var localRotX: Double = 0.0,
    @Expose
    @SerializedName("localRotY") var localRotY: Double = 0.0,
    @Expose
    @SerializedName("localRotZ") var localRotZ: Double = 0.0,
    @Expose
    @SerializedName("track") var track: String? = null,
    @Expose
    @SerializedName("bomb") var bomb: Boolean = false,
    @Expose
    @SerializedName("noteJumpStartBeat") var noteJumpStartBeat: Double? = null,
    @Expose
    @SerializedName("noteJumpStartBeatOffset") var noteJumpStartBeatOffset: Double? = null
):Serializable{

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
            max(x, x + width),
            max(y, y + height),
            max(z, z + duration)
        )
    val trueLowestPoint
        get() = Point(
            min(x, x + width),
            min(y, y + height),
            z
        )
    /**Changes the MyObstacle Type to an _obstacle Type */
    fun to_obstacle(): _obstacles {
        val obs = toValidWall()
        val tempStartTime = obs.z.toFloat()
        val tempDuration = obs.duration.toFloat()

        val tempLineIndex = obs.calculateLineIndex()
        val tempWidth = obs.calculateWidth()

        val tempType = obs.type()

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
        val tempStartTime = obs.z.toFloat()
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
            track = cd?.track)

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
            t.x += t.width
            t.width *= -1
        }
        if(height < 0){
            t.y += t.height
            t.height *= -1
        }
        t.width = t.width.coerceAtLeast(minValue)
        t.height = t.height.coerceAtLeast(minValue)
        t.rotationY = t.rotationY % 360
        t.localRotX = t.localRotX % 360
        t.localRotY = t.localRotY % 360
        t.localRotZ = t.localRotZ % 360

        if (t.duration in -0.0001 .. 0.0001)
            t.duration = 0.0001
        t.duration = t.duration.coerceAtLeast(-2.0 * GlobalConfig.hjsDuration)
        t.z = t.z.coerceAtLeast(minValue)
        return t
    }

    /**returns th _obstacle value of the width*/
    private fun calculateWidth():Int =
            (width * 1000 +1000).toInt()

    /**Return the _obstacle value of the startRow*/
    private fun calculateLineIndex():Int {
        val i = x +2
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

        var tStartH: Int = (250 * (y * 0.6)).toInt()
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

        val tRotation= if (rotationX == 0.0 && rotationY == 0.0 && rotationZ == 0.0) null
            else listOf(rotationX,rotationY,rotationZ)
        val tLocalRotation = if (localRotX == 0.0 && localRotY == 0.0 && localRotZ == 0.0) null
            else listOf(localRotX,localRotY,localRotZ)

        return when {
            GlobalConfig.neValues -> _obstacleCustomData(
                _position = listOf(x, y),
                _scale = listOf(width, height),
                _color = cdColor,
                _localRotation = tLocalRotation,
                _rotation = tRotation,
                track = track,
                _noteJumpStartBeat = noteJumpStartBeat,
                _noteJumpStartBeatOffset = noteJumpStartBeatOffset
            )
            track != null || color != null || tRotation != null -> _obstacleCustomData(
                _color = cdColor,
                _rotation = tRotation,
                _localRotation = null,
                track = track
            )
            else ->
                null
        }
    }

}

internal const val minValue = 0.005
