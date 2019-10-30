package com.github.spookyghost.beatwalls


fun run() {
    println(readPath().absolutePath)
    println("bpm: ${readBpm()}")
    println("hjd: ${readHjsDuration()}")
    println("offset: ${readOffset()}")
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
