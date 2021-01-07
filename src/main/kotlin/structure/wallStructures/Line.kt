package structure.wallStructures

import structure.helperClasses.Point
import structure.helperClasses.SpookyWall
import kotlin.math.*

/**
 * Draws a wall of line between the 2 provided Points
 */
class Line: WallStructure(){

    /**
     * how many walls will be created. Default: 6 * duration
     */
    var amount: Int? = null

    /**
     * The startPoint
     */
    var p1 = Point(0, 0, 0)

    /**
     * the End Point
     */
    var p2 = Point(0, 0, 1)

    /**
     * generating the Walls
     */
    override fun create(): List<SpookyWall> {
        if (amount == null)
            amount = (6 * abs(p2.z - p1.z)).toInt()
        return (line(p1, p2, amount)).toList()
    }
}

fun line(p0: Point, p1: Point, amount: Int?): ArrayList<SpookyWall> {
    return line(p0.x, p0.y, p0.z, p1.x, p1.y, p1.z, amount)
}

fun line(p0: Triple<Double, Double, Double>, p1: Triple<Double, Double, Double>, amount: Int?): ArrayList<SpookyWall> {
    return line(
        p0.first,
        p0.second,
        p0.third,
        p1.first,
        p1.first,
        p1.first,
        amount
    )
}

/** Draws a line between 2 coordinates */
fun line(
    px1: Double,
    py1: Double,
    pz1: Double = 0.0,
    px2: Double,
    py2: Double,
    pz2: Double = 0.0,
    defaultAmount: Int? = null,
    defaultDuration: Double? = null
): ArrayList<SpookyWall> {
    //swap values if y2 < y1  - this functions goes from bottom to top
    var x1 = px1
    var x2 = px2
    var y1 = py1
    var y2 = py2
    var z1 = pz1
    var z2 = pz2

    val a = abs(y2 - y1)
    val c = sqrt(
        abs(x2 - x1).pow(2) + abs(z2 - z1).pow(2)
    )
    val b = sqrt(a.pow(2) + c.pow(2))
    val dgr = asin(a / b)


    val amount = defaultAmount ?: ((cos(dgr) * sin(dgr)).pow(1.5) * 50 + 1).toInt()

    val list = arrayListOf<SpookyWall>()

    if (z2 < z1) {
        x1 = x2.also { x2 = x1 }
        y1 = y2.also { y2 = y1 }
        z1 = z2.also { z2 = z1 }
    }

    //setting the solid values
    val w = (abs(x2 - x1) / amount)
    val width = w
    val h = (abs(y2 - y1) / amount)
    val height = h
    val d = (abs(z2 - z1) / amount)
    val duration = d

    for (i in 0 until amount) {
        //setting the dynamic values
        val startHeight =
            if (y2 > y1)
                y1 + i * h
            else
                y1 - (i + 1) * h
        val startRow =
            if (x2 > x1)
                x1 + i * w
            else
                x1 - (i + 1) * w
        val startTime = z1 + i * d

        //adding the obstacle
        val myD = defaultDuration ?: duration
        list.add(
            SpookyWall(
                startRow,
                myD,
                width,
                height,
                startHeight,
                startTime
            )
        )
    }
    return list
}