package com.github.spookyghost.beatwalls

import com.google.gson.Gson
import mu.KotlinLogging
import song.Info
import song.isSong
import java.io.File

private val logger = KotlinLogging.logger {}

fun initConfig(songPath:String){
    val file = File(songPath)
    if (!file.isSong())
        errorExit { "Please drag in the Whole Folder for the initial Config" }
    val infoJson = File(file,"info.dat").readText()
    val info = Gson().fromJson(infoJson, Info::class.java)
    val diff = pickDifficulty(info)
    val diffName = diff.first
    val diffOffset = diff.second
    val name = diffName.replace(".dat",".bw")

    val path = File(songPath,name)
    if(path.exists()){
        logger.info { "File already exist, not creating Default file" }
    }else
        path.writeText(defaultSongAssetString())

    val hjd = pickHjd()

    val bpm = info._beatsPerMinute
    val offset = info._songTimeOffset+diffOffset

    savePath(path)
    saveHjsDuration(hjd)
    saveBpm(bpm)
    saveOffset(offset)
}

fun pickDifficulty(info: Info): Pair<String, Int> {
    val diffSet = info._difficultyBeatmapSets.map { it._beatmapCharacteristicName to it._difficultyBeatmaps }
    val names = diffSet.flatMap { it.second.map {  l -> l._beatmapFilename to l._customData._editorOffset} }
    for ((index, it) in names.withIndex()) {
        println("$index -> ${it.first}")
    }
    print("\nEnter the Number of the Difficult you want to work with:\ninput: ")
    val index = readLine()?.toIntOrNull()
    if (index == null || index !in 0..names.size)
        return pickDifficulty(info)
    return names[index]
}

fun pickHjd(): Double {
    println("Enter Half jump Duration (usually 2)")
    print("input: ")
    return readLine()?.toDoubleOrNull() ?: pickHjd()
}

private fun defaultSongAssetString() =
    """
# This is an example File of a DifficultyAsset. Use this to orchestate Walls.
# Lines starting with an # are Comments and will get ignored
# Commands, Specify the Walls you want to create
# Syntax Beat(check mm for  that):Name
# simple wallstructures
10: Helix
    amount: 5
    changeDuration: -3
    repeat: 10
20.0: RandomNoise
    amount: 5
    repeat: 10
    
# defining a wallstructures for multiple use
define: upDownCurve
    # what structure we are basing of
    structures: curve
    
    # the 4 controlPoints we are basing of. These are Points in 3d-space. 
    # Imagine a line between p1 and p4. The other 2 Points,  p2 and p3 are "pulling" on it
    p1: 0,4,0
    p2: 2,4,0.33
    p3: 3,2,0.66
    p4: 2,0,1
    
    # fit the startRow to 5. All Walls in the curve will get stretched in width until they reach that startRow
    fitStartRow: 10
    # same for startHeight. All walls will get stretched in height until they reach that startheight
    fitStartHeight: 5
    # mirrors the whole structure to the left side
    mirror: 2
    
30: upDownCurve
    # we repeat our structure 5 times, but only every second beat
    repeat: 5
    repeatAddZ: 2
    
31: upDownCurve
    # we reverse the structure, the rest is same as 30
    reverse: true
    repeat: 5
    repeatAddZ: 2
    
# like in 30/31 but mirrored horizontally and with hyper walls
40: upDownCurve
    mirror: 3
    changeDuration: -3
    repeat: 5
    repeatAddZ: 2
    
41: upDownCurve
    mirror: 3
    changeDuration: -3
    reverse: true
    repeat: 5
    repeatAddZ: 2
    """.trimIndent()
