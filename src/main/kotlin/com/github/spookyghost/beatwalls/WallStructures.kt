package com.github.spookyghost.beatwalls

interface WallStruct {
    val set: ArrayList<_obstacles>
    fun type(wallH:Int,startH:Int) = wallH * 1000 + startH*1000
}

class Floor(length: Double= 4.0, width: Double=2.0):WallStruct{
    override val set: ArrayList<_obstacles> =
        arrayListOf(
            _obstacles(0.0,3000 - (width/2).toInt(),type(0,0),length,((width+1)*1000).toInt())
        )
}

class storage():WallStruct{
    override var set: ArrayList<_obstacles> = arrayListOf()

}

