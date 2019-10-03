package structure

import assetFile.AssetController
import com.github.ajalt.clikt.core.subcommands
import parameter.Command
import parameter.Parameter

object StructureManager {
    private val allWallStructure = allDefaultWalls()
    private fun allDefaultWalls(): List<WallStructure> {
        val specialWalls = WallStructure::class.sealedSubclasses
            .mapNotNull { it.objectInstance }

        val customWalls =
            AssetController.customWallStructures()

        val list = specialWalls + customWalls // + mWallCommandStruct
        return  list
    }

    private fun findStructure(c: List<String>): List<WallStructure> {
        val names = c.map { it.toLowerCase() }
        val list = allWallStructure
        return list.filter {
            names.contains(it.name.toLowerCase())
        }
    }

    fun walls(c:Command): List<Wall> {
        val arr = c.command.split(" ")
        val struct = findStructure(arr)
        var p = Parameter()
        struct.forEach {
            p = p.subcommands(it)
        }
        TODO()
        p.main(arr)
        val tempWalls = struct.flatMap { it.walls() }
        return p.parseWalls(tempWalls)

    }
}

fun main(){
    val command = Command(0.0,"/bw -fh floor")
    println(StructureManager.walls(command))
}