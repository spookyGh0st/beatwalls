package structure.wallStructures

import structure.helperClasses.Point

/**
 * NOT IMPLEMENTED YET
 *
 * Helix Curve lets you define 1/4 of a curve around the center. The program creates the rest of the helix
 */
class HelixCurve: WallStructure() {
    /**
     * the start Point of the Curve
     */
    var p1: Point = Point(0, 0, 0)

    /**
     * the first Controllpoint, defaults to the startPoint
     */
    var p2: Point =
        Point(4.0, 0.0, 0.33)

    /**
     * second ControlPoint, defaults to the end point
     */
    var p3: Point =
        Point(4.0, 0.0, 0.66)

    /**
     * The EndPoint of the Curve
     */
    var p4: Point = Point(4, 2, 1)

    /**
     * the amount of walls per Helix
     */
    var amount: Int = 8

    /**
     * how many helix spines will be Created. Only 2 or 4 allowed
     */
    var count: Int = 4

    /**
     * generating the Walls
     */
     override fun generate()  =
         TODO()
}