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
    l.forEach { it.startRow = changeX?.invoke() ?: it.startRow }
    l.forEach { it.width = changeWidth?.invoke() ?: it.width }
    l.forEach { it.startHeight = changeY?.invoke() ?: it.startHeight }
    l.forEach { it.height = changeHeight?.invoke() ?: it.height }
    l.forEach { it.startTime = changeZ?.invoke() ?: it.startTime }
    l.forEach { it.duration = changeDuration?.invoke() ?: it.duration }
}

/**
 * scales the values of the walls
 */
internal fun WallStructure.adjustScale(l: List<SpookyWall>) {
    l.forEach { it.duration *= scaleDuration.invoke() }
    l.forEach { it.startTime *= scaleZ.invoke() }
    l.forEach { it.height *= scaleHeight.invoke() }
    l.forEach { it.startHeight *= scaleY.invoke() }
    l.forEach { it.startRow *= scaleZ.invoke() }
    l.forEach { it.width *= scaleWidth.invoke() }

    if (scale != null) {
        l.forEach {
            it.startTime *= scale!!.invoke()
            if (it.duration > 0)
                it.duration *= scale!!.invoke()
        }
    }
}

/**
 * adds to the values of the walls
 */
internal fun WallStructure.adjustAdd(l: List<SpookyWall>) {
    l.forEach { it.startRow += addX.invoke() }
    l.forEach { it.width += addWidth.invoke() }
    l.forEach { it.startHeight += addY.invoke() }
    l.forEach { it.height += addHeight.invoke() }
    l.forEach { it.startTime += addZ.invoke() }
    l.forEach { it.duration += addDuration.invoke() }
}

/**
 * fits the values of the walls
 */
internal fun WallStructure.adjustFit(l: List<SpookyWall>) {
    if (fitX != null)
        l.forEach {
            it.width = (it.startRow + (it.width.takeIf { i -> i > 0 } ?: 0.0)) - fitX!!.invoke()
            it.startRow = fitX!!.invoke()
        }
    if (fitWidth != null)
        l.forEach {
            it.startRow = (it.startRow + (it.width.takeIf { i -> i > 0 } ?: 0.0)) - fitWidth!!.invoke()
            it.width = fitWidth!!.invoke()
        }
    if (fitY != null)
        l.forEach {
            it.height = (it.startHeight + (it.height.takeIf { i -> i > 0 } ?: 0.0)) - fitY!!.invoke()
            it.startHeight = fitY!!.invoke()
        }
    if (fitHeight != null)
        l.forEach {
            it.startHeight = (it.startHeight + (it.height.takeIf { i -> i > 0 } ?: 0.0)) - fitHeight!!.invoke()
            it.height = fitHeight!!.invoke()
        }
    if (fitZ != null)
        l.forEach {
            it.duration = (it.startTime + (it.duration.takeIf { i -> i > 0 } ?: 0.0)) - fitZ!!.invoke()
            it.startTime = fitZ!!.invoke()
        }
    if (fitDuration != null)
        l.forEach {
            it.startTime = (it.startTime + (it.duration.takeIf { i -> i > 0 } ?: 0.0)) - fitDuration!!.invoke()
            it.duration = fitDuration!!.invoke()
        }
}
