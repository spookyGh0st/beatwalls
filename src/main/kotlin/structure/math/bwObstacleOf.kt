package structure.math

import structure.bwElements.BwObstacle
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt

fun bwObstacleOf(p0: Vec3, p1: Vec3, type: PointConnectionType) = when(type){
    PointConnectionType.Cuboid -> BwObstacle(
        scale = Vec3(p1.x - p0.x, p1.y - p0.y, p1.z - p0.z),
        position = Vec3(middle(p0.x, p1.x), middle(p0.y, p1.y), middle(p0.z, p1.z)),
    )
    PointConnectionType.Rectangle -> {
        val a = p1.x - p0.x
        val b = p1.y - p1.y
        val c = sqrt(a.pow(2) + b.pow(2))
        val localRotZ =  180 - atan(a / b) /(2* PI)*360
        BwObstacle(
            scale = Vec3(c, 0, p1.z - p0.z),
            position = Vec3(middle(p0.x, p1.x), middle(p0.y, p1.y), middle(p0.z, p1.z)),
            localRotation = Vec3(0, 0, localRotZ)
        )
    }
    PointConnectionType.Line -> {
        val aForZ = p1.x - p0.x
        val bForZ = p1.y - p1.y
        val cForZ = sqrt(aForZ.pow(2) + bForZ.pow(2))
        val localRotZ = 180 - atan(aForZ / bForZ) / (2 * PI) * 360

        val aForX = p1.z - p0.z
        val bForX = cForZ
        val cForX = sqrt(aForX.pow(2) + bForX.pow(2))
        val localRotX = 180 - atan(aForZ / bForZ) / (2 * PI) * 360
        BwObstacle(
            scale = Vec3(0, 0, cForX),
            position = Vec3(middle(p0.x, p1.x), middle(p0.y, p1.y), middle(p0.z, p1.z)),
            localRotation = Vec3(localRotX, 0, localRotZ)
        )
    }
}

private fun middle(x: Double, y: Double): Double =
    x + (y-x)/2