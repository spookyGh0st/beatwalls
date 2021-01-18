package structure.wallStructures

import structure.math.PointConnectionType
import structure.math.bwObstacleOf
import structure.math.Vec3

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
         bwObstacleOf(p0,p1,type)
     )
}