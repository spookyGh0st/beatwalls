package structure.wallStructures

import structure.bwElements.BwObstacle
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
        val amount1 = amount()
        val l = mutableListOf<BwObstacle>()
        val vec = p1 - p0
        for (i in 0 until amount1){
            val t0 = p0 + (i+0.0)/ amount1 * vec
            val t1 = p0 + (i+1.0)/ amount1 * vec
            l.add(bwObstacleOf(t0,t1, type))
        }
        return l.toList()
    }
}

