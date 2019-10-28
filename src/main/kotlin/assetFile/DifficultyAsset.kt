package assetFile

import com.github.spookyghost.beatwalls.errorExit
import mu.KotlinLogging
import reader.isDifficulty
import song.Difficulty
import structure.*
import java.io.File
import java.nio.file.Paths
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability

private val logger = KotlinLogging.logger {}

class DifficultyAsset(val bpm: Double, val njsOffset:Double, val path:String, val list: ArrayList<WallStructure> ){
    /**
     * saves the Structure to the AssetFile
     */
    fun saveStructures(a:AssetFile, d:Difficulty){
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

            val sameStructure = a.savedStructures.find { it.name == saving.name }
            if (sameStructure!= null){
                logger.info { "${saving.name} already exist, replacing" }
                a.savedStructures.remove(sameStructure)
            }

            a.savedStructures.add(savedWallStructure)
            AssetFileAPI.writeAssetFile()
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
        TODO()
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

/**
 * reads in a String and returns the corresponging SongAssetFile
 */
fun readDifficultyAsset(s: String):DifficultyAsset{
    // creates the list of key-Value
    val lines = parseAssetString(s)

    assertVersion(lines)

    val path = lines.pop("path")
    val njsOffset = lines.pop("njsOffset").toDouble()
    val bpm = lines.pop("bpm").toDouble()
    val structures = lines.readStructures()


    val list  =  DifficultyAsset(bpm = bpm, path = path, njsOffset = njsOffset,list = structures)
    logger.info { "Read DifficultyAsset Succesfull with ${structures.size} structures" }
    return list
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


//   ____  ____   __   ____  ____  ____
//  (  _ \(  __) / _\ (    \(  __)(  _ \
//   )   / ) _) /    \ ) D ( ) _)  )   /
//  (__\_)(____)\_/\_/(____/(____)(__\_)
//
// I basically wrote a new script language.
// Below is what needed to go automatically set all the values for all the parameters.

/**
 * reads the Structures from the list
 */
private fun MutableList<Pair<String,String>>.readStructures(): ArrayList<WallStructure>{
    val list = arrayListOf<WallStructure>()

    for (i in 0 until this.size){
        if (this[i].key().toDoubleOrNull() != null){
            val structName = this[i].value().toLowerCase()
            val beat = this[i].key().toDouble()
            val struct: WallStructure = findStructure(structName)

            //sets the beat
            struct.beat = beat
            list.add(struct)
        }else{
            readWallStructOptions(list.last(),this[i])
        }
    }
    return list
}

fun findStructure(structName: String): WallStructure {
    val assetFile = AssetFileAPI.assetFile
    val customStructs = assetFile.savedStructures.map { it.toCustomWallStructure() }
    val customStructsNames = customStructs.map { it.name.toLowerCase() }
    val specialStructs = WallStructure::class.sealedSubclasses
    val specialStructsNames = specialStructs.map { it.simpleName?.toLowerCase()?:""}
    val struct: WallStructure

    // sets the struct
    struct = when (structName) {
        in customStructsNames -> customStructs.find { it.name.toLowerCase() == structName }!!.deepCopy()
        in specialStructsNames -> specialStructs.find { it.simpleName!!.toLowerCase() == structName }!!.createInstance()
        else -> {
            logger.info { "structure $structName not found" }
            EmptyWallStructure
        }
    }
    return struct
}

/**
 * fills the given structure with the option, if it exist
 */
private fun readWallStructOptions(wallStructure: WallStructure, option: Pair<String, String>){
    if (wallStructure is EmptyWallStructure){
        return
    }
    val properties = wallStructure::class.memberProperties.toMutableList()
    val property = properties.find { it.name.toLowerCase() ==option.key().toLowerCase() }
    val value:Any?
    if (property == null) {
        errorExit { "The WallStructure $wallStructure does not have the property ${option.key()}" }
    }
    val type = property?.returnType?.withNullability(false)

    with (option.value()){
        value = when (type) {
            Boolean::class.createType() -> toBoolean()
            Int::class.createType() -> toInt()
            Double::class.createType() -> toDouble()
            Double::class.createType() -> toDouble()
            String::class.createType() -> toString()
            Point::class.createType() -> toPoint()
            WallStructure::class.createType() -> toWallStructure()
            else -> null
        }
    }

    if (property is KMutableProperty<*> ){
        property.setter.call(wallStructure,value)
    }
}

/**
 * creates the list of key-value Pairs
 */
private fun parseAssetString(s:String): MutableList<Pair<String, String>> =
    s
        .lines()
        .asSequence()
        .filterNot { it.startsWith("#") || it.isEmpty() }
        .map { it.replace("\\t".toRegex(), "") }
        .map { it.split(":") }
        .map { it.map { l -> l.trim() } }
        .map { it[0].toLowerCase() to it.minus(it[0]).joinToString(":") }
        .toMutableList()

/**
 * exit, when the version is wrong
 */
private fun assertVersion(lines:MutableList<Pair<String,String>>){
    if(lines.pop("version")  != "1.0"){
        errorExit { "Wrong Version, current Version is 1.0" }
    }
}

/**
 * gets the value to the given key and removes it from the list
 */
private fun MutableList<Pair<String,String>>.pop(key:String): String {
    val tempLine =  this.find { it.key() == key.toLowerCase()}
    return if (tempLine != null) {
        this.remove(tempLine)
        tempLine.value()
    }else{
        errorExit { "Cant find the value to $key, do you have it in your DifficultyAsset?" }
        ""
    }
}

/**
 * different name for componen1/component2
 */
private fun Pair<String,String>.value(): String {
    return this.component2()
}
private fun Pair<String,String>.key(): String {
    return this.component1()
}

/**
 * String to Point
 */
private fun String.toPoint(): Point {
    val values = this.split(",")
        .map { it.trim() }
        .map { it.toDoubleOrNull() }
    return Point(
        x = values.getOrNull(0)?:0.0,
        y = values.getOrNull(1)?:0.0,
        z = values.getOrNull(2)?:0.0
    )
}

/**
 * String to WallStructure
 */
private fun String.toWallStructure(): WallStructure{
    return findStructure(this)

}



