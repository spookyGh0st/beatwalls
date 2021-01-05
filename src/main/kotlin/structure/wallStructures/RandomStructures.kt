package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run

/**
 * Define your own WallStructure from existing WallStructures.
 */
class RandomStructures: WallStructure() {
    /**
     * The name of Different Structures. Separated by comma (example: structures: Floor, Ceiling)
     * You can also define Parameters of the first Structure
     * These get loaded in Order, So if your reference defined Structures, those must be listed before that
     * The Beat Value gets every time, so it should be 0 most of the time
     */
    var structures: List<WallStructure> = listOf()

    /**
     * The first point of the area which your structures get placed into
     */
    var p1 = Point(-8, 0, 0)

    /**
     * The first point of the area which your structures get placed into
     */
    var p2 = Point(8, 0, 8)

    /**
     * How many structures you want to place. default: 8
     */
    var amount: Int = 8

    /**
     * avoids spawning structures in the playspace. default: true
     */
    var avoidCenter: Boolean = true


    /**
     * generating the Walls
     */
    override fun generate()  = run()
}