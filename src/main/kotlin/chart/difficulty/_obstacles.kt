@file:Suppress("ClassName")

package chart.difficulty

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import interpreter.property.functions.getOrZero
import structure.helperClasses.Color
import structure.helperClasses.SpookyWall

data class _obstacles (
    @SerializedName("_time") var _time : Float,
    @SerializedName("_lineIndex") val _lineIndex : Int,
    @SerializedName("_type") val _type : Int,
    @SerializedName("_duration") var _duration : Float,
    @SerializedName("_width") val _width : Int,
    @SerializedName("_customData") val _obstacleCustomData : _obstacleCustomData?
)

fun _obstacles.toSpookyWall(): SpookyWall = SpookyWall(
    x = _obstacleCustomData?._position?.getOrZero(0)?: 0.0,
    y = _obstacleCustomData?._position?.getOrZero(1)?: 0.0,
    z = _obstacleCustomData?._position?.getOrZero(2)?: 0.0,
    width = _obstacleCustomData?._scale?.getOrZero(0)?: 0.0,
    height = _obstacleCustomData?._scale?.getOrZero(1)?: 0.0,
    duration = _obstacleCustomData?._scale?.getOrZero(2)?: 0.0,
    color = if ( _obstacleCustomData?._color == null) null else Color(_obstacleCustomData._color),
    rotationX = _obstacleCustomData?._rotation?.getOrZero(0)?: 0.0,
    rotationY = _obstacleCustomData?._rotation?.getOrZero(1)?: 0.0,
    rotationZ = _obstacleCustomData?._rotation?.getOrZero(2)?: 0.0,
    localRotX = _obstacleCustomData?._localRotation?.getOrZero(0)?: 0.0,
    localRotY = _obstacleCustomData?._localRotation?.getOrZero(1)?: 0.0,
    localRotZ = _obstacleCustomData?._localRotation?.getOrZero(2)?: 0.0,
    noteJumpStartBeat = _obstacleCustomData?._noteJumpStartBeat,
    noteJumpStartBeatOffset = _obstacleCustomData?._noteJumpStartBeatOffset,
    bomb = false
)

