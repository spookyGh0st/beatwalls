package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.bendWalls(walls: List<SpookyWall>): List<SpookyWall> {
    var l = walls.map { it.copy() }
    adjust(l)
    reverse(l)
    speeder(l)
    rotate(l)
    extraStuff(l)
    l = mirror(l)
    return l
}

fun WallStructure.generateBendAndRepeatWalls(): List<SpookyWall> {
    this.repeatCounter.value = 0
    var l = this.generateWalls()
    l= this.bendWalls(l)
    l = this.repeatStruct(l)
    return l
}

