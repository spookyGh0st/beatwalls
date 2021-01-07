package structure.wallbender

import structure.wallStructures.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.repeat(walls: List<SpookyWall>): List<SpookyWall> {
    var l = walls
    l=repeatStruct(l)
    return l
}

fun WallStructure.repeatStruct(walls:List<SpookyWall>): List<SpookyWall>{
    this.structureState.count = 0
    val l = walls.toMutableList()
    for(i in 1 until repeat()){
        this.structureState.count ++
        var temp = this.create()
        temp = this.bendWalls(temp)
        l+=temp
    }
    return l.toList()
}
