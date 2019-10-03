package structure

import com.github.ajalt.clikt.core.CliktCommand

/**
____  ____  _____ ____ ___    _    _      __        ___    _     _     ____ _____ ____  _   _  ____ _____ _   _ ____  _____ ____
/ ___||  _ \| ____/ ___|_ _|  / \  | |     \ \      / / \  | |   | |   / ___|_   _|  _ \| | | |/ ___|_   _| | | |  _ \| ____/ ___|
\___ \| |_) |  _|| |    | |  / _ \ | |      \ \ /\ / / _ \ | |   | |   \___ \ | | | |_) | | | | |     | | | | | | |_) |  _| \___ \
___) |  __/| |__| |___ | | / ___ \| |___    \ V  V / ___ \| |___| |___ ___) || | |  _ <| |_| | |___  | | | |_| |  _ <| |___ ___) |
|____/|_|   |_____\____|___/_/   \_\_____|    \_/\_/_/   \_\_____|_____|____/ |_| |_| \_\\___/ \____| |_|  \___/|_| \_\_____|____/
 */

sealed class SpecialWallStructure :WallStructure, CliktCommand(){
    override val name: String = this::class.simpleName!!
    override val wallList: ArrayList<Wall> = arrayListOf()

    fun remove(wall: Wall) {
        wallList.remove(wall)
    }

    fun remove(wall: Collection<Wall>) {
        wallList.removeAll(wall)
    }

    fun add(wall: Wall) {
        wallList.add(wall)
    }

    fun add(wall: Collection<Wall>) {
        wallList.addAll(wall)
    }
}

object SampleStructure: SpecialWallStructure(){
    override fun run(){
    }
}
object TestStructure: SpecialWallStructure(){
    override fun run() {
        add(Wall(0.0, 1.0, 0.0, 0.0, 0.0, 0.0))
    }
}

fun main(){
}

