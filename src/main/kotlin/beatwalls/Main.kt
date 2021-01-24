package beatwalls

import interpreter.Beatwalls
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File
import kotlin.system.exitProcess

const val mainFileSuffix = "main.bw"
val includeRegex = Regex("include:.+")

@ExperimentalCoroutinesApi
suspend fun main(args: Array<String>) {
    hello()
    val d = loadDirectory(args)
    val bwFiles = loadBwFiles(d).toMutableList()

    runOnChange(bwFiles) {
        runCount++
        println()
        logInfo("Starting $runCount. run of Beatwizard!")
        Beatwalls(d,bwFiles).run()
    }
}

var runCount = 0

fun loadBwFiles(wd: File): List<File>{
    val mainFile = File(wd, mainFileSuffix)
    return loadIncludes(wd, mainFile)
}

private fun loadIncludes(wd: File, f: File): List<File> {
    if (!f.isFile){
        logError("The file ${f.name} does not exist. Full Path: ${f.absolutePath}")
        return emptyList()
    }

    val l = mutableListOf<File>()
    f.forEachLine {
        if (includeRegex.matches(it)) {
            val words = it.split(":")
            if (words.size != 2) errorExit("Include must be the format `include: Filename` and is case sensitive")
            val n = words[1].trim()
            val nf = File(wd, n)
            l.addAll(loadIncludes(wd, nf))
        }
        else { return@forEachLine }
    }
    l.add(f)
    return l.toList()
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
        errorExit("For the first Setup please drag in the map folder! Could read directory ${dir.absolutePath}. Have you deleted the map?")
    if (!dir.isDirectory)
        errorExit("The File ${dir.absolutePath} is not a Directory. Please drag in the whole map folder")
    if (dir.list()?.contains("Info.dat") == false)
        errorExit("The Directory is missing the Info.dat File. Are you using an outdated Editor?")
    if (dir.list()?.contains(mainFileSuffix) == true && args.size == 1)
        logWarning("Remember! After the initial setup, you don't need to drag in the map again. Beatwalls saves that for you!")
    if (dir.list()?.contains(mainFileSuffix) == false)
        initialize(dir)

    saveWorkingDirectory(dir)
    logInfo("Running Beatwalls in ${dir.absolutePath}")
    return dir
}

fun logInfo(msg: String = ""){
    println("[\uD83D\uDCA1 INFO]:    $msg")
}

fun logWarning(msg: String){
    println("[⚠️ WARNING]: $msg")
}
fun logError(msg: String){
    println("[❌ Error]:   $msg")
}

fun errorExit(msg: String): Nothing {
    logError(msg)
    logError("Press enter to exit")
    readLine()
    exitProcess(-1)
}


