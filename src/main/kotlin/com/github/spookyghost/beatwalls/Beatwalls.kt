package com.github.spookyghost.beatwalls

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import mu.KotlinLogging
import java.io.File

val logger = KotlinLogging.logger{}

class Beatwalls : CliktCommand() {

    private val file: File by argument(help = "difficulty File (e.G expertPlus.dat)").file().validate {
        require((it.isDifficulty()) || it.isSong() ||(file.isDirectory && allDirs)) { "Specify one Difficulty or use -a for a Folder"}
    }

    private val keepFiles by option ("--keepFiles", "-k",help = "NOT IMPLEMENTED - keeps original files as backups").flag(default = false)

    private val dryRun by option("--dryRun", "-d",help = "NOT IMPLEMENTED - Do not modify filesystem, only log output").flag(default = false)

    private val keepWalls by option("--keepWalls","-w",help = "keeps the original walls instead of deleting them").flag(default = false)

    private val quiet by option("--quiet", "-q",help = "NOT IMPLEMENTED - Do not print to sdtout").flag(default = false)

    private val yes by option("--yes", "-y",help = "skips confirmation").flag(default = false)

    private val allDirs by option("--allDirs", "-a",help = "NOT IMPLEMENTED - Run on all subfolders of given directory").flag(default = false)

    private val bpm by option("--bpm", "-b",help="Beats per minute").double()

    private val spawnDistance by option("--spawnDistance","-s",help="SpawnDistance for timed walls").int().default(2)

    //todo find the spawndistance by decompiling bs under https://bsmg.wiki/modding/extras#dn-spy and check kyles convo



    init {
        context {
            helpFormatter = CliktHelpFormatter(showDefaultValues = true)
        }

    }

    override fun run() {
        var beatsPerMinute = 0.0
        val difficultyList = mutableMapOf<Difficulty,File>()

        try {
            WallStructureManager.loadManager(readAssets())
            when {
                file.isSong() -> {
                    logger.info { "Detected Song. Running the program through all Difficulties which have commands" }
                    val map = Song(file)
                    beatsPerMinute = bpm?:map.info._beatsPerMinute
                    map.difficultyList.forEach {
                        if (it.component1().containsCommand("bw"))
                            difficultyList += it.toPair()
                    }
                }
                file.isDifficulty() -> {
                    logger.info{"Detected Difficulty"}
                    if (bpm == null)
                        logger.error { "No BPM detected, pls use the -b option" }
                    else
                        beatsPerMinute = bpm as Double
                    difficultyList += Pair(readDifficulty(file),file)
                }
                else -> {
                    TODO()
                }
            }

        }catch (e:Exception){
            logger.error { "Failed to resolve File" }
        }


        difficultyList.forEach {

            //prints stuff if the quiet option is false
            printWarnings()

            //clears the wall if the keepwallsflag is false
            if (!keepWalls) it.component1()._obstacles.clear()

            it.component1().createWalls(beatsPerMinute, spawnDistance)
            if(!dryRun)
                writeDifficulty(it.toPair())
        }
        println("press enter to exit")
        readLine()
    }
    fun printWarnings() {
        if (!quiet) {
            println("keep old Files: $keepFiles")
            println("dry run: $dryRun")
            println("keep old Walls: $keepWalls")
            if (!yes) {
                println("continue? (y/n)")
                if (readLine()?.toLowerCase() ?: "n" != "y") TODO("KILL programm and spit error message")
            }
        }
    }
}

