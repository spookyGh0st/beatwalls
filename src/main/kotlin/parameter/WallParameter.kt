package parameter

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int
import structure.Wall


class WallParameter: CliktCommand(name = "/bw") {
    /**
     * The beat offset moves the structure forwards/backwards, leave empty
     */
    val beat by option().double().default(0.0)

    /**
     * If this is set, this Times the Structure by moving it forward according to the njsoffset.
     */
    val time by option("-t").flag()

    /**
     * repeat the given Structure the given amount of times ( -r 4 creates 4 Structures)
     */
    val repeat by option("-r").int()
    /**
     * If repeat is set, this changes the created Gap. default: 1.0
     */
    val repeatGap by option("-rG").double().default(1.0)

    /**
     * changes the Wall to a fast Wall
     */
    val fast by option("-f").flag()
    /**
     * changes the Wall to a hyper Wall
     */
    val hyper by option("-h").flag()

    /**
     * Set This option, to duplicate to not duplicate the Wall when using mirror
     */
    val noDuplicate by option("-d").flag(default = false)
    /**
     * mirrors the wallStructure to the other Side
     */
    val mirror by option("-m").flag()
    /**
     * mirrors the wallstructur horizontal on y=2
     */
    val verticalMirror by option("-V").flag()
    /**
     * point mirrors the wall on the to x=0, y=2
     */
    val pointMirror by option("-p").flag()

    /**
     * scales the duration, for example -s 2 Scales all Walls to 2 times the duration. default:1
     */
    val scale by option("-s").double().default(1.0)
    /**
     * scales all walls horizontally. use with -S. default: 1
     */
    val verticalScale by option("-S").double().default(1.0)

    /**
     * extends all values to x value
     */
    val extendX by option().double()
    /**
     * extends all values to y value
     */
    val extendY by option().double()
    /**
     * extends all values to Z value
     */
    val extendZ by option().double()

    /**
     * splitts the wall to the given amount
     */
    val split by option().int()
    /**
     * fucks up the wall
     */
    val fuckUp by option().int()
    fun parseWalls(list: List<Wall>): List<Wall> {
        return list
    }
    override fun run() {    }
}
