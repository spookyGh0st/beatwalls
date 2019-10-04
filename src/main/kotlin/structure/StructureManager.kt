package structure

import assetFile.AssetController
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import parameter.Command
import parameter.Parameter
import song.Difficulty
import song._BPMChanges

object StructureManager {
    private val allWallStructure = allDefaultWalls()
    private val bpm = AssetController.getBPM()
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

    fun walls(c:Command, d:Difficulty): List<Wall> {
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
        var tempWalls = struct.flatMap { it.walls() }

        /** bpm change */
        val tempBpm =
            d._BPMChanges.findLast { bpmChanges -> bpmChanges._time <= c.beatStartTime }?._BPM ?: bpm

        /** before bpm change*/
        with (p){
            tempWalls =tempWalls
                .mapIf(fast) {it.fast()}
                .mapIf(hyper){ it.hyper() }
                .mapIf(mirror) {it.mirror()}
                .mapIf(mVertical) {it.mVertical()}
                .map{ it.scale(scale)}
                .map{it.verticalScale(verticalScale)}
                .mapIf(extendX != null){it.extendX(extendX!!)}
                .mapIf(extendY != null){it.extendY(extendY!!)}
                .mapIf(extendZ != null){it.extendZ(extendZ!!)}
                    //bpm change
                .map { it.adjustToBPM(bpm, tempBpm, c.beatStartTime)    }
                .mapIf(time) { it.time(AssetController.njsOffset())}
        }


        /** after bpm change */
        return tempWalls
    }

    private fun List<Wall>.adjustBefore(p: Parameter): List<Wall> {
        return this
        //todo
    }


    private fun List<Wall>.mapIf(b:Boolean, refactor: (Wall) -> Wall): List<Wall>{
        return if(b)
            this.map { refactor(it) }
        else
            this

    }
}
