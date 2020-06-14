package beatwalls

import interpreter.Interpreter
import java.lang.Exception

fun run() {
    //reloads the config
    GlobalConfig.reload()
    // reads in the Chart.difficulty
    val diff = AssetReader.getDifficulty()
    // the parsed list of the file
    val list = Interpreter().compile()
    // created the Walls in the diff
    try {
        diff.createWalls(list)
    }catch (e:Exception){
        errorExit(e) { "failed to create Objects" }
    }
    // writes the diff
    AssetReader.writeDifficulty(diff)
}
