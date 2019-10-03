package structure

import com.github.ajalt.clikt.core.CliktCommand
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
sealed class WallStructure : CliktCommand {
/**

  ___                            _              _
 |_ _|_ __ ___  _ __   ___  _ __| |_ __ _ _ __ | |_
  | || '_ ` _ \| '_ \ / _ \| '__| __/ _` | '_ \| __|
  | || | | | | | |_) | (_) | |  | || (_| | | | | |_
 |___|_| |_| |_| .__/ \___/|_|   \__\__,_|_| |_|\__|
               |_|

* ALL DATA CLASSES WILL HOLD FIXED WALLS AND WONT RESET ON SETUP. THE REST DOES
* So if you want to create Special WallStructure, add them on the bottom
* */

    constructor() : super()
    constructor(name: String) : super(name = name)


    /**saves the name of the class directly to the name property */
    open var name: String = this::class.simpleName ?: ""
    open var wallList: ArrayList<Wall> = arrayListOf()

    protected fun remove(wall: Wall) {
        wallList.remove(wall)
    }

    protected fun remove(wall: Collection<Wall>) {
        wallList.removeAll(wall)
    }

    protected fun add(wall: Wall) {
        wallList.add(wall)
    }

    protected fun add(wall: Collection<Wall>) {
        wallList.addAll(wall)
    }

    fun walls(): List<Wall> {
        return wallList.map { it.copy() }
    }
}

/** CustomWallStructure, used to safe static Walls */

data class CustomWallStructure(
    @SerializedName("Name")
    @Expose
    override var name: String,
    @SerializedName("Wall List")
    @Expose
    override var wallList: ArrayList<Wall>
): WallStructure(name) {
    override fun run() {
    }
}

/**
 ____  ____  _____ ____ ___    _    _      __        ___    _     _     ____ _____ ____  _   _  ____ _____ _   _ ____  _____ ____
/ ___||  _ \| ____/ ___|_ _|  / \  | |     \ \      / / \  | |   | |   / ___|_   _|  _ \| | | |/ ___|_   _| | | |  _ \| ____/ ___|
\___ \| |_) |  _|| |    | |  / _ \ | |      \ \ /\ / / _ \ | |   | |   \___ \ | | | |_) | | | | |     | | | | | | |_) |  _| \___ \
___) |  __/| |__| |___ | | / ___ \| |___    \ V  V / ___ \| |___| |___ ___) || | |  _ <| |_| | |___  | | | |_| |  _ <| |___ ___) |
|____/|_|   |_____\____|___/_/   \_\_____|    \_/\_/_/   \_\_____|_____|____/ |_| |_| \_\\___/ \____| |_|  \___/|_| \_\_____|____/
*/

object SampleStructure: WallStructure(){
    override fun run(){
    }
}
object TestStructure: WallStructure(){
    override fun run() {
        add(Wall(0.0, 1.0, 0.0, 0.0, 0.0, 0.0))
    }
}

fun main(){
}

