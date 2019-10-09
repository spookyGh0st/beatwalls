package structure

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.triple
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int
import kotlin.math.*

/**
____  ____  _____ ____ ___    _    _      __        ___    _     _     ____ _____ ____  _   _  ____ _____ _   _ ____  _____ ____
/ ___||  _ \| ____/ ___|_ _|  / \  | |     \ \      / / \  | |   | |   / ___|_   _|  _ \| | | |/ ___|_   _| | | |  _ \| ____/ ___|
\___ \| |_) |  _|| |    | |  / _ \ | |      \ \ /\ / / _ \ | |   | |   \___ \ | | | |_) | | | | |     | | | | | | |_) |  _| \___ \
___) |  __/| |__| |___ | | / ___ \| |___    \ V  V / ___ \| |___| |___ ___) || | |  _ <| |_| | |___  | | | |_| |  _ <| |___ ___) |
|____/|_|   |_____\____|___/_/   \_\_____|    \_/\_/_/   \_\_____|_____|____/ |_| |_| \_\\___/ \____| |_|  \___/|_| \_\_____|____/
 */

sealed class SpecialWallStructure :WallStructure, CliktCommand(){
    override val name: String = this::class.simpleName!!
    override val wallList: ArrayList<Wall> = arrayListOf()

    fun remove(wall: Wall) {
        wallList.remove(wall)
    }

    fun remove(wall: Collection<Wall>) {
        wallList.removeAll(wall)
    }

    fun add(wall: Wall) {
        wallList.add(wall)
    }

    fun add(wall: Collection<Wall>) {
        wallList.addAll(wall)
    }
}

object Helix: SpecialWallStructure(){
    /**
     * How many Helixes get created. default: 1
     */
    val count by option().int().default(1)
    /**
     * The radius of the helix. default: 2
     */
    val radius by option().double().default(2.0)
    /**
     * Controlls, how many walls are created. Does NOT controll the exact number. default: 5
     */
    val fineTuning by option().int().default(5)
    /**
     * the starting rotation in degree. default: 0
     */
    val startRotation by option().double().default(0.0)
    /**
     * how often you want one helix to spin. default: 1
     */
    val rotationCount by option().double().default(1.0)
    /**
     * reverses the direction of the helix
     */
    val reverse by option("-r").flag()
    /**
     * the start Height default:2
     */
    val heightOffset by option().double().default(2.0)
    /**
     * speeds up or slows down the song exponential. 0-1 speeds up 1 - 999 slows down. play around with this if you want. default: 1
     */
    val speedChange by option().double().default(1.0)
    override fun run() {
        add(
            circle(
                count = count,
                radius = radius,
                fineTuning = fineTuning,
                startRotation = startRotation,
                rotationCount = rotationCount,
                heightOffset = heightOffset,
                speedChange = speedChange,
                helix = true,
                reverse = reverse
            )
        )

    }
}

/** draws a line given a centerPoint, an angle and a radius */
object CyanLine: SpecialWallStructure() {
    /**
     * The degree of the curve. default: 0.0
     */
    val degree by option("-d").double().default(0.0)
    /**
     * the length of the wall. default: 1.0
     */
    val length by option("-l").double().default(1.0)

    /**
     * the center x (row) position. default: 0
     */
    val centerX by option("-x").double().default(0.0)

    /**
     * the center y (height). default: 2.0
     */
    val centerY by option("-y").double().default(2.0)

    /**
     * The intensity of the walls. Leave blanc to automaticcly get a decent one
     */
    val amount by option("-a").int()
    override fun run() {

        val dgr = degree / 360 * (2* PI)
        val x1 = (centerX + cos(dgr))*length
        val x2 = (centerX - cos(dgr))*length
        val y1 = (centerY + sin(dgr))*length
        val y2 = (centerY - sin(dgr))*length

        add(
            line(x1, x2, y1, y2, 0.0, 0.0, amount)
        )
    }
}

/** creates normal stairways */
object StairWay: SpecialWallStructure() {
    /**
     * amount of walls created in stucture. default 4
     */
    val amount by option("-a").int().default(4)
    /**
     * the start height. default 0.0
     */
    val min  by option().double().default(0.0)
    /**
     * the end height. default 4.0
     */
    private val max by option().double().default(4.0)

    /**
     * the width of each wall. default 1.0
     */
    private val width by option("-w").double().default(1.0)

    /**
     * the startx of each wall. default 4.0
     */
    private val x by option("-x").double().default(4.0)
    override fun run() {
        for(i in 0 until amount){
            val height = abs(max-min) /amount
            val startHeight = if(min<=max)
                min + i* height
            else
                min - (i+1)*height
            add(Wall(4.0, 1.0 / amount, width, height, startHeight, i.toDouble() / amount))
        }
    }
}
/** Line */
object Line:SpecialWallStructure() {
    /**
     * startPoint,  x,y,z (startRow,startHeight,startTime)
     */
    val p1 by option("-s").double().triple().default(Triple(0.0,0.0,0.0))
    /**
     * endPoint,  x,y,z (startRow,startHeight,startTime)
     */
    val p2 by option("-s").double().triple().default(Triple(0.0,0.0,0.0))
    /**
     * amount, amount of walls created
     */
    val amount by option("-a").int().default(4)
    override fun run() {
        wallList.addAll(line(p1,p2,amount))
    }
}

