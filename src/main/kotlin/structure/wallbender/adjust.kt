package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

/**
 * adjust does all the adding, scaling and changing and fitting of the walls
 */
internal fun WallStructure.adjust(l:List<SpookyWall>): List<SpookyWall> {
    adjustChange(l)
    adjustFit(l)
    return l
}

/**
 * changes the values of the walls
 */
internal fun WallStructure.adjustChange(l: List<SpookyWall>){
    for ((i, wall) in l.withIndex()) {
        this.constantController.wall = wall
        this.constantController.progress = i.toDouble()/l.size
        wall.x = z
        wall.y = y
        wall.z = z
        wall.width = w
        wall.height = h
        wall.duration = d

    }
}

/**
 * fits the values of the walls
 */
internal fun WallStructure.adjustFit(l: List<SpookyWall>) {
    if (fitDuration != null)
        l.forEach {
            it.z = (it.z + (it.duration.takeIf { i -> i > 0 } ?: 0.0)) - fitDuration!!.invoke()
            it.duration = fitDuration!!.invoke()
        }
    if (fitStartTime != null)
        l.forEach {
            it.duration = (it.z + (it.duration.takeIf { i -> i > 0 } ?: 0.0)) - fitStartTime!!.invoke()
            it.z = fitStartTime!!.invoke()
        }
    if (fitHeight != null)
        l.forEach {
            it.y = (it.y + (it.height.takeIf { i -> i > 0 } ?: 0.0)) - fitHeight!!.invoke()
            it.height = fitHeight!!.invoke()
        }
    if (fitStartHeight != null)
        l.forEach {
            it.height = (it.y + (it.height.takeIf { i -> i > 0 } ?: 0.0)) - fitStartHeight!!.invoke()
            it.y = fitStartHeight!!.invoke()
        }
    if (fitStartRow != null)
        l.forEach {
            it.width = (it.x + (it.width.takeIf { i -> i > 0 } ?: 0.0)) - fitStartRow!!.invoke()
            it.x = fitStartRow!!.invoke()
        }
    if (fitWidth != null)
        l.forEach {
            it.x = (it.x + (it.width.takeIf { i -> i > 0 } ?: 0.0)) - fitWidth!!.invoke()
            it.width = fitWidth!!.invoke()
        }
    //extra
    if (scale != null) {
        l.forEach {
            it.z *= scale as Double
            if (it.duration > 0)
                it.duration *= scale as Double
        }
    }
}
