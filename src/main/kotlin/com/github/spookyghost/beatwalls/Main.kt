package com.github.spookyghost.beatwalls

import mu.KotlinLogging
import kotlin.system.exitProcess

fun main(args: Array<String>) = Beatwalls().main(args)

private val logger = KotlinLogging.logger {}

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
