package interpreter

import chart.difficulty.*
import structure.bwElements.BwElement
import structure.bwElements.BwEvent
import structure.bwElements.BwNote
import structure.bwElements.BwObstacle
import math.*
import kotlin.math.*

@Suppress("SimplifyBooleanWithConstants")
class Translator(val structs: List<BwElement>, val bw: Beatwalls) {
    val obst = mutableListOf<_obstacles>()
    val notes = mutableListOf<_notes>()
    val events = mutableListOf<_events>()

    // The minimum height/width/ starttime for a ME wall to be visible
    private val meMinValue = 0.005
    fun translate(){
        for (e in structs){
            when(e){
                is BwObstacle -> addObstacle(e)
                is BwNote -> addBwNote(e)
                is BwEvent -> addBwEvent(e)
            }
        }
    }

    private fun addBwEvent(event: BwEvent) {
        TODO()
    }

    private fun addBwNote(note: BwNote) {
        TODO()
    }

    fun addObstacle(obs: BwObstacle){
        val obstacle = when(bw.options.modType){
            Options.ModType.ME -> meObstacle(obs)
            Options.ModType.NE -> neObstacle(obs)
        }
        obstacle._time += bw.options.halfJumpDuration
        obst.add(obstacle)
    }

    // jup this is ugly as hell. TOO BAD
    fun meObstacle(obs: BwObstacle): _obstacles {
        // The left down point of the obstacle
        val x = obs.translation.x - abs(obs.scale.x/2) + 2
        val lineIndex  = if (x >= 0.0)
            (x *1000 + 1000).toInt()
        else
            (x*1000 - 1000).toInt()

        var w = abs(obs.scale.x)
        w = w.coerceAtLeast(meMinValue)
        val width = (w * 1000 + 1000).toInt()

        var y = obs.translation.y - abs(obs.scale.y/2)
        y = (250 * (y * 0.6))
        val startHeight = when {
            y>999 -> 999
            y<0 -> 0
            else -> y.toInt()
        }

        var h = abs(obs.scale.y).coerceAtLeast(meMinValue)
        h = (((1.0 / 3.0) * (h * 0.6)) * 1000)
        val height = when {
            h>4000 -> 4000
            h<0 -> 0
            else -> h.toInt()
        }
        val type = (height * 1000 + startHeight+4001)

        var time = obs.beat + (obs.translation.z - obs.scale.z/2)
        time = time.coerceAtLeast(meMinValue)

        var duration = obs.scale.z
        if (duration in 0.0001 .. 0.0001)
            duration = 0.0001

        return _obstacles(
            _time = time,
            _duration = duration,
            _lineIndex = lineIndex,
            _type = type,
            _width = width,
            _obstacleCustomData = null
        )
    }

    fun minObstacle(obs: BwObstacle){
        obs.scale = Vec3(
            abs(obs.scale.x).coerceAtLeast(meMinValue),
            abs(obs.scale.y).coerceAtLeast(meMinValue),
            abs(obs.scale.z.coerceAtLeast(meMinValue*0.01)),
        )
    }

    fun neObstacle(obs: BwObstacle): _obstacles {
        obs.scale = Vec3(
            abs(obs.scale.x).coerceAtLeast(meMinValue),
            abs(obs.scale.y).coerceAtLeast(meMinValue),
            abs(obs.scale.z).coerceAtLeast(meMinValue*0.01),
        )

        rabbitCorrect(obs)
        // bwCorrect(obs)

        val nePos = obs.translation
        val neScale:List<Double>
        val neLocalRot = neRotation(obs.rotation)
        neScale = if (neLocalRot == null || (neLocalRot.getOrNull(0) == 0.0 && neLocalRot.getOrNull(1) ==0.0))
            obs.scale.toVec2().toList()
        else
            obs.scale.toList()

        val beat = obs.beat + nePos.z


        return _obstacles(
            _time = beat,
            _lineIndex = 0,
            _type = 0,
            _duration = obs.duration ?: obs.scale.z,
            _width = 0,
            _obstacleCustomData = _obstacleCustomData(
                _position = nePos.toVec2().toList(),
                _scale = neScale,
                _color = obs.color?.toList(),
                _rotation = neRotation(obs.globalRotation),
                _localRotation = neLocalRot,
                _track = obs.track,
                _noteJumpMovementSpeed = obs.noteJumpMovementSpeed,
                _noteJumpStartBeatOffset = obs.noteJumpStartBeatOffset,
                _fake = obs.fake.takeIf { it == true },
                _interactable = obs.interactable.takeIf { it == false },
                _disableNoteGravity = (!obs.gravity).takeIf { it == true },
            )
        )
    }
    // my own try on correcting the pos
    fun bwCorrect(obs: BwObstacle) {
        val transMat = translationMat4(obs.translation)
        val scaleMat = scalingMat4(obs.scale)

        val bwRot = obs.rotation.toRotationMat4()
        val origin = Vec3(0.0, 0.5, -0.5) * obs.scale
        val tToOrigin = translationMat4(origin)
        val neRot = obs.rotation.inverse().toRotationMat4()
        val tFromOrigin = translationMat4(-1.0 * origin)
        val correction = bwRot * tToOrigin * neRot * tFromOrigin
        val posCorrection = Vec4(correction.x.w, correction.y.w, correction.z.w, correction.w.w)
        var pos = Vec4(-0.5,-0.5,-0.5,1.0)
        pos = transMat *scaleMat *  pos  + posCorrection
        obs.translation = pos.toVec3()
    }

    // correction of ne obst ~~based~~copied from rabbit's loader
    val ingameObstacleScale = Vec3(1.02, 1.0, 1.0)
    fun rabbitCorrect(obs: BwObstacle) {
        val globalOffset = Vec3(-0.5 * obs.scale.x,0.0,0.0)
        var offset = Vec3(0.0,-0.5*obs.scale.y, -0.5*obs.scale.z)
        offset = obs.rotation.rotate(offset)
        val pos = obs.translation + offset + globalOffset
        obs.translation = pos
        obs.scale *= ingameObstacleScale
    }

    private fun neRotation(quat: Quaternion): List<Double>? {
        var vec = quat.toEuler()
        return if (vec.x == 0.0 && vec.y == 0.0 && vec.z == 0.0)
            null
        else{
            vec =  (1.0/(2* PI) * 360) * vec
            listOf(vec.x, vec.y, vec.z)
        }
    }
}
