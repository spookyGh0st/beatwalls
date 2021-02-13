package structure.wallStructures

import structure.bwElements.BwObstacle
import math.PointConnectionType
import math.Vec3

/**
 * create a single Wall
 */
class Wall: WallStructure() {
    /** StartPoint */
    var p0: Vec3 = Vec3()
    /** Endpoint */
    var p1: Vec3 = Vec3()

    /** How the Points get connected */
    var type: PointConnectionType = PointConnectionType.Cuboid

    /**
     * generating the Walls
     */
     override fun createWalls()  = listOf(
         BwObstacle(p0,p1,type)
     )
}