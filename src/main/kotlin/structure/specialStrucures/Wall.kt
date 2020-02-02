package structure.specialStrucures

import structure.SpookyWall
import structure.Wall
import structure.add

fun Wall.run(){
    add(
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