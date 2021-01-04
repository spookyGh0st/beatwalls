package beatwalls

import interpreter.Beatwalls
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mu.KotlinLogging
import java.io.File
import kotlin.system.exitProcess

internal val logger = KotlinLogging.logger("beatwalls")

@ExperimentalCoroutinesApi
suspend fun main(args: Array<String>) {
    hello()
    val d = loadDirectory(args)
    Beatwalls(d).run()
    runOnChange { Beatwalls(d).run() }
}

private fun loadDirectory(args: Array<String>): File {
    val dir = when (args.size) {
        0 -> readWorkingDirectory()
        1 -> File(args[0])
        else -> {
            logInfo("After version 1.0 all Options are declared in the BW file directly. Please update your shortcuts")
            readWorkingDirectory()
        }
    }

    if (!dir.exists())
        errorExit { "For the first Setup please drag in the map folder! Could read directory ${dir.absolutePath}. Have you deleted the map?" }
    if (!dir.isDirectory)
        errorExit { "The File ${dir.absolutePath} is not a Directory. Please drag in the whole map folder" }
    if (!dir.list().contains("Info.dat"))
        errorExit { "The Directory is missing the Info.dat File. Are you using an outdated Editor?" }
    if (!dir.list().contains("main.bw"))
        initConfig(dir)

    saveWorkingDirectory(dir)
    logInfo("Running Beatwalls in ${dir.absolutePath}")
    return dir
}

fun logInfo(msg: String){
    println("[INFO]: $msg")
}

fun logWarning(msg: String){
    println("[WARNING]: $msg")
}

fun errorExit(e: Exception? = null, msg: () -> Any): Nothing {
    if (e != null) {
        println("Exeption:\n")
        println(e.message)
        println(e.stackTrace.toList().toString())
    }
    println(
        """
  _____ ____  ____   ___  ____  
 | ____|  _ \|  _ \ / _ \|  _ \ 
 |  _| | |_) | |_) | | | | |_) |
 | |___|  _ <|  _ <| |_| |  _ < 
 |_____|_| \_\_| \_\\___/|_| \_\
 
        """.trimIndent()
    )
    println(msg.invoke())
    println("PLEASE FIX THE ERROR AND RESTART THE PROGRAM")
    println("if you think this is a bug, let me know on discord or github")
    readLine()
    exitProcess(-1)
}


