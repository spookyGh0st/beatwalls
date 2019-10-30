package assetFile

import com.github.spookyghost.beatwalls.errorExit
import mu.KotlinLogging
import reader.isDifficulty
import song.Difficulty
import structure.CustomWallStructure
import structure.Define
import structure.Save
import structure.WallStructure
import java.io.File
import java.nio.file.Paths

private val logger = KotlinLogging.logger {}

class DifficultyAsset(val bpm: Double, val njsOffset:Double, val path:String, val list: ArrayList<WallStructure> ){
    /**
     * saves the Structure to the AssetFile
     */
    fun saveStructures(a:Any, d:Difficulty){ //todo
        val savingList = list.filterIsInstance<Save>()
        list.removeAll(savingList)
        for(saving in savingList){
            val selectedStructures = list
                .filter { it.beat >= saving.beat && it.beat <= saving.beat + saving.duration}
                .map { it.deepCopy() }

            selectedStructures.forEach {
                if(it !is CustomWallStructure)
                    it.walls().clear()
                it.beat-=saving.beat }

            val selectedWalls =d._obstacles
                .map { it.toWall() }
                .filter { it.startTime >= saving.beat && it.startTime <= saving.beat + saving.duration}
                .map { it.copy() }

            selectedWalls.forEach { it.startTime -= saving.beat }


            val savedWallStructure = SavedWallStructure(
                name = saving.name,
                wallList = selectedWalls,
                structureList = selectedStructures
            )

            //todo
           // val sameStructure = a.savedStructures.find { it.name == saving.name }
           // if (sameStructure!= null){
           //     logger.info { "${saving.name} already exist, replacing" }
           //     a.savedStructures.remove(sameStructure)
           // }

           // a.savedStructures.add(savedWallStructure)
           // AssetFileAPI.writeAssetFile()
            logger.info { "created ${saving.name} with ${savedWallStructure.wallList.size + savedWallStructure.structureList.flatMap { it.walls() }.size} walls" }
        }
    }


    fun createWalls(difficulty: Difficulty){
        for(w in list){
            if (w is Save || w is Define)
                continue

            val walls = w.walls()
            walls.forEach { it.startTime+=w.beat }
            walls.forEach { it.adjustToBPM(bpm, difficulty) }

            // adds the njsOffset if time is true
            if(w.time)
                walls.forEach { it.startTime+=njsOffset }

            val obstacles = walls.map { it.to_obstacle() }
            difficulty._obstacles.addAll(obstacles)
        }
    }

    @Override
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DifficultyAsset

        if (bpm != other.bpm) return false
        if (njsOffset != other.njsOffset) return false
        if (path != other.path) return false
        if (list != other.list) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bpm.hashCode()
        result = 31 * result + njsOffset.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + list.hashCode()
        return result
    }

    override fun toString(): String {
        return "DifficultyAsset(bpm=$bpm, njsOffset=$njsOffset, path='$path', list=$list)"
    }
}


/**
 * gets the given Songasset
 */
fun findDifficultyAsset(file: File): File {
    try {
        if (file.isDifficulty()) {
            // gets the SongAssetFile
            val path = file.absolutePath
            val f = File(System.getProperty("java.class.path"))
            val dir = f.absoluteFile.parentFile
            val tPath = dir.toString()
            File(tPath, "DifficultyAssets").mkdirs()
            val name = Paths.get(path).parent.fileName
            val songAssetFile = Paths.get(tPath, "DifficultyAssets", "$name.txt").toFile()
            logger.info { "Working on $songAssetFile" }

            //checks if its already exist
            if (!songAssetFile.exists()) {
                val njs = askForDouble("NJS offset",2.0)
                val bpm = askForDouble("BPM",0.0)
                songAssetFile.writeText(defaultSongAssetString(path, njs, bpm))
                if (!songAssetFile.exists())
                    errorExit { "Failed to write Default File" }
                errorExit { "created Default SongAssetFile at $songAssetFile" }
            }
            return songAssetFile
        } else
            return file
    }catch (e:Exception){
        errorExit(e) { "Failed to read in the SongAssetFile" }
        return file
    }
}

/** Asking for NJS */
private fun askForDouble(name:String, default: Double): Double {
    return try {
        println("Enter the $name for the File (default: $default)")
        val njsInput = readLine()?:"$default"
        val njs = njsInput.toDoubleOrNull()?:default
        logger.info { "Set the NJS Offset to $njs" }
        njs
    }catch (e:Exception){
        logger.error { "Something went wrong, try again" }
        askForDouble(name, default)
    }
}



private fun defaultSongAssetString(path: String,njsOffset: Double, bpm: Double)=
    """
# This is an example File of a DifficultyAsset. Use this to orchestate Walls.
# Lines starting with an # are Comments and will get ignored

# Mandatory Fields:

# version, must be 1.0 currently
version: 1.0

# path of the Song
path: $path

# bpm of the Song
bpm: $bpm

# njs you want to work with (used for timing)
njsOffset: $njsOffset


# Commands, Specify the Walls you want to create
# Syntax Beat(check mm for  that):Name
# Example Wall, remove
10.0: Floor
    time: true
20.0: RandomNoise
    amount: 10
    """.trimIndent()


