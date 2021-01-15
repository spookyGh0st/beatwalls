package structure.wallStructures

import structure.bwElements.BwObstacle
import structure.math.CubicSpline
import structure.math.CuboidConstrains
import structure.math.Vec3
import structure.math.bwObstacleOf
import types.BwInt
import kotlin.math.abs

/**
 * random curves in a given cubic. Always starts at p0 and ends at p1
 */
class RandomCurve: WallPath(){
    /**
     * first Point that controls the cubic, in which section walls are created. defaults to a random side
     */
    var p0: Vec3 = Vec3()

    /**
     * second Point that crontrols in which section walls are created. z must be higher than p1
     */
    var p1: Vec3 = Vec3()

    /**
     * The amount of single curve nesting together.
     * Defaults to abs(p1.z-p0.z)
     */
    var splineAmount: BwInt = { abs(p1.z-p0.z).toInt() }

    /**
     * Will try to avoid setting Walls in the center
     * No guarantees, since the math for that is hard!
     */
    var avoidCenter: Boolean = true

    /**
     * generating the Walls
     */
     override fun createWalls(): List<BwObstacle> {
        val points = arrayListOf<Vec3>()
        val l = mutableListOf<BwObstacle>()
        val n = splineAmount()
        points.ensureCapacity(n)
        points.add(p0)

        val cc = CuboidConstrains(p0,p1,structureState.R)
        for (i in 1 until n-1){
            val z = p1.z - i/(n-1.0) * (p0.z-p1.z)
            points.add(cc.random(avoidCenter,z))
        }
        points.add(p1)

        val spline = CubicSpline(points)
        val a =  amount()
        for (i in 0 until a){
            val t0 = (i+0.0)/a
            val t1 = (i+1.0)/a

            val p0 = spline.splineAtTime(t0)
            val p1 = spline.splineAtTime(t1)

            l.add(bwObstacleOf(p0,p1,type))
        }
        return l.toList()
    }
}
