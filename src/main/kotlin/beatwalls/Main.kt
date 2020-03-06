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
    readLine()
    exitProcess(-1)
}


