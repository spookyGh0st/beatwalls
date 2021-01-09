package structure.wallStructures

import structure.helperClasses.*
import types.BwInt
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow

/**
 * Draw a curve of Walls.
 * It connects through each points starting at p0.
 * Requires at least 2 Points
 *
 */
open class Curve : Wallpath() {
    /** the 0. Point of the Curve */
    var p0: Vec3 = Vec3()
    /** the 1. Point of the Curve */
    var p1: Vec3 = Vec3()
    /** the 2. Point of the Curve */
    var p2: Vec3? = null
    /** the 3. Point of the Curve */
    var p3: Vec3? = null
    /** the 4. Point of the Curve */
    var p4: Vec3? = null
    /** the 5. Point of the Curve */
    var p5: Vec3? = null
    /** the 6. Point of the Curve */
    var p6: Vec3? = null
    /** the 7. Point of the Curve */
    var p7: Vec3? = null


    override fun createWalls(): List<BwObstacle> {
        val l = mutableListOf<BwObstacle>()
        val points = listOfNotNull(p0,p1,p2,p3,p4,p5,p6,p7)
        val spline = CubicSpline(points)
        val n = (points.size-1) * amount()
        for (k in 0 until n){
            structureState.progress = k.toDouble()/n
            val t0 = (k+0.0)/amount()
            val t1 = (k+1.0)/amount()

            val p0 = spline.splineAtTime(t0)
            val p1 = spline.splineAtTime(t1)

            l.add(bwObstacleOfPoints(p0,p1,type))
        }
        return l.toList()
    }
}

fun curve(startPoint: Point, p1: Point, p2: Point, endPoint: Point, amount: Int): List<SpookyWall> {
    val list = mutableListOf<SpookyWall>()
    repeat(amount){
        val currentPoint =
            quadraticBezier(
                startPoint,
                p1,
                p2,
                endPoint,
                it.toDouble() / amount
            )
        val nextPoint = quadraticBezier(
            startPoint,
            p1,
            p2,
            endPoint,
            (it + 1.0) / amount
        )
        val startRow = currentPoint.x
        val startHeight = currentPoint.y
        val startTime = min(currentPoint.z, nextPoint.z)
        val width = nextPoint.x - currentPoint.x
        val height = nextPoint.y - currentPoint.y
        val duration = abs(nextPoint.z - currentPoint.z)
        list.add(
            SpookyWall(
                startRow,
                duration,
                width,
                height,
                startHeight,
                startTime
            )
        )
    }
    return list.toList()
}

fun quadraticBezier(p0: Point, p1: Point, p2: Point, p3: Point, t:Double): Point {
    val x =(1-t).pow(3)*p0.x +
            (1-t).pow(2)*3*t*p1.x +
            (1-t)*3*t*t*p2.x +
            t*t*t*p3.x
    val y =(1-t).pow(3)*p0.y +
            (1-t).pow(2)*3*t*p1.y +
            (1-t)*3*t*t*p2.y +
            t*t*t*p3.y
    val z =(1-t).pow(3)*p0.z +
            (1-t).pow(2)*3*t*p1.z +
            (1-t)*3*t*t*p2.z +
            t*t*t*p3.z
    return Point(x, y, z)
}