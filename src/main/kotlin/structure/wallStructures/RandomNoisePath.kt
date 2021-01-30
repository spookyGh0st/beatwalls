package structure.wallStructures

import structure.bwElements.BwObstacle
import structure.math.CubicSpline

/**
 * Randomized spawns Walls along a Curve path
 */
class RandomNoisePath : Curve() {
    override fun createWalls(): List<BwObstacle> {
        val l = mutableListOf<BwObstacle>()
        val points = vec3PointList(p0,p1,p2,p3,p4,p5,p6,p7)
        val spline = CubicSpline(points)
        val n = (points.size-1) * amount()
        for (k in 0 until n){
            setProgress(k.toDouble()/n)
            val p0 = spline.splineAtTime(structureState.rand.nextDouble())
            p0.z = k.toDouble()/n*duration
            l.add(BwObstacle(
                translation = p0
            ))
        }
        return l.toList()
    }
}