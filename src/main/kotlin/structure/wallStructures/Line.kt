package structure.wallStructures

import structure.bwElements.BwObstacle
import structure.math.*
import types.BwInt
import kotlin.math.roundToInt

/**
 * Draws Direct Lines between the Points
 */
class Line: WallStructure(), WallPath{
    /** the 0. Point of the Curve */
    var p0: Vec2 = Vec2()
    /** the 1. Point of the Curve */
    var p1: Vec2 = Vec2()
    /** the 2. Point of the Curve */
    var p2: Vec2? = null
    /** the 3. Point of the Curve */
    var p3: Vec2? = null
    /** the 4. Point of the Curve */
    var p4: Vec2? = null
    /** the 5. Point of the Curve */
    var p5: Vec2? = null
    /** the 6. Point of the Curve */
    var p6: Vec2? = null
    /** the 7. Point of the Curve */
    var p7: Vec2? = null

    /**
     * The amount of Walls Generated.
     * It is recommended to link this to the duration
     * Default: 6 * d
     */
    override var amount: BwInt = { (6 * duration ).roundToInt() }

    /**
     * The Duration of the Structure in Beats
     * Default: 1.0
     */
    override var duration = 1.0

    /**
     * The type of Wall that will be created.
     * ME does only support Cuboid, all other will look cooler B)
     */
    override var type: PointConnectionType = PointConnectionType.Cuboid


    /**
     * generating the Walls
     */
    override fun createWalls(): List<BwObstacle> {
        // Always must be before amount get's called
        structureState.duration = duration
        val n = amount()

        val pathPoints = vec3PointList(p0,p1,p2,p3,p4,p5,p6,p7)

        // Each Wall is defined from 2 Points
        // So in total points.size -1 Walls will be created
        val points = mutableListOf<Vec3>()

        for (i in 0 until  n){
            val s = pathPoints.size

            val ip = i.toDouble()/n * (s -1)
            val index = ip.toInt()
            val lineStart = pathPoints[index]
            val lineEnd = pathPoints[index+1]
            points.add(lineStart + (ip -index) * (lineEnd -lineStart))
        }
        points.add(pathPoints.last())
        return createFromPointList(points)
    }
}
