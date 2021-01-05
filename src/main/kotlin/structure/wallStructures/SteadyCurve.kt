package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run

/**
 * Draw a steady curve of Walls. that is exactly 1 beat long
 * */
class SteadyCurve: WallStructure(){
    /**
     * the start Point of the Curve
     */
    var p1: Point = Point(0, 0, 0)

    /**
     * the first Controllpoint, defaults to the startPoint
     */
    var p2: Point? = null

    /**
     * second ControlPoint, defaults to the end point
     */
    var p3: Point? = null

    /**
     * The EndPoint of the Curve
     */
    var p4: Point = Point(0, 0, 1)

    /**
     * amount of Walls
     */
    var amount: Int = 8

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}