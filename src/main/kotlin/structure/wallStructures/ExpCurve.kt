package structure.wallStructures

import structure.bwElements.BwObstacle
import math.*
import types.BwInt
import kotlin.math.roundToInt

/**
 * A Bezier Curve where 1 control Point instead of 2 is used
 */
open class ExpCurve : WallStructure(), WallPath {
    /** the Starting Point of the Curve */
    var p0: Vec2 = Vec2()
    /** the ControlPoint of the Curve. Imagine stretching it in Gimp. */
    var p1: Vec2 = Vec2()
    /** the Endpoint of the Curve */
    var p2: Vec2 = Vec2()

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

    override fun createWalls(): List<BwObstacle> {
        // Always must be before amount get's called
        structureState.duration = duration
        val n = amount()

        val t0 = p0.toVec3(0.0)
        val t1 = p1.toVec3(duration*1.0/3.0)
        val t2 = p1.toVec3(duration*2.0/3.0)
        val t3 = p2.toVec3(duration)

        val points = mutableListOf<Vec3>()
        for (k in 0 .. n){
            val p = k.toDouble()/n
            setProgress(p)
            val v = quadraticBezier(t0,t1,t2,t3, p)
            points.add(v)
        }
        return createFromPointList(points)
    }
}

