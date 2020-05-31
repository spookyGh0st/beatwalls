package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.repeatStruct(walls:List<SpookyWall>): List<SpookyWall>{
    val l = walls.toMutableList()
    for(i in 1 until repeat){
        this.repeatCounter.value++
        var temp = this.generateWalls()
        temp = this.bendWalls(temp)
        l+=temp
    }
    return l.toList()
}
