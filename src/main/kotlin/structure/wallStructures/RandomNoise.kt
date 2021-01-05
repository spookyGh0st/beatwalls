package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run

/**
 * Random Noise (small mini cubes)
 */
class RandomNoise: WallStructure(){
    /**
     * the amount of the created Walls, if no value is given creates 8x the beatcount
     */
    var amount: Int? = null

    /**
     * controls one corner of the Area
     */
    var p1 = Point(-6, 0, 0)

    /**
     * controls the other corner of the Area
     */
    var p2 = Point(6, 5, 1)

    /**
     * avoids spawning structures in the playspace. default: true
     */
    var avoidCenter: Boolean = true
    /**
     * generating the Walls
     */
    override fun generate() = run()
}