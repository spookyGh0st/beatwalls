package structure

import assetFile.AssetController
import parameter.Parameter
import kotlin.reflect.full.createInstance

object StructureManager {
    private val allWallStructure = allWalls()
    private fun allWalls(): List<WallStructure> {
        val specialWalls = WallStructure::class.sealedSubclasses
            .filter { !it.isData }
            .map { it.createInstance() }

        val mixedStructure =
            AssetController.mixedWallStructures()

        val mWallCustomStruct=
            mixedStructure
                .map { it.customWallStructure }
                .flatten()

        val customWalls =
            AssetController.customWallStructures()

        val tempList =  specialWalls + customWalls + mWallCustomStruct

        val mWallCommandStruct =
            mixedStructure
                .map { it.command }
                .flatten()
                .map { Parameter(it) }
                .map { it.structureList }
                .flatten()

        TODO()

        return tempList + mWallCommandStruct
    }


    private fun parse(command: Parameter, list: List<WallStructure> = allWallStructure):ArrayList<Wall>{
        command.structureList
        TODO()
    }
}
