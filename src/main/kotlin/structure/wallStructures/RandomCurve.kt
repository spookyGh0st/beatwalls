package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run
import kotlin.random.Random

/**
 * random curves in a given cubic. Always starts at p1 and ends at p2.
 */
class RandomCurve: WallStructure(){

    /**
     * dont touch
     */
    private var randomSideChooser = Random.nextBoolean()

    /**
     * first Point that controls the cubic, in which section walls are created. defaults to a random side
     */
    var p1: Point = if (randomSideChooser) Point(
        1,
        0,
        0
    ) else Point(-1, 0, 0)

    /**
     * second Point that crontrols in which section walls are created. z must be higher than p1
     */
    var p2: Point = if (randomSideChooser) Point(
        4,
        0,
        1
    ) else Point(-4, 4, 1)

    /**
     * the amount of Walls per beat
     */
    var amount: Int = 8

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}