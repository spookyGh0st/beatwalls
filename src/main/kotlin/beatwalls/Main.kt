package beatwalls

import kotlinx.coroutines.ExperimentalCoroutinesApi
import mu.KotlinLogging
import java.io.File
import kotlin.system.exitProcess


@ExperimentalCoroutinesApi
suspend fun main(args: Array<String>) {
    checkOptions(args)
    checkPath()
    update()
    run()
    runOnChange { run() }
}

fun checkPath(){
    val path = readPath()
    if(!path.canRead())
        errorExit { "cant read $path, setup this program by dragging a Song into it" }
}

fun checkOptions(args: Array<String>){
    val file = args.find { File(it).exists() }
    if (file != null) {
        initConfig(file)
        logger.info { "finished Setup, open the file at ${readPath()} now." }
    }
    if (args.contains("--clearAll"))
        GlobalConfig.clearAll = true
    if (args.contains("--deleteAllPrevious"))
        GlobalConfig.deleteAllPrevious = true
    if (args.contains("--noUpdate"))
        GlobalConfig.deleteAllPrevious = true
}

object GlobalConfig{
    var clearAll: Boolean = false
    var deleteAllPrevious: Boolean = false
    var noUpdate: Boolean = false
    var file: File = readPath()
}

private val logger = KotlinLogging.logger {}

fun errorExit(e: Exception? = null, msg: () -> Any): Nothing {
    logger.error { msg.invoke() }
    if (e != null) {
        logger.info { "See Error Log below" }
        logger.error { e.message }
    }
    readLine()
    exitProcess(-1)
}


