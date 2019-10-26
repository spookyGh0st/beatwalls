package assetFile

import com.github.spookyghost.beatwalls.errorExit
import mu.KotlinLogging
import reader.isSong
import structure.WallStructure
import java.io.File
import java.nio.file.Paths
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties

private val logger = KotlinLogging.logger {}

class SongAsset(val njsOffset:Double = 2.0, val path:String = "", val list: ArrayList<WallStructure> = arrayListOf())

private fun defaultSongAssetString(path: String,njsOffset: Double)=
    """
# This is an example File of a SongAsset. Use this to orchestate Walls.
# Lines starting with an # are Comments and will get ignored

# Mandatory Fields:
# version, must be 1.0 currently
version: 1.0
# path of the Song
path: $path
# njs you want to work with (used for timing)
njsOffset: $njsOffset

# Commands, Specify the Walls you want to create
# Syntax Beat(check mm for  that):Name
# Example Wall, remove
1.0:Floor
    mirror:true
    time:true
2.0:Helix
    amount:2
    """.trimIndent()

/**
 * gets the given Songasset
 */
fun findSongAsset(file:File, assetFile: AssetFile): SongAsset {
    try {
        if (file.isSong()) {
            // gets the SongAssetFile
            val path = file.absolutePath
            val f = File(System.getProperty("java.class.path"))
            val dir = f.absoluteFile.parentFile
            val tPath = dir.toString()
            File(tPath, "SongAssets").mkdirs()
            val songAssetFile = Paths.get(tPath, "SongAssets", "${Paths.get(path).fileName}.txt").toFile()

            //checks if its already exist
            if (!songAssetFile.exists()) {
                val njs = getNJSOffset()
                songAssetFile.writeText(defaultSongAssetString(path, njs))
                if (!songAssetFile.exists())
                    errorExit { "Failed to write Default File" }
            }
            return readSongAsset(songAssetFile.readText(), assetFile)
        } else
            return readSongAsset(file.readText(), assetFile = assetFile)
    }catch (e:Exception){
        errorExit(e) { "Failed to read in the SongAssetFile" }
        TODO()
    }
}

/** Asking for NJS */
private fun getNJSOffset(): Double {
    return try {
        println("Enter the NJS Offset for the File (usually 2)")
        val njsInput = readLine()?:"2"
        val njs = njsInput.toDoubleOrNull()?:2.0
        logger.info { "Set the NJS Offset to $njs" }
        njs
    }catch (e:Exception){
        logger.error { "Something went wrong, try again" }
        getNJSOffset()
    }
}

/**
 * reads in a String and returns the corresponging SongAssetFile
 */
private fun readSongAsset(s:String, assetFile: AssetFile):SongAsset{
    // creates the list of key-Value
    val lines = parseAssetString(s)

    assertVersion(lines)

    val path = lines.pop("path")
    val njsOffset = lines.pop("njsOffset").toDouble()
    val structures = lines.readStructures(assetFile)

    return SongAsset(path = path, njsOffset = njsOffset,list = structures)
}




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
private fun MutableList<Pair<String,String>>.readStructures(assetFile: AssetFile): ArrayList<WallStructure>{
    val list = arrayListOf<WallStructure>()

    val customStructs = assetFile.savedStructures.map { it.toCustomWallStructure() }
    val customStructsNames = customStructs.map { it.name.toLowerCase() }
    val specialStructs = WallStructure::class.sealedSubclasses
    val specialStructsNames = specialStructs.map { it.simpleName?.toLowerCase()?:""}

    for (i in 0 until this.size){
        if (this[i].key().toDoubleOrNull() != null){
            val structName = this[i].value().toLowerCase()
            val beat = this[i].key().toDouble()
            val struct: WallStructure

            // sets the struct
            struct = when (structName) {
                in customStructsNames -> customStructs.find { it.name.toLowerCase() == structName }!!.copy()
                in specialStructsNames -> specialStructs.find { it.simpleName!!.toLowerCase() == structName }!!.createInstance()
                else -> {
                    errorExit { "Cant find the Structure $structName" }
                    TODO()
                }
            }
            //and the beat
            struct.beat = beat
            list.add(struct)
        }else{
            readWallStructOptions(list.last(),this[i])

        }
    }
    return list
}

/**
 * fills the given structure with the option, if it exist
 */
private fun readWallStructOptions(wallStructure: WallStructure, option: Pair<String, String>){
    val properties = wallStructure::class.memberProperties.toMutableList()
    val property = properties.find { it.name.toLowerCase() ==option.key().toLowerCase() }
    val value:Any?
    if (property == null) {
        errorExit { "The WallStructure $wallStructure does not have the property ${option.key()}" }
    }
    val type = property?.returnType

    with (option.value()){
        value = when (type) {
            Boolean::class.createType() -> toBoolean()
            Int::class.createType() -> toInt()
            Double::class.createType() -> toDouble()
            String::class.createType() -> toString()
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
    .map { it.replace("\\s".toRegex(), "") }
    .map { it.replace("\\t".toRegex(), "") }
    .map { it.toLowerCase() }
    .map { it.split(":") }
    .map { it[0] to it[1] }
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
        errorExit { "Cant find the value to $key, do you have it in your SongAsset?" }
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



