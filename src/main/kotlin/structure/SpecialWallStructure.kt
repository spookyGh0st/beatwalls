package structure

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int
import parameter.Command
import kotlin.math.*
import kotlin.random.Random

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
    val startPoint by option("-s").double().triple().default(Triple(0.0,0.0,0.0))
    /**
     * endPoint,  x,y,z (startRow,startHeight,startTime)
     */
    val endPoint by option("-e").double().triple().default(Triple(0.0,0.0,0.0))
    /**
     * amount, amount of walls created
     */
    val amount by option("-a").int().default(4)
    override fun run() {
        add(line(startPoint,endPoint,amount))
    }
}


///** Curve Object - when called, creates a example curve */
//object Curve: SpecialWallStructure() {
//    /**
//     * startPoint,  x,y,z (startRow,startHeight,startTime)
//     */
//    val startPoint by option("-s").double().triple().default(Triple(0.0,0.0,0.0))
//    /**
//     * endPoint,  x,y,z (startRow,startHeight,startTime)
//     */
//    val endPoint by option("-e").double().triple().default(Triple(0.0,0.0,0.0))
//    /**
//     * amount, amount of walls created
//     */
//    val amount by option("-a").int().default(4)
//    /**
//     * firstHandler1, use this to contoll the angle. this is also a point x,y,z (startRow,startHeight,startTime)
//     */
//    val firstHandler by option("-f").double().triple().default(Triple(0.0,0.0,0.0))
//    /**
//     * secondHandler, use this to contoll the angle
//     */
//    val secondHandler by option("-s").double().triple().default(Triple(0.0,0.0,0.0))
//
//    override fun run() {
//        add(curve(startPoint.toPoint(), firstHandler.toPoint(), secondHandler.toPoint() , endPoint.toPoint(), amount))
//
//    }
//}

/** RandomBox Object - when called, creates a random box with the given amount per tick and the given ticks per beat */
object RandomBox: SpecialWallStructure() {
    val list = arrayListOf<Wall>()
    /**
     * how many walls are created per wall. default: 4
     */
    val wallsPerTick by option("-w").int().default(4)
    /**
     * how many ticks there are. default: 4
     */
    val tickAmount by option("-t").int().default(4)
    /**
     * the intensity, how many walls pew side. default: 8
     */
    val intensity by option("-i").int().default(8)

    /**
     * if you want to create lines or normal walls. default: false
     */
    val line by option("-l").flag()
    override fun run() {

        val allWalls: ArrayList<Wall> = getBoxList(intensity)

        for (start in 0 until tickAmount) {
            val tempList = ArrayList(allWalls.map { it.copy() })
            repeat(wallsPerTick) {
                val w = tempList.random()
                w.startTime = start.toDouble() / tickAmount
                if (line){
                    w.height = 0.0
                    w.width = 0.0
                }
                add(w)
                tempList.remove(w)
            }
        }
    }
}

/** gets very small noise in the area -4 .. 4 */
object RandomNoise: SpecialWallStructure() {
    /**
     * how many walls per beat. default: 8
     */
    val interval by option("-i").int().default(8)

    /**
     * how many walls per intervall tick. default: 2
     */
    val amount by option("-a").int().default(2)

    /**
     * the width of the whole structure, default 12
     */
    val width by option("-w").double().default(12.0)

    /**
     * obstruct, obstruct the player space
     */
    val obstruct by option("-o").flag(default = false)

    /**
     * the size of each wall. default: 0.0
     */
    val size by option("-s").double().default(0.001)
    override fun run() {
        println(width)
        for(i in 0 until interval){
            repeat(amount) {

                val halfWidth = width / 2
                val startRow =
                    if (!obstruct)
                        if (Random.nextBoolean())
                            - Random.nextDouble(2.0, 0.0 + halfWidth)
                        else
                            Random.nextDouble(2.0, 0.0 + halfWidth)
                    else
                        Random.nextDouble(-halfWidth, halfWidth)


                val startHeight = if (!obstruct)
                    Random.nextDouble(3.0, 5.3)
                else
                    Random.nextDouble(5.3)

                val tempwidth = Random.nextDouble(-size / 2, size / 2)
                val height = Random.nextDouble(-size / 2, size / 2)

                val tempO = Wall(
                    startRow = startRow,
                    duration = 0.0,
                    width = tempwidth,
                    height = height,
                    startHeight = startHeight,
                    startTime = i.toDouble()/ interval
                )
                add(tempO)
            }
        }
    }
}
object SideWave: SpecialWallStructure(){
    /**
     * how many walls will be created. default: 8.0
     */
    val amount by option().int().default(8)
    override fun run() {
        for(i in 0 until (amount)){
            val y = i/amount*(2* PI)
            val nY = (i+1)/amount*(2* PI)

                add(
                Wall(
                    duration = 1 / amount.toDouble(),
                    height = abs(cos(nY) - cos(y)),
                    startHeight = 1 - cos(y),
                    startRow = 3.0,
                    width = 0.5,
                    startTime = i / amount.toDouble()
                )
            )
        }
    }
}

/**
 * writes default text from the custom WallStructures. Currently only A-Z supported
 */
object Text: SpecialWallStructure() {
    /**
     * the input text. no spaces or numbers. just A-Z
     */
    val text by option("-t").default("")
    /**
     * the gap between the letters. default 2.5
     */
    val gap by option("-g").double().default(2.5)
    /**
     * the center of the word, default 0.0
     */
    val center by option("-c").double().default(0.0)
    override fun run() {
        var x=  center-(text.length-1) * gap / 2 - gap/2
        for(c in text){
            val tempList =StructureManager.walls(Command(0.0,c.toString()),null)
            tempList.forEach { it.startRow += x }
            x+=gap
            add(tempList)
        }
    }
}
