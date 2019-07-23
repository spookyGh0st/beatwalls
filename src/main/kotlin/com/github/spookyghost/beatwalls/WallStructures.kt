package com.github.spookyghost.beatwalls

fun type(wallH: Int, startH: Int) = wallH * 1000 + startH+4001

fun ArrayList<_obstacles>.floor(width:Double,length:Double) {
    this.add(_obstacles(0.0, 3000 - (width / 2).toInt(), type(0, 0), length, ((width + 1) * 1000).toInt()) )
}

fun ArrayList<_obstacles>.fastCathedral(spawnDistance: Int){
    this.addAll(listOf(
        _obstacles(spawnDistance.toDouble(),-4,type(4000,0),spawnDistance.toDouble(),1),
        _obstacles(spawnDistance.toDouble(),8,type(4000,0), spawnDistance.toDouble(),1)
        )
    )
}

fun ArrayList<_obstacles>.text(text:String){
    text.toCharArray().forEach {
       this.addAll(it.toObstacle())
        TODO("spacing")
    }
}



fun Char.toObstacle():ArrayList<_obstacles> =
    TODO()

