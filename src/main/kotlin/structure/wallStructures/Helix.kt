package structure.wallStructures

import structure.bwElements.BwObstacle
import structure.math.PointConnectionType
import structure.math.Vec2
import structure.math.Vec3
import types.BwDouble
import types.BwInt
import types.bwDouble
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * spins Walls around a center Point
 * Can make heavy use of easing to create cool effects
 * You can Create multiple helixes around the same Point by using the c - Variable
 */
class Helix:WallStructure(),  WallPath{

    /**
     * The amount of Walls Generated **PER SPIRAL**
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
     * The radius of the Helix.
     * Like many other properties, this Property supports easing.
     * example: radius: linear(2,4)
     */
    var radius: BwDouble = bwDouble(2)


    /**
     * spins every wall additionally this amount
     */
    var localRotationOffset: BwDouble = bwDouble(0)

    /**
     * the start rotation in degree.
     * 0 starts on the right, 90 on top, 180 on the left, etc....
     * To create multiple Helixe use:
     * ```yaml
     * repeat: 3
     * startRotation: c*90
     * ```
     */
    var startRotation: BwDouble = bwDouble(0)

    /**
     * describes in degree, how many "Spins" the helix has. default: 360
     */
    var rotationAmount: BwDouble = bwDouble(360)

    /**
     * Point of the center, defaults to 0,2
     */
    var p0 = Vec2(0, 2)


    /**
     * generating the Walls
     */
    override fun createWalls(): List<BwObstacle> {
        // Always must be set before amount get's called
        structureState.duration = duration
        val n = amount()

        val points = mutableListOf<Vec3>()
        for(i in 0..n){
            val z = i.toDouble()/ n
            setProgress(z)
            val currentRot = (z* rotationAmount() + startRotation())/360*2*PI
            val x = cos(currentRot)
            val y = sin(currentRot)
            val r = radius()
            points.add(Vec3(p0.x +x*r,p0.y +y*r,z))
        }
        val l = createFromPointList(points)
        for(o in l){
            setProgress(o.translation.z)
            o.globalRotation.z += localRotationOffset()
        }
        return l
    }
}