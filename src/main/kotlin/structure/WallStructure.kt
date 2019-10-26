package structure

import assetFile.InterfaceAdapter
import com.google.gson.*
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}

//    _   __                           __   _____ __                  __
//   / | / /___  _________ ___  ____ _/ /  / ___// /________  _______/ /___  __________  _____
//  /  |/ / __ \/ ___/ __ `__ \/ __ `/ /   \__ \/ __/ ___/ / / / ___/ __/ / / / ___/ _ \/ ___/
// / /|  / /_/ / /  / / / / / / /_/ / /   ___/ / /_/ /  / /_/ / /__/ /_/ /_/ / /  /  __(__  )
///_/ |_/\____/_/  /_/ /_/ /_/\__,_/_/   /____/\__/_/   \__,_/\___/\__/\__,_/_/   \___/____/

sealed class WallStructure
{
    var beat: Double = 0.0
    var mirror: Boolean = false
    var time: Boolean = false
    private val walls: ArrayList<Wall> = arrayListOf()
    init {
        beat = adjustBeat()
    }

    fun walls(): ArrayList<Wall> {
        run()
        return walls
    }
    protected open fun run(){}
    private fun adjustBeat()  = beat++
    fun add(w:Wall){
        walls.add(w)
    }

    fun add(w:Collection<Wall>){
        walls.addAll(w)
    }

    //hacky Workaround for cloning
    fun copy(): WallStructure {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(WallStructure::class.java, InterfaceAdapter<WallStructure>())
            .create()
        val json = gson.toJson(this, WallStructure::class.java)
        return gson.fromJson(json,WallStructure::class.java)
    }
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

/**
 * Saves All WallStructures between
 */
class Save:WallStructure(){
    var name: String = ""
    var duration: Double = 1.0
    override fun run() {
    }
}



fun main(){
}