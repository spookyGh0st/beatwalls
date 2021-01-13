package structure.wallStructures

import structure.helperClasses.BwObstacle
import structure.math.CuboidConstrains
import structure.math.Vec3
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

    override fun createWalls(): List<BwObstacle> {
        val l = mutableListOf<BwObstacle>()
        val cc = CuboidConstrains(p0, p1, structureState.R)

        for (i in 0 until amount()){
            val z = p0.z + ((i.toDouble()/amount())*(p1.z - p0.z))
            l.add(BwObstacle(
                position = cc.random(avoidCenter, z)
            ))
        }
        return l.toList()
    }
}
