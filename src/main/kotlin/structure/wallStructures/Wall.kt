package structure.wallStructures

import structure.helperClasses.PointConnectionType
import structure.helperClasses.bwObstacleOfPoints
import structure.math.Vec3

/**
 * create a single Wall
 */
class Wall: WallStructure() {
    /** StartPoint */
    val p0: Vec3 = Vec3()
    /** Endpoint */
    val p1: Vec3 = Vec3()

    /** How the Points get connected */
    val type: PointConnectionType = PointConnectionType.Cuboid

    /**
     * generating the Walls
     */
     override fun createWalls()  = listOf(
         bwObstacleOfPoints(p0,p1,type)
     )
}