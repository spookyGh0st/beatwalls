package structure.wallbender

import interpreter.property.specialProperties.Repeat
import structure.WallStructure
import structure.helperClasses.SpookyWall


fun WallStructure.repeat(x: (ws: WallStructure) -> List<SpookyWall>): List<SpookyWall> {
    val r  = repeatNeu.reversed()
    val i = r.iterator()
    return if(r.isEmpty())
        x(this)
    else
        r2(r.first(),r.subList(1,r.size),x)
}
fun WallStructure.r2(r: Repeat, n: List<Repeat>, x: (ws: WallStructure) -> List<SpookyWall>): List<SpookyWall> {
    val l = mutableListOf<SpookyWall>()
    for (i in 0 until r.max) {
        r.value = i
        if(n.isNotEmpty())
            l.addAll(r2(n.first(),n.subList(1,n.size),x))
        else
            l.addAll(x(this))

    }
    r.value = 0
    return l
}



//var l = walls
    //l=repeatWalls(l)
    //l=repeatStruct(l)
    //return l

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
