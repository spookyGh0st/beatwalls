package structure.specialStrucures

import structure.*
import structure.helperClasses.Point
import structure.helperClasses.SpookyWall
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow

fun Curve.run(): List<SpookyWall> {
    return curve(p1, p2 ?: p1, p3 ?: p4, p4, amount)
}

fun SteadyCurve.run(): List<SpookyWall> {
    p1=p1.copy(z=0.0)
    p2= p2?.copy(z=0.3333)
    p3= p3?.copy(z=0.6666)
    p4=p4.copy(z=1.0)
    return (curve(p1, p2 ?: p1, p3 ?: p4, p4, amount))
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