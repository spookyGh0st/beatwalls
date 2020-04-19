package structure.specialStrucures

import structure.*
import structure.helperClasses.CuboidConstrains
import structure.helperClasses.Point
import structure.helperClasses.SpookyWall
import kotlin.math.roundToInt
import kotlin.random.Random

fun RandomCuboidLines.run(): List<SpookyWall> {
    val l= mutableListOf<SpookyWall>()
    val c = CuboidConstrains(p1, p2)
    val trueCount = count ?: c.duration.roundToInt()
    val r = Random(seed?: Random.nextInt())
    repeat(trueCount) {
        val z1 = c.sz + it.toDouble() / trueCount * c.duration
        val z2 = z1 + 1.0 / trueCount * c.duration
        // selects a random Side
        val randomSide = r.nextInt(randomSidePicker)
        // selects a random Section
        val randomSection = r.nextInt(1, sections)
        val randomX = c.sx + c.width / sections * randomSection
        val randomY = c.sy + c.height / sections * randomSection

        // selects the first Point of the line
        val lineP1 = when (randomSide) {
            0 -> Point(c.sx, randomY, z1)
            1 -> Point(c.ex, randomY, z1)
            2 -> Point(randomX, c.sy, z1)
            3 -> Point(randomX, c.ey, z1)
            else -> Point(0, 0, 0) // never happens
        }

        // calculates the end X and Y
        val randomEndX = lineP1.x + if (r.nextBoolean()) -c.width / sections else c.width / sections
        val randomEndY = lineP1.y + if (r.nextBoolean()) -c.height / sections else c.height / sections

        val lineP2 = when (randomSide) {
            0 -> lineP1.copy(y = randomEndY, z = z2)
            1 -> lineP1.copy(y = randomEndY, z = z2)
            2 -> lineP1.copy(x = randomEndX, z = z2)
            3 -> lineP1.copy(x = randomEndX, z = z2)
            else -> Point(0, 0, 0) // never happens
        }
        l.addAll(line(lineP1, lineP2, amount))
    }
    return l.toList()
}
