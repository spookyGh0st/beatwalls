package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run

/**
 * Creates a continues line with up to 31 Points. this one does not have a typo in the name :)
 */
class ContinuousCurve : WallStructure(){
    /**
     * dont touch
     */
    val creationAmount = 32
    /**
     * The duration of each wall
     */
    var duration: Double = -3.0
    /**
     * The amount of Walls per beat
     */
    var amount: Int = 8

    /**
     * The 1 Point. use this to set an exact Point the wall will go through
     */
    var p1: Point? = null

    /**
     * The ControllPoint for the 1 Point. Use this to guide the curve to his direction
     */
    var c1: Point? = null

    /**
     * The 2 Point. use this to set an exact Point the wall will go through
     */
    var p2: Point? = null

    /**
     * The ControllPoint for the 2 Point. Use this to guide the curve to his direction
     */
    var c2: Point? = null

    /**
     * The 3 Point. use this to set an exact Point the wall will go through
     */
    var p3: Point? = null

    /**
     * The ControllPoint for the 3 Point. Use this to guide the curve to his direction
     */
    var c3: Point? = null

    /**
     * The 4 Point. use this to set an exact Point the wall will go through
     */
    var p4: Point? = null

    /**
     * The ControllPoint for the 4 Point. Use this to guide the curve to his direction
     */
    var c4: Point? = null

    /**
     * The 5 Point. use this to set an exact Point the wall will go through
     */
    var p5: Point? = null

    /**
     * The ControllPoint for the 5 Point. Use this to guide the curve to his direction
     */
    var c5: Point? = null

    /**
     * The 6 Point. use this to set an exact Point the wall will go through
     */
    var p6: Point? = null

    /**
     * The ControllPoint for the 6 Point. Use this to guide the curve to his direction
     */
    var c6: Point? = null

    /**
     * The 7 Point. use this to set an exact Point the wall will go through
     */
    var p7: Point? = null

    /**
     * The ControllPoint for the 7 Point. Use this to guide the curve to his direction
     */
    var c7: Point? = null

    /**
     * The 8 Point. use this to set an exact Point the wall will go through
     */
    var p8: Point? = null

    /**
     * The ControllPoint for the 8 Point. Use this to guide the curve to his direction
     */
    var c8: Point? = null

    /**
     * The 9 Point. use this to set an exact Point the wall will go through
     */
    var p9: Point? = null

    /**
     * The ControllPoint for the 9 Point. Use this to guide the curve to his direction
     */
    var c9: Point? = null

    /**
     * The 10 Point. use this to set an exact Point the wall will go through
     */
    var p10: Point? = null

    /**
     * The ControllPoint for the 10 Point. Use this to guide the curve to his direction
     */
    var c10: Point? = null

    /**
     * The 11 Point. use this to set an exact Point the wall will go through
     */
    var p11: Point? = null

    /**
     * The ControllPoint for the 11 Point. Use this to guide the curve to his direction
     */
    var c11: Point? = null

    /**
     * The 12 Point. use this to set an exact Point the wall will go through
     */
    var p12: Point? = null

    /**
     * The ControllPoint for the 12 Point. Use this to guide the curve to his direction
     */
    var c12: Point? = null

    /**
     * The 13 Point. use this to set an exact Point the wall will go through
     */
    var p13: Point? = null

    /**
     * The ControllPoint for the 13 Point. Use this to guide the curve to his direction
     */
    var c13: Point? = null

    /**
     * The 14 Point. use this to set an exact Point the wall will go through
     */
    var p14: Point? = null

    /**
     * The ControllPoint for the 14 Point. Use this to guide the curve to his direction
     */
    var c14: Point? = null

    /**
     * The 15 Point. use this to set an exact Point the wall will go through
     */
    var p15: Point? = null

    /**
     * The ControllPoint for the 15 Point. Use this to guide the curve to his direction
     */
    var c15: Point? = null

    /**
     * The 16 Point. use this to set an exact Point the wall will go through
     */
    var p16: Point? = null

    /**
     * The ControllPoint for the 16 Point. Use this to guide the curve to his direction
     */
    var c16: Point? = null

    /**
     * The 17 Point. use this to set an exact Point the wall will go through
     */
    var p17: Point? = null

    /**
     * The ControllPoint for the 17 Point. Use this to guide the curve to his direction
     */
    var c17: Point? = null

    /**
     * The 18 Point. use this to set an exact Point the wall will go through
     */
    var p18: Point? = null

    /**
     * The ControllPoint for the 18 Point. Use this to guide the curve to his direction
     */
    var c18: Point? = null

    /**
     * The 19 Point. use this to set an exact Point the wall will go through
     */
    var p19: Point? = null

    /**
     * The ControllPoint for the 19 Point. Use this to guide the curve to his direction
     */
    var c19: Point? = null

    /**
     * The 20 Point. use this to set an exact Point the wall will go through
     */
    var p20: Point? = null

    /**
     * The ControllPoint for the 20 Point. Use this to guide the curve to his direction
     */
    var c20: Point? = null

    /**
     * The 21 Point. use this to set an exact Point the wall will go through
     */
    var p21: Point? = null

    /**
     * The ControllPoint for the 21 Point. Use this to guide the curve to his direction
     */
    var c21: Point? = null

    /**
     * The 22 Point. use this to set an exact Point the wall will go through
     */
    var p22: Point? = null

    /**
     * The ControllPoint for the 22 Point. Use this to guide the curve to his direction
     */
    var c22: Point? = null

    /**
     * The 23 Point. use this to set an exact Point the wall will go through
     */
    var p23: Point? = null

    /**
     * The ControllPoint for the 23 Point. Use this to guide the curve to his direction
     */
    var c23: Point? = null

    /**
     * The 24 Point. use this to set an exact Point the wall will go through
     */
    var p24: Point? = null

    /**
     * The ControllPoint for the 24 Point. Use this to guide the curve to his direction
     */
    var c24: Point? = null

    /**
     * The 25 Point. use this to set an exact Point the wall will go through
     */
    var p25: Point? = null

    /**
     * The ControllPoint for the 25 Point. Use this to guide the curve to his direction
     */
    var c25: Point? = null

    /**
     * The 26 Point. use this to set an exact Point the wall will go through
     */
    var p26: Point? = null

    /**
     * The ControllPoint for the 26 Point. Use this to guide the curve to his direction
     */
    var c26: Point? = null

    /**
     * The 27 Point. use this to set an exact Point the wall will go through
     */
    var p27: Point? = null

    /**
     * The ControllPoint for the 27 Point. Use this to guide the curve to his direction
     */
    var c27: Point? = null

    /**
     * The 28 Point. use this to set an exact Point the wall will go through
     */
    var p28: Point? = null

    /**
     * The ControllPoint for the 28 Point. Use this to guide the curve to his direction
     */
    var c28: Point? = null

    /**
     * The 29 Point. use this to set an exact Point the wall will go through
     */
    var p29: Point? = null

    /**
     * The ControllPoint for the 29 Point. Use this to guide the curve to his direction
     */
    var c29: Point? = null

    /**
     * The 30 Point. use this to set an exact Point the wall will go through
     */
    var p30: Point? = null

    /**
     * The ControllPoint for the 30 Point. Use this to guide the curve to his direction
     */
    var c30: Point? = null

    /**
     * The 31 Point. use this to set an exact Point the wall will go through
     */
    var p31: Point? = null

    /**
     * The ControllPoint for the 31 Point. Use this to guide the curve to his direction
     */
    var c31: Point? = null

    /**
     * The 32 Point. use this to set an exact Point the wall will go through
     */
    var p32: Point? = null

    /**
     * The ControllPoint for the 32 Point. Use this to guide the curve to his direction
     */
    var c32: Point? = null

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}