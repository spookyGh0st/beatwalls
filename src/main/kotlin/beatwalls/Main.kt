package beatwalls

import kotlinx.coroutines.ExperimentalCoroutinesApi
import mu.KotlinLogging
import kotlin.system.exitProcess

internal val logger = KotlinLogging.logger("beatwalls")

@ExperimentalCoroutinesApi
suspend fun main(args: Array<String>) {
    GlobalConfig(args)
    update()
    run()
    runOnChange { run() }
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


