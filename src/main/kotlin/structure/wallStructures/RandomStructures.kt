package structure.wallStructures

import beatwalls.errorExit
import structure.helperClasses.CuboidConstrains
import structure.helperClasses.Point
import structure.helperClasses.SpookyWall
import kotlin.random.Random

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
    override fun generate(): MutableList<SpookyWall> {
        when {
            structures.isEmpty() -> errorExit { "The structureList of this at beat $beat is empty, check if you have a typo " }
            structures.contains(this) -> errorExit { "NO RECURSION, BAD BOY" }
        }
        val l= mutableListOf<SpookyWall>()
        val cc = CuboidConstrains(p1, p2, seed?.invoke()?: Random.nextInt())
        for(i in 0 until amount){
            for(w in structures){
                val t = w.generate()
                t.forEach { it.startTime += (w.beat()) }
                val rp = cc.random(avoidCenter)
                t.forEach { it.startRow += rp.x }
                t.forEach { it.startHeight += rp.y }
                t.forEach { it.startTime += i.toDouble()/ amount * cc.duration}
                l.addAll(t)
            }
        }
        return l
    }
}