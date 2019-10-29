package com.github.spookyghost.beatwalls

import assetFile.*
import reader.readDifficulty
import reader.writeDifficulty
import java.io.File

fun run(path: String) {
    println(path)
    /*
    val assetFile = AssetFileAPI.assetFile()

    val difficultyAssetFile = findDifficultyAsset(song)
    assetFile.currentSong = difficultyAssetFile.absolutePath
    val difficultyAssetText = difficultyAssetFile.readText()

    val songAsset = readDifficultyAsset(difficultyAssetText)

    val diffFile = File(songAsset.path)
    val difficulty = readDifficulty(diffFile)

    songAsset.saveStructures(assetFile,difficulty)
    difficulty._obstacles.clear()

    songAsset.createWalls(difficulty)
    writeDifficulty(difficulty,diffFile)
     */
}
