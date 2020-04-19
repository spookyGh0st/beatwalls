package structure.specialStrucures

import structure.helperClasses.SpookyWall
import structure.Wall
import structure.helperClasses.CuboidConstrains

fun Wall.run(): List<SpookyWall> {
    val cc = CuboidConstrains(p1,p2)
    return listOf(
        SpookyWall(
            x = cc.sx,
            duration = cc.duration,
            width = cc.width,
            height = cc.height,
            y = cc.sy,
            z = cc.sz
        )
    )
}