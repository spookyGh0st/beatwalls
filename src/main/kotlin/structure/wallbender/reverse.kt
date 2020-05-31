package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

internal fun WallStructure.reverse(l:List<SpookyWall>) {
    if (reverse) {
        val last = l.maxBy { spookyWall ->
            spookyWall.z + (spookyWall.duration.takeIf { it > 0 } ?: 0.0)
        }?.z ?: 0.0
        l.forEachIndexed { index,spookyWall ->
            activeWall = spookyWall
            this.i = index.toDouble()/l.size
            spookyWall.z = last - (spookyWall.z + (spookyWall.duration.takeIf { it > 0 } ?: 0.0))
        }
    }

    if (reverseX) {
        reverseX(l)
    }

    if (reverseY) {
        reverseY(l)
    }
}
fun WallStructure.reverseX(l: List<SpookyWall>) {
    val min = l.minXOrZero()
    val max = l.maxXOrZero()
    val center = min + ((max-min )/ 2)
    l.forEachIndexed { index, w ->
        activeWall = w
        this.i = index.toDouble()/l.size
        w.x = center + (center - w.x)
        w.width *= -1
    }
}

fun WallStructure.reverseY(l: List<SpookyWall>) {
    val min = l.minYOrZero()
    val max = l.maxYOrZero()
    val center = min + ((max - min) / 2)
    l.forEachIndexed { index, w ->
        activeWall = w
        this.i = index.toDouble()/l.size
        w.y = center + (center - w.y)
        w.height *= -1
    }
}

private fun List<SpookyWall>.maxX() =
    this.maxBy { spookyWall -> spookyWall.trueMaxPoint.x }?.trueMaxPoint?.x

private fun List<SpookyWall>.minX() =
    this.minBy { spookyWall -> spookyWall.trueLowestPoint.x }?.trueLowestPoint?.x

private fun List<SpookyWall>.maxY() =
    this.maxBy { spookyWall -> spookyWall.trueMaxPoint.y }?.trueMaxPoint?.y

private fun List<SpookyWall>.minY() =
    this.minBy { spookyWall -> spookyWall.trueLowestPoint.y }?.trueLowestPoint?.y

private fun List<SpookyWall>.maxXOrZero() =
    this.maxX()?: 0.0
private fun List<SpookyWall>.minXOrZero() =
    this.minX()?: 0.0
private fun List<SpookyWall>.maxYOrZero() =
    this.maxY()?: 0.0
private fun List<SpookyWall>.minYOrZero() =
    this.minY()?: 0.0
