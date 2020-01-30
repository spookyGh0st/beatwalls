package structure

import mu.KotlinLogging
import java.io.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
private val logger = KotlinLogging.logger {}

/** this is for getting the acutal Walls */
fun WallStructure.walls(): ArrayList<SpookyWall> {
    //order is important, dont question it
    run()
    adjust()
    repeat()
    mirror()
    logger.info { "Added ${this::class.simpleName?:"undefined Structure"} with ${spookyWalls.size} walls on beat $beat." }
    return spookyWalls
}

fun WallStructure.copyWalls() :ArrayList<SpookyWall> = ArrayList((spookyWalls.map { it.copy() }))

fun WallStructure.deepCopy():WallStructure = deepCopyBySer(this)

fun WallStructure.add(w:Collection<SpookyWall>){
    spookyWalls.addAll(w)
}

fun WallStructure.add(w:SpookyWall){
    spookyWalls.add(w)
}

fun WallStructure.mirror(){
    var otherSpookyWalls: ArrayList<SpookyWall> = arrayListOf()
    when(mirror){
        1->spookyWalls.forEach { it.mirror() }
        2-> {otherSpookyWalls = copyWalls();spookyWalls.forEach { it.mirror() }}
        3->spookyWalls.forEach {it.verticalMirror()}
        4-> {otherSpookyWalls = copyWalls();spookyWalls.forEach { it.verticalMirror() }}
        5->spookyWalls.forEach {it.pointMirror()}
        6-> {otherSpookyWalls = copyWalls();spookyWalls.forEach { it.pointMirror() }}
        7-> {otherSpookyWalls = copyWalls()
            spookyWalls.forEach { it.verticalMirror() }
            spookyWalls.addAll(otherSpookyWalls)
            otherSpookyWalls =copyWalls()
            spookyWalls.forEach { it.mirror()}}
        8-> {otherSpookyWalls =copyWalls()
            spookyWalls.forEach { it.pointMirror() }
            spookyWalls.addAll(otherSpookyWalls)
            otherSpookyWalls = copyWalls()
            spookyWalls.forEach { it.mirror()}}
    }
    spookyWalls.addAll(otherSpookyWalls)
    //todo change the mirror 8 to allow helix
    //todo add a mirror to mirror in between tho Structure
}

fun WallStructure.repeat(){
    val tempWalls  = arrayListOf<SpookyWall>()
    for (i in 1 until repeat){
        val temp = this.copyWalls()
        temp.forEach {
            it.startTime+=repeatAddZ*i
            it.startRow += repeatAddX*i
            it.startHeight += repeatAddY*i

            it.startTime+=repeatAddStartTime*i
            it.startHeight+=repeatAddStartHeight*i
            it.startRow+=repeatAddStartRow*i
            it.width+=repeatAddWidth*i
            it.duration+=repeatAddDuration*i
            it.height+=repeatAddHeight*i
        }
        tempWalls.addAll(temp)
    }
    add(tempWalls)
}

fun WallStructure.adjust(){
    //change
    spookyWalls.forEach { it.duration = changeDuration?.invoke()?:it.duration }
    spookyWalls.forEach { it.startTime = changeStartTime?.invoke()?:it.startTime }
    spookyWalls.forEach { it.height = changeHeight?.invoke()?:it.height }
    spookyWalls.forEach { it.startHeight = changeStartHeight?.invoke()?:it.startHeight}
    spookyWalls.forEach { it.startRow = changeStartRow?.invoke()?:it.startRow}
    spookyWalls.forEach { it.width = changeWidth?.invoke()?:it.width }

    //scale
    spookyWalls.forEach { it.duration *= scaleDuration?.invoke()?:1.0 }
    spookyWalls.forEach { it.startTime *= scaleStartTime?.invoke()?:1.0 }
    spookyWalls.forEach { it.height *= scaleHeight?.invoke()?:1.0 }
    spookyWalls.forEach { it.startHeight *= scaleStartHeight?.invoke()?:1.0 }
    spookyWalls.forEach { it.startRow *= scaleStartRow?.invoke()?:1.0 }
    spookyWalls.forEach { it.width *= scaleWidth?.invoke()?:1.0 }

    //add
    spookyWalls.forEach { it.duration += addDuration?.invoke()?:0.0 }
    spookyWalls.forEach { it.startTime += addStartTime?.invoke()?:0.0 }
    spookyWalls.forEach { it.height += addHeight?.invoke()?:0.0 }
    spookyWalls.forEach { it.startHeight += addStartHeight?.invoke()?:0.0 }
    spookyWalls.forEach { it.startRow += addStartRow?.invoke()?:0.0 }
    spookyWalls.forEach { it.width += addWidth?.invoke()?:0.0 }

    //fit
    //todo fix, there fucks with negative width
    if (fitDuration!=null)
        if (fitHeight!=null)
            spookyWalls.forEach {
                it.startTime = (it.startTime+(it.duration.takeIf { i -> i > 0 }?:0.0)) - fitDuration!!.invoke()
                it.duration = fitDuration!!.invoke()
            }
    if (fitStartTime!=null)
        spookyWalls.forEach {
            it.duration = (it.startTime+(it.duration.takeIf { i -> i > 0 }?:0.0)) - fitStartTime!!.invoke()
            it.startTime = fitStartTime!!.invoke()
        }
    if (fitHeight!=null)
        spookyWalls.forEach {
            it.startHeight = (it.startHeight+(it.height.takeIf { i -> i > 0 }?:0.0)) - fitHeight!!.invoke()
            it.height = fitHeight!!.invoke()
        }
    if (fitStartHeight!=null)
        spookyWalls.forEach {
            it.height = (it.startHeight+(it.height.takeIf { i -> i > 0 }?:0.0)) - fitStartHeight!!.invoke()
            it.startHeight = fitStartHeight!!.invoke()
        }
    if (fitStartRow!=null)
        spookyWalls.forEach {
            it.width = (it.startRow+(it.width.takeIf { i -> i > 0 }?:0.0)) - fitStartRow!!.invoke()
            it.startRow = fitStartRow!!.invoke()
        }
    if (fitWidth!=null)
        spookyWalls.forEach {
            it.startRow = (it.startRow+(it.width.takeIf { i -> i > 0 }?:0.0)) - fitWidth!!.invoke()
            it.width = fitWidth!!.invoke()
        }
    //extra
    if(scale!=null){
        spookyWalls.forEach {
            it.startTime *= scale as Double
            if (it.duration > 0)
                it.duration *= scale as Double
        }
    }

    if(speeder != null){
        val maxZ = spookyWalls.maxBy { it.trueMaxPoint.z }?.trueMaxPoint?.z ?: 0.0
        spookyWalls.forEach { wall ->
            wall.startTime = wall.startTime.pow(speeder!!)
            if (wall.duration > 0)
                wall.duration = wall.duration.pow(speeder!!)
        }

        val newMaxZ =spookyWalls.maxBy { it.trueMaxPoint.z }?.trueMaxPoint?.z ?: 0.0
        val mult = 1/(newMaxZ)*maxZ
        spookyWalls.forEach {
            it.startTime *= mult
            if(it.duration > 0)
                it.duration *= mult
        }
    }

    if(reverse){
        val last = spookyWalls.maxBy { spookyWall -> spookyWall.startTime+(spookyWall.duration.takeIf { it > 0 }?:0.0) }?.startTime?:0.0
        spookyWalls.forEach { spookyWall -> spookyWall.startTime = last-(spookyWall.startTime+(spookyWall.duration.takeIf { it >0  }?:0.0)) }
    }

    if(reverseX){
        spookyWalls.reverseX()
    }

    if(reverseY){
        spookyWalls.reverseY()
    }
}

fun ArrayList<SpookyWall>.reverseX(){
    val min = this.minXOrZero()
    val max = this.maxXOrZero()
    val center = min + ((max-min )/ 2)
    this.forEach {
        it.startRow = center + (center - it.startRow)
        it.width *= -1
    }
}

fun ArrayList<SpookyWall>.reverseY() {
    val min = this.minYOrZero()
    val max = this.maxYOrZero()
    val center = min + ((max - min) / 2)
    this.forEach {
        it.startHeight = center + (center - it.startHeight)
        it.height *= -1
    }
}
class CuboidConstrains(p1: Point, p2: Point) {
    val sx = min(p1.x, p2.x)
    val ex = max(p1.x, p2.x).coerceAtLeast(sx + 0.0000001)
    val sy = min(p1.y, p2.y)
    val ey = max(p1.y, p2.y).coerceAtLeast(sy + 0.0000001)
    val sz = min(p1.z, p2.z)
    val ez = max(p1.z, p2.z).coerceAtLeast(sz + 0.0000001)
    val duration = ez - sz
    val height = ey - sy
    val width = ex - sx
}

private fun ArrayList<SpookyWall>.maxX() =
    this.maxBy { spookyWall -> spookyWall.trueMaxPoint.x }?.trueMaxPoint?.x

private fun ArrayList<SpookyWall>.minX() =
    this.minBy { spookyWall -> spookyWall.trueLowestPoint.x }?.trueLowestPoint?.x

private fun ArrayList<SpookyWall>.maxY() =
    this.maxBy { spookyWall -> spookyWall.trueMaxPoint.y }?.trueMaxPoint?.y

private fun ArrayList<SpookyWall>.minY() =
    this.minBy { spookyWall -> spookyWall.trueLowestPoint.y }?.trueLowestPoint?.y

private fun ArrayList<SpookyWall>.maxXOrZero() =
    this.maxX()?: 0.0
private fun ArrayList<SpookyWall>.minXOrZero() =
    this.minX()?: 0.0
private fun ArrayList<SpookyWall>.maxYOrZero() =
    this.maxY()?: 0.0
private fun ArrayList<SpookyWall>.minYOrZero() =
    this.minY()?: 0.0

/**
 * workaround for deep copy
 */
private fun <T : Serializable> deepCopyBySer(obj: T): T {
    val baos = ByteArrayOutputStream()
    val oos  = ObjectOutputStream(baos)
    oos.writeObject(obj)
    oos.close()
    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois  = ObjectInputStream(bais)
    @Suppress("unchecked_cast")
    return ois.readObject() as T
}
