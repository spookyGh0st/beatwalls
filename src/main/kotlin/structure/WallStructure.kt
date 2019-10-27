package structure

import mu.KotlinLogging
import java.io.*
import kotlin.random.Random


private val logger = KotlinLogging.logger {}

//    _   __                           __   _____ __                  __
//   / | / /___  _________ ___  ____ _/ /  / ___// /________  _______/ /___  __________  _____
//  /  |/ / __ \/ ___/ __ `__ \/ __ `/ /   \__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// / /|  / /_/ / /  / / / / / / /_/ / /   ___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///_/ |_/\____/_/  /_/ /_/ /_/\__,_/_/   /____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/

sealed class WallStructure:Serializable
{
    /**
     * saved Walls
     */
    private var walls: ArrayList<Wall> = arrayListOf()
    /**
     * the Beat, dont change that, it will get overwritten anyway
     */
    var beat: Double = 0.0

    /**
     * mirrors the Wall to the otherSide, duplicates by default, look at doNotDuplicate
     */
    var mirror: Boolean = false

    /**
     * do not duplicate when mirroring
     */
    var doNotDuplicate: Boolean = false

    /**
     * times the Wall by adding the njsOffset
     */
    var time: Boolean = false

    fun walls(): ArrayList<Wall> {
        run()
        mirror()
        return walls
    }

    private fun mirror(){
        if(!mirror){
            return
        }
        val tempList = if (!doNotDuplicate)
            walls.map { it.copy() }
        else
            emptyList()
        walls.forEach { it.mirror() }
        walls.addAll(tempList)
    }


    protected open fun run(){}
    fun add(w:Wall){
        walls.add(w)
    }
    fun add(w:Collection<Wall>){
        walls.addAll(w)
    }
    fun deepCopy():WallStructure = deepCopyBySer(this)
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

class CustomWallStructure(val name:String): WallStructure()

object EmptyWallStructure:WallStructure(){
}


//   _____                 _       __   _       __      _________ __                  __
//  / ___/____  ___  _____(_)___ _/ /  | |     / /___ _/ / / ___// /________  _______/ /___  __________  _____
//  \__ \/ __ \/ _ \/ ___/ / __ `/ /   | | /| / / __ `/ / /\__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// ___/ / /_/ /  __/ /__/ / /_/ / /    | |/ |/ / /_/ / / /___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///____/ .___/\___/\___/_/\__,_/_/     |__/|__/\__,_/_/_//____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/
//    /_/

class TestWallStructure(val test:Boolean = false): WallStructure()

class RandomNoise:WallStructure(){
    /**
     * the amount of the created Walls
     */
    var amount: Int  = 10
    override fun run() {
        repeat(amount){
            val w = Wall(
                startRow = Random.nextDouble(-6.0,6.0),
                duration = 0.0,
                width = 0.0,
                height = 0.0,
                startHeight = Random.nextDouble(5.0),
                startTime = it.toDouble()/amount
            )
            add(w)
        }
    }
}

/**
 * Saves All WallStructures between
 */
class Save:WallStructure(){
    var name: String = ""
    var duration: Double = 1.0
    override fun run() {
    }
}

fun main (){
    val w = CustomWallStructure("test1")
    w.walls().add(Wall(1.1,1.1,1.1,1.1,1.1,1.1))
    val w2 = w.deepCopy()
    w.walls().forEach { it.startRow+=2 }
    println(w.walls().first().startRow)
    println(w2.walls().first().startRow)
}
