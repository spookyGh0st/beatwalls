package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

/**
 * adjust does all the adding, scaling and changing and fitting of the walls
 */
internal fun WallStructure.adjust(l:List<SpookyWall>): List<SpookyWall> {
    adjustChange(l)
    adjustScale(l)
    adjustAdd(l)
    adjustFit(l)
    return l
}

/**
 * changes the values of the walls
 */
internal fun WallStructure.adjustChange(l: List<SpookyWall>){
    l.forEach { it.duration = changeDuration?.invoke() ?: it.duration }
    l.forEach { it.z = changeStartTime?.invoke() ?: it.z }
    l.forEach { it.height = changeHeight?.invoke() ?: it.height }
    l.forEach { it.y = changeStartHeight?.invoke() ?: it.y }
    l.forEach { it.x = changeStartRow?.invoke() ?: it.x }
    l.forEach { it.width = changeWidth?.invoke() ?: it.width }
}

/**
 * scales the values of the walls
 */
internal fun WallStructure.adjustScale(l: List<SpookyWall>) {
    l.forEach { it.duration *= scaleDuration?.invoke() ?: 1.0 }
    l.forEach { it.z *= scaleStartTime?.invoke() ?: 1.0 }
    l.forEach { it.height *= scaleHeight?.invoke() ?: 1.0 }
    l.forEach { it.y *= scaleStartHeight?.invoke() ?: 1.0 }
    l.forEach { it.x *= scaleStartRow?.invoke() ?: 1.0 }
    l.forEach { it.width *= scaleWidth?.invoke() ?: 1.0 }

    if (scale != null) {
        spookyWalls.forEach {
            it.z *= scale as Double
            if (it.duration > 0)
                it.duration *= scale as Double
        }
    }
}

/**
 * adds to the values of the walls
 */
internal fun WallStructure.adjustAdd(l: List<SpookyWall>) {
    l.forEach { it.duration += addDuration?.invoke() ?: 0.0 }
    l.forEach { it.z += addStartTime?.invoke() ?: 0.0 }
    l.forEach { it.height += addHeight?.invoke() ?: 0.0 }
    l.forEach { it.y += addStartHeight?.invoke() ?: 0.0 }
    l.forEach { it.x += addStartRow?.invoke() ?: 0.0 }
    l.forEach { it.width += addWidth?.invoke() ?: 0.0 }
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
