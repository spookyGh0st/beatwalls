package structure.wallStructures

import structure.helperClasses.BwObstacle
import structure.math.bwObstacleOf
import structure.math.CubicSpline
import structure.math.Vec3

/**
 * Draw a curve of Walls.
 * It connects through each points starting at p0.
 * Requires at least 2 Points
 *
 */
open class Curve : WallPath() {
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
            val t0 = (k+0.0)/n
            val t1 = (k+1.0)/n

            val p0 = spline.splineAtTime(t0)
            val p1 = spline.splineAtTime(t1)

            l.add(bwObstacleOf(p0,p1,type))
        }
        return l.toList()
    }
}

