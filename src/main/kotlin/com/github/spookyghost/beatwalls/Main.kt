package com.github.spookyghost.beatwalls

import mu.KotlinLogging
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    if(args.isNotEmpty()){
        initConfig(args[0])
        exit{"finished Setup"}
    }
    else{
        val path = readPath()
        if(path.isEmpty())
            errorExit() { "No File stored, setup this program by dragging a Song into it" }
        run(path)
        exit { "Finished Run" }
    }
}

private val logger = KotlinLogging.logger {}

fun exit(msg: () -> Any){
    println(msg.invoke())
    println("\nPress Enter to Exit")
    readLine()
    exitProcess(0)
}

fun errorExit(e:Exception? = null, msg: () -> Any){
    println(msg.invoke())
    if(e != null){
        logger.info { "See Error Log below" }
        logger.error { e.message }
    }
    println("\nPress Enter to Exit")
    readLine()
    exitProcess(1)
}


