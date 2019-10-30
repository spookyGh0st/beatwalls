package com.github.spookyghost.beatwalls

import com.google.gson.Gson
import mu.KotlinLogging
import reader.isSong
import song.Info
import java.io.File

private val logger = KotlinLogging.logger {}

fun initConfig(songPath:String){
    val file = File(songPath)
    if (!file.isSong())
        errorExit { "Please drag in the Whole Folder for the initial Config" }
    val infoJson = File(file,"info.dat").readText()
    val info = Gson().fromJson(infoJson, Info::class.java)
    val name = pickDifficulty(info).replace(".dat",".bw")

    val path = File(songPath,name)
    path.writeText(defaultSongAssetString())
    val hjd = pickHjd()

    val bpm = info._beatsPerMinute
    val offset = info._songTimeOffset

    println(path)

    savePath(path)
    saveHjsDuration(hjd)
    saveBpm(bpm)
    saveOffset(offset)
}

fun pickDifficulty(info: Info): String {
    val diffSet = info._difficultyBeatmapSets.map { it._beatmapCharacteristicName to it._difficultyBeatmaps }
    val names = diffSet.flatMap { it.second.map {  l -> l._beatmapFilename } }
    println()
    for ((index, it) in names.withIndex()) {
        println("$index -> $it")
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
# Example Wall, remove
10: Curve
    mirror: 8
    changeDuration: -3
    sp: 0,0,0
    cp1: 2,0,0
    cp2: 2,0,1
    ep: 0,0,0
20.0: RandomNoise
    amount: 10
    time: true
    """.trimIndent()
