package com.github.spookyghost.beatwalls

import kotlinx.coroutines.ExperimentalCoroutinesApi
import mu.KotlinLogging
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
    if(args.isNotEmpty()){
        initConfig(args[0])
        logger.info { "finished Setup, open the file at ${readPath()} now."}
    }
}

private val logger = KotlinLogging.logger {}

fun errorExit(e:Exception? = null, msg: () -> Any){
    logger.error { msg.invoke() }
    if(e != null){
        logger.info { "See Error Log below" }
        logger.error { e.message }
    }
    readLine()
    exitProcess(-1)
}


