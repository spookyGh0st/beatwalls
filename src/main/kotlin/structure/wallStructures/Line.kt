package structure.wallStructures

import structure.helperClasses.BwObstacle
import structure.math.PointConnectionType
import structure.math.bwObstacleOf
import structure.math.*

/**
 * Draws a wall of line between the 2 provided Points
 */
class Line: WallPath(){

    /**
     * The startPoint
     */
    var p0 = Vec3()

    /**
     * the End Point
     */
    var p1 = Vec3()

    /**
     * generating the Walls
     */
    override fun createWalls(): List<BwObstacle> {
        return bwObstacleLine(p0, p1, amount(), type)
    }
}

fun bwObstacleLine(p0: Vec3, p1: Vec3, amount: Int, type: PointConnectionType): List<BwObstacle> {
    val l = mutableListOf<BwObstacle>()
    val vec = p1 - p0
    for (i in 0 until amount){
        val t0 = p0 + (i+0.0)/amount * vec
        val t1 = p0 + (i+1.0)/amount * vec
        l.add(bwObstacleOf(t0,t1,type))
    }
    return l.toList()
}