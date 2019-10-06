package parameter

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int
import structure.Wall


class WallParameter: CliktCommand(name = "/bw") {
    val beat by option().double().default(0.0)

    val time by option("-t").flag()

    val fast by option("-f").flag()
    val hyper by option("-h").flag()

    val duplicate by option("-d").flag()
    val mirror by option("-m").flag()
    val verticalMirror by option("-V").flag()
    val pointMirror by option("-p").flag()

    val scale by option("-s").double().default(1.0)
    val verticalScale by option("-S").double().default(1.0)

    val extendX by option().double()
    val extendY by option().double()
    val extendZ by option().double()

    val split by option().int()
    val fuckUp by option().int()
    fun parseWalls(list: List<Wall>): List<Wall> {
        return list
    }
    override fun run() {    }
}
