package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

internal fun WallStructure.adjust(l:List<SpookyWall>): List<SpookyWall> {
    adjustChange(l)
    adjustScale(l)
    adjustAdd(l)
    adjustFit(l)
    return l
}

internal fun WallStructure.adjustChange(l: List<SpookyWall>){
    l.forEach { it.duration = changeDuration?.invoke() ?: it.duration }
    l.forEach { it.startTime = changeStartTime?.invoke() ?: it.startTime }
    l.forEach { it.height = changeHeight?.invoke() ?: it.height }
    l.forEach { it.startHeight = changeStartHeight?.invoke() ?: it.startHeight }
    l.forEach { it.startRow = changeStartRow?.invoke() ?: it.startRow }
    l.forEach { it.width = changeWidth?.invoke() ?: it.width }
}

internal fun WallStructure.adjustScale(l: List<SpookyWall>) {
    l.forEach { it.duration *= scaleDuration?.invoke() ?: 1.0 }
    l.forEach { it.startTime *= scaleStartTime?.invoke() ?: 1.0 }
    l.forEach { it.height *= scaleHeight?.invoke() ?: 1.0 }
    l.forEach { it.startHeight *= scaleStartHeight?.invoke() ?: 1.0 }
    l.forEach { it.startRow *= scaleStartRow?.invoke() ?: 1.0 }
    l.forEach { it.width *= scaleWidth?.invoke() ?: 1.0 }

    if(scale!=null){
        spookyWalls.forEach {
            it.startTime *= scale as Double
            if (it.duration > 0)
                it.duration *= scale as Double
        }
    }
}
internal fun WallStructure.adjustAdd(l: List<SpookyWall>) {
    l.forEach { it.duration += addDuration?.invoke() ?: 0.0 }
    l.forEach { it.startTime += addStartTime?.invoke() ?: 0.0 }
    l.forEach { it.height += addHeight?.invoke() ?: 0.0 }
    l.forEach { it.startHeight += addStartHeight?.invoke() ?: 0.0 }
    l.forEach { it.startRow += addStartRow?.invoke() ?: 0.0 }
    l.forEach { it.width += addWidth?.invoke() ?: 0.0 }
}

internal fun WallStructure.adjustFit(l: List<SpookyWall>) {
    if (fitDuration != null)
        l.forEach {
            it.startTime = (it.startTime + (it.duration.takeIf { i -> i > 0 } ?: 0.0)) - fitDuration!!.invoke()
            it.duration = fitDuration!!.invoke()
        }
    if (fitStartTime != null)
        l.forEach {
            it.duration = (it.startTime + (it.duration.takeIf { i -> i > 0 } ?: 0.0)) - fitStartTime!!.invoke()
            it.startTime = fitStartTime!!.invoke()
        }
    if (fitHeight != null)
        l.forEach {
            it.startHeight = (it.startHeight + (it.height.takeIf { i -> i > 0 } ?: 0.0)) - fitHeight!!.invoke()
            it.height = fitHeight!!.invoke()
        }
    if (fitStartHeight != null)
        l.forEach {
            it.height = (it.startHeight + (it.height.takeIf { i -> i > 0 } ?: 0.0)) - fitStartHeight!!.invoke()
            it.startHeight = fitStartHeight!!.invoke()
        }
    if (fitStartRow != null)
        l.forEach {
            it.width = (it.startRow + (it.width.takeIf { i -> i > 0 } ?: 0.0)) - fitStartRow!!.invoke()
            it.startRow = fitStartRow!!.invoke()
        }
    if (fitWidth != null)
        l.forEach {
            it.startRow = (it.startRow + (it.width.takeIf { i -> i > 0 } ?: 0.0)) - fitWidth!!.invoke()
            it.width = fitWidth!!.invoke()
        }
    //extra
    if (scale != null) {
        l.forEach {
            it.startTime *= scale as Double
            if (it.duration > 0)
                it.duration *= scale as Double
        }
    }
}
