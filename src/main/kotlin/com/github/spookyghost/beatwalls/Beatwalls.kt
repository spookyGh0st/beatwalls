package com.github.spookyghost.beatwalls

import assetFile.parseAsset
import assetFile.readAsset

fun run() {
    // reads in the Chart.difficulty
    val diff = AssetReader.getDifficulty()
    // reads in the Asset
    val asset = readAsset()
    // the text of the File we work on
    val diffAssetText = readPath().readText()
    // the parsed list of the file
    val list = parseAsset(diffAssetText)
    // created the Walls in the diff
    diff.createWalls(list, asset)
    // writes the diff
    AssetReader.writeDifficulty(diff)
}
