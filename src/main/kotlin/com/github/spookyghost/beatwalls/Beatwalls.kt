package com.github.spookyghost.beatwalls

import assetFile.parseAsset
import assetFile.readAsset
import difficulty.Difficulty
import difficulty.readDifficulty
import difficulty.writeDifficulty

fun run() {
    // reads in the difficulty
    val diff = readDifficulty()
    // runs a preCheck on the diff, if it can read everything
    preCheck(diff)
    // reads in the Asset
    val asset = readAsset()
    // the text of the File we work on
    val diffAssetText = readPath().readText()
    // the parsed list of the file
    val list = parseAsset(diffAssetText)
    // created the Walls in the diff
    diff.createWalls(list,asset)
    // writes the diff
    writeDifficulty(diff)
}

fun preCheck(d: Difficulty){
    try {
        d._customData._BPMChanges
    }catch (e:Exception){
        errorExit { "You still use an old version of mediocre mapper. Please update to mma2" }
    }
}
