package structure.math

import structure.bwElements.BwObstacle
import kotlin.math.*

fun bwObstacleOf(p0: Vec3, p1: Vec3, type: PointConnectionType) = when(type){
    PointConnectionType.Cuboid -> BwObstacle(
        scale = p1-p0,
        position = p0 + 0.5 * (p1-p0)
    )
    PointConnectionType.Rectangle -> {
        val a = p1.y - p0.y
        val b = p1.x - p0.x
        val c = sqrt(a.pow(2) + b.pow(2))
        var localRotZ =  asin(a/c) / (2*PI) * 360
        if (p0.x > p1.x) localRotZ*= -1
        BwObstacle(
            scale = Vec3(c, 0, p1.z - p0.z),
            position = Vec3(middle(p0.x, p1.x), middle(p0.y, p1.y), middle(p0.z, p1.z)),
            localRotation = Vec3(0, 0, localRotZ)
        )
    }
    PointConnectionType.Line -> {
        val vBind = p1 -p0
        val x = vBind.x
        val y = vBind.y
        val z = vBind.z
        val r = vBind.length
        val theta = (acos(z/r) / (2*PI)) * 360 * -1
        val phi =   (atan2(y,x) / (2*PI)) * 360 * -1 + 90
        val rot =   Vec3(theta, phi,0)

        val scaleZ = sqrt((z/2) *(z/2) +(y/2) * (y/2))
        val pos = p0 + 0.5 * vBind

        BwObstacle(
            scale = Vec3(0, 0, scaleZ, ),
            position = pos,
            localRotation = rot
        )
    }
}

private fun middle(x: Double, y: Double): Double =
    x + (y-x)/2