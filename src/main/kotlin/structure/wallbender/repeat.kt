package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.repeat(walls: List<SpookyWall>): List<SpookyWall> {
    var l = walls
    l=repeatWalls(l)
    l=repeatStruct(l)
    return l
}
fun WallStructure.repeatWalls(walls: List<SpookyWall>): List<SpookyWall> {
    val l = walls.toMutableList()
    for (i in 1 until repeatWalls){
        val temp = walls.map {
            it.copy(
                z=it.z+repeatAddZ*i+repeatAddStartTime*i,
                duration = it.duration+repeatAddDuration*i,
                x = it.x + repeatAddX*i+repeatAddStartRow*i,
                width = it.width+repeatAddWidth*i,
                y = it.y + repeatAddY*i + repeatAddStartHeight*i,
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
        temp = temp.map {
            it.copy(
                z = it.z + repeatAddZ * i + repeatAddStartTime * i,
                duration = it.duration + repeatAddDuration * i,
                x = it.x + repeatAddX * i + repeatAddStartRow * i,
                width = it.width + repeatAddWidth * i,
                y = it.y + repeatAddY * i + repeatAddStartHeight * i,
                height = it.height + repeatAddHeight * i,
                rotation = it.rotation + repeatAddDuration * i
            )
        }
        l+=temp
    }
    return l.toList()
}
