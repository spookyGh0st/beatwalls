package interpreter.evaluator

import chart.difficulty._obstacleCustomData
import chart.difficulty._obstacles
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import structure.Structure
import structure.helperClasses.BwElement
import structure.helperClasses.BwObstacle
import structure.helperClasses.Mat3
import structure.helperClasses.Vec3
import kotlin.math.*

@Suppress("SimplifyBooleanWithConstants")
class Evaluator(val structs: List<Structure>) {
    val elements = mutableListOf<BwElement>()

    fun evaluate(): List<BwElement>{
        runBlocking {
            for (s in structs){
                launch { elements.addAll(s.createElements()) }
            }
        }
        return elements.toList()
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
        // todo BPM change adjusting

        return _obstacles(
            _time = pNE.z,
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
}

fun neRotation(vec: Vec3): List<Double>? {
    return if (vec.x == 0.0 && vec.y == 0.0 && vec.z == 0.0)
        null
    else
        listOf(vec.x, vec.y, vec.z)
}

fun rotatePoint(point: Vec3, origin: Vec3, rot: Vec3): Vec3{
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
