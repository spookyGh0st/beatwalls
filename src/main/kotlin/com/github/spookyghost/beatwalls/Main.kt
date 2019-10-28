package com.github.spookyghost.beatwalls

import assetFile.AssetFile
import assetFile.readAssetFile
import mu.KotlinLogging
import java.io.File
import java.lang.IndexOutOfBoundsException
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    try {
        val myArgs: Array<String> = if (args.isEmpty() && File(readAssetFile().currentSong).exists())
            arrayOf(readAssetFile().currentSong)
        else
            args

        BeatWalls().main(myArgs)
        println("Succesfull")
        readLine()

    } catch (e: IndexOutOfBoundsException ) {
        errorExit(e) { "Something failed" }
    }
}

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
