package structure.bwElements

import beatwalls.logWarning
import math.*
import kotlin.math.*

interface BwObject: BwElement {
    var rotation: Quaternion
    var scale: Vec3
    var translation: Vec3
    var globalRotation: Quaternion
    var color: Color?
    var noteJumpMovementSpeed: Double?
    var noteJumpStartBeatOffset: Double?
    var fake: Boolean
    var interactable: Boolean
    var gravity: Boolean
    var track: String?

    fun setTo(p0: Vec3, p1: Vec3, type: PointConnectionType) {
        when(type) {
            PointConnectionType.Cuboid -> {
                scale = p1 - p0
                translation = p0 + 0.5 * (p1 - p0)
            }
            PointConnectionType.Rectangle -> {
                val x = p1.x - p0.x
                val y = p1.y - p0.y
                val c = sqrt(x.pow(2) + y.pow(2))
                val rot = atan2(y, x)
                val axis = Vec3(0.0, 0.0, 1.0)
                rotation = Quaternion(axis, rot)
                scale = Vec3(c, 0, p1.z - p0.z)
                translation = Vec3(middle(p0.x, p1.x), middle(p0.y, p1.y), middle(p0.z, p1.z))
            }
            PointConnectionType.Line -> {
                val v1 = Vec3(0.0,0.0,1.0)
                val v2 = p1 - p0

                val length = v2.length
                val pos = p0 + 0.5 * v2

                v1.normalize(); v2.normalize()

                val a = v1.cross(v2)

                val q = Quaternion()
                q.x = a.x; q.y = a.y; q.z = a.z
                q.w = sqrt((v1.length * v1.length) * (v2.length * v2.length)) + v1.dot(v2)
                q.normalize()
                logWarning("Type Line is currently broken, do not use")

                rotation = q// Quaternion(rot)
                scale = Vec3(0, 0, length)
                translation = pos
            }
        }
    }
    private fun middle(x: Double, y: Double): Double =
        x + (y-x)/2
}
