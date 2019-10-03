package structure

import assetFile.AssetController
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import parameter.Command
import parameter.Parameter

object StructureManager {
    val allWallStructure = allDefaultWalls()
    private fun allDefaultWalls(): List<WallStructure> {
        val specialWalls = SpecialWallStructure::class.sealedSubclasses
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
        val arr = ArrayList(c.command.split(" "))
        val struct = findStructure(arr)
        var p = Parameter()

        /** ads the subcommands to the special wallstructures */
        struct
            .filterIsInstance<SpecialWallStructure>()
            .forEach {  p = p.subcommands(it)   }

        /** removes the normal wallStructures from the args */
        struct
            .filterIsInstance<CustomWallStructure>()
            .forEach { arr.remove(it.name.toLowerCase()) }

        p.main(arr)
        val tempWalls = struct.flatMap { it.walls() }
        return p.parseWalls(tempWalls)

    }
}

fun main(){


    val command = Command(0.0,"-fh floor")
    println(StructureManager.walls(command))
}
