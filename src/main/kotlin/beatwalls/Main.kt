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
    logger.error { msg.invoke() }
    if (e != null) {
        logger.info { "See Error Log below" }
        logger.error { e.message }
    }
    logger.info("PLEASE FIX THE ERROR AND RESTART THE PROGRAM")
    logger.info("if you think this is a bug, let me know on discord or github")
    readLine()
    exitProcess(-1)
}


