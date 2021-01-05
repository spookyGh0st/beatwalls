package structure.wallStructures

import structure.helperClasses.Point
import structure.specialStrucures.run

/**
 * draws multiple lines around Sections of the cuboid.
 */
class RandomCuboidLines : WallStructure() {
    /**
     * the first corner of the cuboid. Default is -2,0,0
     */
    var p1: Point =
        Point(-2, 0, 0)

    /**
     * the second corner of the cuboid. Default is 2,4,8
     */
    var p2: Point = Point(2, 4, 4)

    /**
     * The amount of walls per line. Default is 8
     */
    var amount: Int = 8

    /**
     * The amount of lines that will be created. Defaults to the duration
     */
    var count: Int? = null

    /**
     * In how many sections will each side/floor be splitted. Must be at least 3. Default: 4
     */
    var sections: Int = 4

    /**
     * 2 = only sides, 4 - bottom and top aswell
     */
    var randomSidePicker: Int = 4
    /**
     * generating the Walls
     */
    override fun generate() = run()
}