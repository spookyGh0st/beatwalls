package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall


/**
 * changes the individual values of the walls
 */
internal fun WallStructure.adjust(l: List<SpookyWall>){
    for ((i, wall) in l.withIndex()) {
        this.constantController.wall = wall
        this.constantController.progress = i.toDouble()/l.size
        wall.x = z
        wall.y = y
        wall.z = z
        wall.width = w
        wall.height = h
        wall.duration = d

        wall.z *= scaleZ
        if (wall.duration > 0)
            wall.duration *= scaleZ
        wall.x *= scaleX
        wall.width *= scaleX
        wall.y *= scaleY
        wall.height *= scaleY

        if (fitDuration != null) {
            wall.z = (wall.z + (wall.duration.takeIf { it > 0 } ?: 0.0)) - fitDuration!!
            wall.duration = fitDuration!!
        }
        if (fitStartTime != null) {
            wall.duration = (wall.z + (wall.duration.takeIf { it > 0 } ?: 0.0)) - fitStartTime!!
            wall.z = fitStartTime!!
        }
        if (fitHeight != null) {
            wall.y = (wall.y + (wall.height.takeIf { it > 0 } ?: 0.0)) - fitHeight!!
            wall.height = fitHeight!!
        }
        if (fitStartHeight != null) {
            wall.height = (wall.y + (wall.height.takeIf { it > 0 } ?: 0.0)) - fitStartHeight!!
            wall.y = fitStartHeight!!
        }
        if (fitStartRow != null) {
            wall.width = (wall.x + (wall.width.takeIf { it > 0 } ?: 0.0)) - fitStartRow!!
            wall.x = fitStartRow!!
        }
        if (fitWidth != null) {
            wall.x = (wall.x + (wall.width.takeIf { it > 0 } ?: 0.0)) - fitWidth!!
            wall.width = fitWidth!!
        }
    }
}
