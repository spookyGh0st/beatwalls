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
    val difficulty = pickDifficulty(info)
    val hjd = pickHjd()

    val bpm = info._beatsPerMinute
    val offset = info._songTimeOffset

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

