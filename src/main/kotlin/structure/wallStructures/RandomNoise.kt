package structure.wallStructures

import structure.helperClasses.BwObstacle
import structure.helperClasses.Vec3
import types.BwInt
import kotlin.math.abs

/**
 * Random Noise (small mini cubes)
 */
class RandomNoise: WallStructure(){
    /**
     * the amount of the created Walls, if no value is given creates 8x the beatcount
     */
    var amount: BwInt = { abs(p1.z - p0.z).toInt() }

    /**
     * controls one corner of the Area
     */
    var p0 = Vec3()

    /**
     * controls the other corner of the Area
     */
    var p1 = Vec3()

    /**
     * avoids spawning structures in the playspace. default: true
     */
    var avoidCenter: Boolean = true
    /**
     * generating the Walls
     */

    private val redP0 = Vec3(-2,0,0)
    private val redP1 = Vec3(2,3,0)
    override fun createWalls(): List<BwObstacle> {
        val l = mutableListOf<BwObstacle>()

        val xMinBlue = minOf(p0.x, p1.x)
        val xMinRed = minOf(redP0.x, redP1.x)
        val xMaxBlue = maxOf(p0.x, p1.x)
        val xMaxRed = maxOf(redP0.x, redP1.x)

        val yMinBlue = minOf(p0.y, p1.y)
        val yMinRed = minOf(redP0.y, redP0.y)
        val yMaxBlue = maxOf(p0.y, p1.y)
        val yMaxRed = maxOf(redP0.y, redP1.y)

        val r = structureState.R
        for (i in 0 until amount()){
            val x = if(r.nextDouble(1.0) < getRatio(xMinBlue-xMinRed, xMaxRed-xMaxBlue))
                r.nextDouble(xMinBlue, xMinRed)
            else
                r.nextDouble(xMaxRed, xMaxBlue)

            val y = if(r.nextDouble(1.0) < getRatio(yMinBlue-yMinRed, yMaxRed-yMaxBlue))
                r.nextDouble(yMinBlue, yMinRed)
            else
                r.nextDouble(yMaxRed, yMaxBlue)

            val z = p0.z + ((i.toDouble()/amount())*(p1.z - p0.z))
            l.add(BwObstacle(
                position = Vec3(x,y,z)
            ))
        }
        return l.toList()
    }
     private fun getRatio(d1: Double, d2: Double) =
         d1/(d1+d2)
}