package beatwalls

import assetFile.parseAsset
import compiler.Compiler

fun run() {
    //reloads the config
    GlobalConfig.reload()
    // reads in the Chart.difficulty
    val diff = AssetReader.getDifficulty()
    // the text of the File we work on
    val diffAssetText = readPath().readText()
    // the parsed list of the file
    val list = Compiler().compile()
    // created the Walls in the diff
    diff.createWalls(list)
    // writes the diff
    AssetReader.writeDifficulty(diff)
}
