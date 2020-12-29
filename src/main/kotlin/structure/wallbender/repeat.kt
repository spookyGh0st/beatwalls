package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.repeat(walls: List<SpookyWall>): List<SpookyWall> {
    var l = walls
    l=repeatWalls(l)
    l=repeatStruct(l)
    return l
}
// todo process
fun WallStructure.repeatWalls(walls: List<SpookyWall>): List<SpookyWall> {
    val l = walls.toMutableList()
    for (i in 1 until repeatWalls){
        val temp = walls.mapIndexed { j, it ->
            val p = j.toDouble()/walls.size
            it.copy(
                startTime=it.startTime+repeatAddZ()*i+repeatAddStartTime*i,
                duration = it.duration+repeatAddDuration*i,
                startRow = it.startRow + repeatAddX*i+repeatAddStartRow*i,
                width = it.width+repeatAddWidth*i,
                startHeight = it.startHeight + repeatAddY*i + repeatAddStartHeight*i,
                height =  it.height+repeatAddHeight*i,
                rotation = it.rotation+repeatAddRotation * i
            )
        }
        l+=temp
    }
    return l.toList()
}

fun WallStructure.repeatStruct(walls:List<SpookyWall>): List<SpookyWall>{
    val l = walls.toMutableList()
    for(i in 1 until repeat){
        var temp = this.generateWalls()
        temp = this.bendWalls(temp)
        temp = temp.mapIndexed { j, it ->
            val p = j.toDouble()/walls.size
            it.copy(
                startTime = it.startTime + repeatAddZ() * i + repeatAddStartTime * i,
                duration = it.duration + repeatAddDuration * i,
                startRow = it.startRow + repeatAddX * i + repeatAddStartRow * i,
                width = it.width + repeatAddWidth * i,
                startHeight = it.startHeight + repeatAddY * i + repeatAddStartHeight * i,
                height = it.height + repeatAddHeight * i,
                rotation = it.rotation + repeatAddDuration * i
            )
        }
        l+=temp
    }
    return l.toList()
}
