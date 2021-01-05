package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run

/**
 * Draws a wall of line between the 2 provided Points
 */
class Line: WallStructure(){

    /**
     * how many walls will be created. Default: 6 * duration
     */
    var amount: Int? = null

    /**
     * The startPoint
     */
    var p1 = Point(0, 0, 0)

    /**
     * the End Point
     */
    var p2 = Point(0, 0, 1)

    /**
     * generating the Walls
     */
    override fun generate() = run()
}