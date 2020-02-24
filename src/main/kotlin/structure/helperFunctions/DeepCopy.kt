package structure

import structure.helperClasses.SpookyWall
import java.io.*

// copies the Wall of one Wallstructure. Used in Repeat
fun WallStructure.copyWalls() :ArrayList<SpookyWall> = ArrayList((spookyWalls.map { it.copy() }))

// deepcopies the Wallstructure
fun WallStructure.deepCopy(): WallStructure =
    deepCopyBySer(this)

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