package interpreter

import chart.difficulty.*
import structure.bwElements.BwElement
import structure.bwElements.BwEvent
import structure.bwElements.BwNote
import structure.bwElements.BwObstacle
import structure.math.Mat3
import structure.math.Vec3
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
        val x = obs.position.x - abs(obs.scale.x/2) + 2
        val lineIndex  = if (x >= 0.0)
            (x *1000 + 1000).toInt()
        else
            (x*1000 - 1000).toInt()

        var w = abs(obs.scale.x)
        w = w.coerceAtLeast(meMinValue)
        val width = (w * 1000 + 1000).toInt()

        var y = obs.position.y - abs(obs.scale.y/2)
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

        var time = obs.beat + (obs.position.z - obs.scale.z/2)
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

    fun neObstacle(obs: BwObstacle): _obstacles {
        val point = Vec3(obs.position.x - obs.scale.x/2, obs.position.y -obs.scale.y/2,obs.position.z - obs.scale.z/2)
        val bwOrigin = obs.position
        val neOrigin = Vec3(obs.position.x, obs.position.y-obs.scale.y/2, obs.position.z - obs.scale.z/2)
        val localRot = Vec3(
            obs.localRotation.x / 360 * 2 * PI,
            obs.localRotation.y / 360 * 2 * PI,
            obs.localRotation.z / 360 * 2 * PI,
        )
        val pBW = rotatePoint(point,bwOrigin,localRot)
        val pNE = rotatePoint(pBW,neOrigin, localRot * -1)
        val beat = pNE.z + obs.beat

        return _obstacles(
            _time = beat,
            _lineIndex = 0,
            _type = 0,
            _duration = 0.0,
            _width = 0,
            _obstacleCustomData = _obstacleCustomData(
                _position = listOf(pNE.x,pNE.y),
                _scale = obs.scale.toList(),
                _color = obs.color?.toList(),
                _rotation = neRotation(obs.rotation),
                _localRotation = neRotation(obs.localRotation),
                _track = obs.track,
                _noteJumpMovementSpeed = obs.noteJumpMovementSpeed,
                _noteJumpStartBeatOffset = obs.noteJumpStartBeatOffset,
                _fake = obs.fake.takeIf { it == true },
                _interactable = obs.interactable.takeIf { it == false },
                _disableNoteGravity = (!obs.gravity).takeIf { it == true },
            )
        )
    }

    private fun neRotation(vec: Vec3): List<Double>? {
        return if (vec.x == 0.0 && vec.y == 0.0 && vec.z == 0.0)
            null
        else
            listOf(vec.x, vec.y, vec.z)
    }

    private fun rotatePoint(point: Vec3, origin: Vec3, rot: Vec3): Vec3 {
        val a = Mat3(
            Vec3(cos(rot.z), sin(rot.z), 0.0),
            Vec3(-sin(rot.z),cos(rot.z), 0.0),
            Vec3(0.0,0.0,1.0),
        )
        val b = Mat3(
            Vec3(cos(rot.y),0.0, sin(rot.y)),
            Vec3(0.0, 1.0,0.0),
            Vec3(-sin(rot.y),0.0, cos(rot.y))
        )
        val c = Mat3(
            Vec3(1.0,0.0,0.0),
            Vec3(0.0,cos(rot.x),sin(rot.x)),
            Vec3(0.0, -sin(rot.x), cos(rot.x)),
        )
        val rotation = c*b*a

        val rotatedPoint = point * rotation
        return rotatedPoint + origin
    }
}
