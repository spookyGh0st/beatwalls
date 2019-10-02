package structure

import com.google.gson.annotations.SerializedName
import parameter.CommandParser

@Suppress("unused")
sealed class WallStructure: CommandParser() {
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

    /**saves the name of the class directly to the name property */
    protected open var name = this::class.simpleName ?: ""
    protected open var wallList = arrayListOf<Wall>()

    /** USE THIS TO CHANGE WALLS */
    protected open fun run() {}

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

    /** clears the walls to ensure the list if fresh! and runs it*/
    fun wallList(array:Array<kotlin.String>): List<Wall>{
        if(!this::class.isData){
            wallList.clear()
            options.clear()
            options.addAll(array)
            run()
        }
        return  cloneWalls()
    }

    private fun cloneWalls(): List<Wall> {
        return wallList.map { it.copy() }
    }
}

/** CustomWallStructure, used to safe static Walls */
data class CustomWallStructure(
    @SerializedName("Name")
    override var name:kotlin.String,
    @SerializedName("Wall List")
    override var wallList: ArrayList<Wall>
): WallStructure()

/**
 ____  ____  _____ ____ ___    _    _      __        ___    _     _     ____ _____ ____  _   _  ____ _____ _   _ ____  _____ ____
/ ___||  _ \| ____/ ___|_ _|  / \  | |     \ \      / / \  | |   | |   / ___|_   _|  _ \| | | |/ ___|_   _| | | |  _ \| ____/ ___|
\___ \| |_) |  _|| |    | |  / _ \ | |      \ \ /\ / / _ \ | |   | |   \___ \ | | | |_) | | | | |     | | | | | | |_) |  _| \___ \
___) |  __/| |__| |___ | | / ___ \| |___    \ V  V / ___ \| |___| |___ ___) || | |  _ <| |_| | |___  | | | |_| |  _ <| |___ ___) |
|____/|_|   |_____\____|___/_/   \_\_____|    \_/\_/_/   \_\_____|_____|____/ |_| |_| \_\\___/ \____| |_|  \___/|_| \_\_____|____/
*/

class TestStructure: WallStructure(){
    val mirror by Boolean(true)
    val amount by Int(12)
    val text by String("abc")
    override fun run() {
        add(Wall(0.0, 1.0, 0.0, 0.0, 0.0, 0.0))
    }
}

fun main(){
}

