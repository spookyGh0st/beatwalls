package structure.wallStructures

import structure.helperClasses.BwObstacle
import structure.helperClasses.bwObstacleOfPoints
import structure.math.Point
import structure.math.Vec3
import types.BwDouble
import types.BwInt
import types.bwDouble
import types.bwInt
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Helix: Wallpath(){
    /**
     * how many spirals will be created
     */
    var spiralAmount: BwInt = bwInt(2)

    /**
     * The radius of the Helix
     */
    var radius: BwDouble = bwDouble(2)

    /**
     * the endradius. default: radius)
     */
    var endRadius:BwDouble = { radius() }

    /**
     * spins every wall additionally this amount
     */
    var localRotationOffset: BwDouble = bwDouble(0)

    /**
     * the start in degree
     */
    var startRotation: BwDouble = bwDouble(0)

    /**
     * describes in degree, how many "Spins" the helix has. default: 360
     */
    var rotationAmount: BwDouble = bwDouble(360)

    /**
     * Point of the center, defaults to 0,2,0
     */
    var center = Point(0, 2, 0)


    /**
     * generating the Walls
     */
     override fun createWalls(): List<BwObstacle> {
        val l = mutableListOf<BwObstacle>()
        for(ci in 0 until spiralAmount()){
             val points = mutableListOf<Vec3>()
             val countOffset = ci.toDouble()/ spiralAmount() *2*PI
             for(i in 0..amount()){
                 val z = i.toDouble()/ amount()
                 structureState.progress = z
                 val currentRot = (z* rotationAmount() + startRotation())/360*2*PI + countOffset
                 val x = cos(currentRot)
                 val y = sin(currentRot)
                 val r = radius() + endRadius() * (i.toDouble() / amount())
                 points[i] = Vec3(center.x +x*r,center.y +y*r,z)
             }
             for(i in 0 until amount()){
                 val p1= points[i]
                 val p2= points[i+1]
                 val ob = bwObstacleOfPoints(p1,p2, type);

                 structureState.progress = p1.z
                 ob.rotation.z += localRotationOffset()
                 l.add(ob)
             }
         }
        return l.toList()
     }
}