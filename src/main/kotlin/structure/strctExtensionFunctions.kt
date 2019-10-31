package structure

import java.io.*

/** this is for getting the acutal Walls */
fun WallStructure.walls(): ArrayList<SpookyWall> {
    //order is important, dont question it
    repeat()
    run()
    adjust()
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
}

fun WallStructure.repeat(){
    val tempWalls  = arrayListOf<SpookyWall>()
    for (i in 1 until repeat){
        val temp = this.deepCopy()
        temp.run()
        temp.spookyWalls.forEach {
            it.startTime+=repeatGap*i
            it.startRow += repeatShiftX*i
            it.startHeight += repeatShiftY*i
        }
        tempWalls.addAll(temp.spookyWalls)
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

    //extra
    if(scale!=null){
        spookyWalls.forEach {
            it.startTime *= scale as Double
            if (it.duration > 0)
                it.duration *= scale as Double
        }
    }
}

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
