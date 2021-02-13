package structure.wallStructures

import structure.bwElements.BwObstacle
import math.*
import types.BwInt
import kotlin.math.roundToInt

/**
 * random curves in a given cubic. Always starts at p0 and ends at p1
 */
class RandomCurve: WallStructure(), WallPath{
    /** first point of the cubic containing the Structure */
    var p0: Vec2 = Vec2()
    /** second point of the cubic containing the Structure */
    var p1: Vec2 = Vec2()

    /**
     * The amount of Walls Generated.
     * It is recommended to link this to the duration
     * Default: 6 * d
     */
    override var amount: BwInt = { (6 * duration ).roundToInt() }

    /**
     * The Duration of the Curve in Beats
     * Default: 1.0
     */
    override var duration = 1.0

    /**
     * The type of Wall that will be created.
     * ME does only support Cuboid, all other will look cooler B)
     */
    override var type: PointConnectionType = PointConnectionType.Cuboid

    /**
     * The amount of single curve nesting together.
     * It is recommended to link this to the duration
     * Default: d * 2
     */
    var splineAmount: BwInt = { (2 * duration).roundToInt() }

    /**
     * Will try to avoid setting Walls in the center
     * No guarantees, since the math for that is hard!
     */
    var avoidCenter: Boolean = true

    override fun createWalls(): List<BwObstacle> {
        // Always must be before amount get's called
        structureState.duration = duration
        val t0 = p0.toVec3(0.0)
        val t1 = p1.toVec3(1.0)

        val points = arrayListOf<Vec3>()
        val l = mutableListOf<Vec3>()
        val splineAmount = splineAmount()
        points.ensureCapacity(splineAmount)
        points.add(t0)

        val cc = CuboidConstrains(t0,t1,structureState.rand)
        for (i in 1 until splineAmount){
            val z = i.toDouble()/splineAmount
            points.add(cc.randomVec3(avoidCenter,z))
        }
        points.add(t1)

        val spline = CubicSpline(points)
        val a =  amount()
        for (i in 0 .. a){
            val t = (i+0.0)/a
            l.add(spline.splineAtTime(t))
        }
        return createFromPointList(l)
    }
}
