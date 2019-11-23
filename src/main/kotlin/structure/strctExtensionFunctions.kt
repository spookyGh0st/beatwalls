package structure

import java.io.*
import kotlin.math.pow

/** this is for getting the acutal Walls */
fun WallStructure.walls(): ArrayList<SpookyWall> {
    //order is important, dont question it
    run()
    adjust()
    repeat()
    mirror()
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
        }
        tempWalls.addAll(temp)
    }
    add(tempWalls)
}

fun WallStructure.adjust(){
    //change
    if (changeDuration!=null)
        spookyWalls.forEach { it.duration = changeDuration as Double }
    if (changeStartTime!=null)
        spookyWalls.forEach { it.startTime = changeStartTime as Double }
    if (changeHeight!=null)
        spookyWalls.forEach { it.height = changeHeight as Double }
    if (changeStartHeight!=null)
        spookyWalls.forEach { it.startHeight = changeStartHeight as Double }
    if (changeStartRow!=null)
        spookyWalls.forEach { it.startRow = changeStartRow as Double }
    if (changeWidth!=null)
        spookyWalls.forEach { it.width = changeWidth as Double }

    //scale
    if (scaleDuration!=null)
        spookyWalls.forEach { it.duration *= scaleDuration as Double }
    if (scaleStartTime!=null)
        spookyWalls.forEach { it.startTime *= scaleStartTime as Double }
    if (scaleHeight!=null)
        spookyWalls.forEach { it.height *= scaleHeight as Double }
    if (scaleStartHeight!=null)
        spookyWalls.forEach { it.startHeight *= scaleStartHeight as Double }
    if (scaleStartRow!=null)
        spookyWalls.forEach { it.startRow *= scaleStartRow as Double }
    if (scaleWidth!=null)
        spookyWalls.forEach { it.width *= scaleWidth as Double }

    //add
    if (addDuration!=null)
        spookyWalls.forEach { it.duration += addDuration as Double }
    if (addStartTime!=null)
        spookyWalls.forEach { it.startTime += addStartTime as Double }
    if (addHeight!=null)
        spookyWalls.forEach { it.height += addHeight as Double }
    if (addStartHeight!=null)
        spookyWalls.forEach { it.startHeight += addStartHeight as Double }
    if (addStartRow!=null)
        spookyWalls.forEach { it.startRow += addStartRow as Double }
    if (addWidth!=null)
        spookyWalls.forEach { it.width += addWidth as Double }

    //fit
    //todo fix, there fucks with negative width
    if (fitDuration!=null)
        if (fitHeight!=null)
            spookyWalls.forEach {
                it.startTime = (it.startTime+(it.duration.takeIf { i -> i > 0 }?:0.0)) - fitDuration as Double
                it.duration = fitDuration as Double
            }
    if (fitStartTime!=null)
        spookyWalls.forEach {
            it.duration = (it.startTime+(it.duration.takeIf { i -> i > 0 }?:0.0)) - fitStartTime as Double
            it.startTime = fitStartTime as Double
        }
    if (fitHeight!=null)
        spookyWalls.forEach {
            it.startHeight = (it.startHeight+(it.height.takeIf { i -> i > 0 }?:0.0)) - fitHeight as Double
            it.height = fitHeight as Double
        }
    if (fitStartHeight!=null)
        spookyWalls.forEach {
            it.height = (it.startHeight+(it.height.takeIf { i -> i > 0 }?:0.0)) - fitStartHeight as Double
            it.startHeight = fitStartHeight as Double
            }
    if (fitStartRow!=null)
        spookyWalls.forEach {
            it.width = (it.startRow+(it.width.takeIf { i -> i > 0 }?:0.0)) - fitStartRow as Double
            it.startRow = fitStartRow as Double
        }
    if (fitWidth!=null)
        spookyWalls.forEach {
            it.startRow = (it.startRow+(it.width.takeIf { i -> i > 0 }?:0.0)) - fitWidth as Double
            it.width = fitWidth as Double
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
        val min = spookyWalls.minXOrZero()
        val max = spookyWalls.maxXOrZero()
        val center = min + ((max-min )/ 2)
        spookyWalls.forEach {
            it.startRow = center + (center - it.startRow)
            it.width *= -1
        }
    }

    if(reverseY){
        val min = spookyWalls.minYOrZero()
        val max = spookyWalls.maxYOrZero()
        val center = min + ((max-min )/ 2)
        spookyWalls.forEach {
            it.startHeight = center + (center - it.startHeight)
            it.height *= -1
        }
        println()
    }
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
