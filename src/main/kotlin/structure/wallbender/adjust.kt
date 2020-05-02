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
        wall.x = x
        wall.y = y
        wall.z = z
        wall.width = width
        wall.height = height
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
        if (fitZ != null) {
            wall.duration = (wall.z + (wall.duration.takeIf { it > 0 } ?: 0.0)) - fitZ!!
            wall.z = fitZ!!
        }
        if (fitHeight != null) {
            wall.y = (wall.y + (wall.height.takeIf { it > 0 } ?: 0.0)) - fitHeight!!
            wall.height = fitHeight!!
        }
        if (fitY != null) {
            wall.height = (wall.y + (wall.height.takeIf { it > 0 } ?: 0.0)) - fitY!!
            wall.y = fitY!!
        }
        if (fitX != null) {
            wall.width = (wall.x + (wall.width.takeIf { it > 0 } ?: 0.0)) - fitX!!
            wall.x = fitX!!
        }
        if (fitWidth != null) {
            wall.x = (wall.x + (wall.width.takeIf { it > 0 } ?: 0.0)) - fitWidth!!
            wall.width = fitWidth!!
        }
    }
}
