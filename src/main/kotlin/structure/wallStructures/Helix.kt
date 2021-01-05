package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run

/**
 * spinning time! make walls spin around the player
 */
class Helix: WallStructure() {

    /**
     * how many spirals will be created
     */
    var count = 2

    /**
     * The radius of the Helix
     */
    var radius = 2.0

    /**
     * does not reflect the actual amount of walls, instead is more of an multiplier (will be changed with version 1.0)
     */
    var amount = 10

    /**
     * the start in degree
     */
    var startRotation = 0.0

    /**
     * describes, how many "Spins" the helix has
     */
    var rotationAmount = 1.0

    /**
     * Point of the center, defaults to 0,2,0
     */
    var center = Point(0, 2, 0)

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}