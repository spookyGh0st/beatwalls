package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run

class NoodleHelix: WallStructure(){
    /**
     * how many spirals will be created
     */
    var count = 1

    /**
     * The radius of the Helix
     */
    var radius = 2.0

    /**
     * the endradius. default: null (normal radius)
     */
    var endRadius:Double? = null
    /**
     *  the amount of walls created. Default: 8*scale
     */
    var amount = 8*(scale?.invoke()?:1).toInt()

    /**
     * spins every wall additionally this amount
     */
    var localRotationOffset = 0.0
    /**
     * the start in degree
     */
    var startRotation = 0.0

    /**
     * describes in degree, how many "Spins" the helix has. default: 360
     */
    var rotationAmount = 360.0

    /**
     * Point of the center, defaults to 0,2,0
     */
    var center = Point(0, 2, 0)

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}