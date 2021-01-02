package structure

import structure.helperClasses.SpookyWall
import java.io.*


// deepcopies the Wallstructure
fun WallStructure.deepCopy(): WallStructure = TODO()

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