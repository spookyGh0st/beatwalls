package structure.specialStrucures

import structure.helperClasses.SpookyWall
import structure.Wall

fun Wall.run(): List<SpookyWall> {
    return listOf(
        SpookyWall(
            startRow = startRow,
            duration = duration,
            width = width,
            height = height,
            startHeight = startHeight,
            startTime = startTime
        )
    )
}