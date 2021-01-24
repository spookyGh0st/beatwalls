package interpreter

import chart.difficulty.*
import structure.bwElements.BwElement
import structure.bwElements.BwEvent
import structure.bwElements.BwNote
import structure.bwElements.BwObstacle
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
        val bwPos = Vec3(
            obs.position.x - obs.scale.x/2,
            obs.position.y - obs.scale.y/2,
            obs.position.z - obs.scale.z/2
        )
        val localRot = obs.localRotation * ( 1.0/360*2*PI)

        val scale = Vec3(
            abs(obs.scale.x).coerceAtLeast(meMinValue),
            abs(obs.scale.y).coerceAtLeast(meMinValue),
            abs(obs.scale.z.coerceAtLeast(meMinValue*0.01)),
       )

        val pivot_diff = scale * Vec3(0,-0,0)
        val correction = pivot_diff - (localRot * pivot_diff)
        val nePos = bwPos + (localRot * pivot_diff) + correction

        val beat = bwPos.z + obs.beat
        val x = if(obs.scale.x < 0) nePos.x -scale.x else nePos.x
        val y = if(obs.scale.y < 0) nePos.y -scale.y else nePos.y

        return _obstacles(
            _time = beat,
            _lineIndex = 0,
            _type = 0,
            _duration = obs.speed?: scale.z,
            _width = 0,
            _obstacleCustomData = _obstacleCustomData(
                _position = listOf(x,y),
                _scale = scale.toVec2().toList(),
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
}
