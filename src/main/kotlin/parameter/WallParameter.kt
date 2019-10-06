package parameter

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import structure.Wall


class WallParameter: CliktCommand(name = "/bw") {
    val beat by option().double().default(0.0)

    val time by option("-t").flag()

    val save by option().double()

    val fast by option("-f").flag()
    val hyper by option("-h").flag()
    val mirror by option("-m").flag()
    val mVertical by option("-V").flag()
    val mHorizontal by option("-H").flag()
    val scale by option("-s").double().default(1.0)
    val verticalScale by option("-v").double().default(1.0)
    val extendX by option().double()
    val extendY by option().double()
    val extendZ by option().double()

    fun parseWalls(list: List<Wall>): List<Wall> {
        return list
    }
    override fun run() {    }
}
