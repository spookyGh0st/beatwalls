package com.github.spookyghost.beatwalls

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.file
import mu.KotlinLogging
import reader.*
import song.*
import structures.WallStructureManager
import java.io.File
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}
class Beatwalls : CliktCommand() {
    //TODO ADD ICON


    private val file: File by argument(help = "difficulty File (e.G expertPlus.dat)").file().validate {
        require((it.isDifficulty()) || it.isSong()) { "Use a SongFolder or DifficultyFile" }
    }

    private val keepFiles by option("--keepFiles", "-k", help = "keeps original files as backups").flag(default = false)

    private val dryRun by option(
        "--dryRun",
        "-d",
        help = "Do not modify filesystem, only log output"
    ).flag(default = false)

    private val keepWalls by option(
        "--keepWalls",
        "-w",
        help = "keeps the original walls instead of deleting them"
    ).flag(default = false)

    private val yes by option("--yes", "-y", help = "skips confirmation").flag(default = false)

    private val bpm by option("--bpm", "-b", help = "Beats per minute").double()


    private var WallCounter = 0
    private var beatsPerMinute = 0.0
    private val difficultyList = mutableMapOf<Difficulty, File>()

    init {
        context {
            helpFormatter = CliktHelpFormatter(showDefaultValues = true)
        }

    }

    override fun run() {

        try {
            WallStructureManager.loadManager(readAssets())
            //adds all the song
            when {
                file.isSong() -> {
                    logger.info { "Detected song. Running the program through all Difficulties which have commands" }
                    val map = Song(file)
                    beatsPerMinute = bpm ?: map.info._beatsPerMinute
                    map.difficultyList.forEach {
                        if (it.component1().containsCommand("bw"))
                            difficultyList += it.toPair()
                    }
                }
                file.isDifficulty() -> {
                    logger.info { "Detected Difficulty" }
                    if (bpm == null)
                        logger.error { "No BPM detected, pls use the -b option" }
                    else
                        beatsPerMinute = bpm as Double
                    difficultyList += Pair(readDifficulty(file), file)
                }
            }

        } catch (e: Exception) {
            logger.error { "Failed to read Song. Is it really in the right format?" }
            exitProcess(-1)
        }


        for (difficulty in difficultyList) {
            logger.info { "\n\nWorking on File ${difficulty.component2()}\n" }

            //print Stuf and warnings
            println("keep old Files: $keepFiles")
            println("dry run: $dryRun")
            println("keep old Walls: $keepWalls")
            if (!yes) {
                println("continue? (y/n)")
                if (readLine()?.toLowerCase() ?: "n" != "y") exitProcess(0)
            }


            //clears the wall if the keepFileFlag is false
            if (keepFiles) {
                val bDiff = difficulty.component1().copy()
                val bPath = File(difficulty.component2().toString() + ".old")
                writeDifficulty(Pair(bDiff, bPath))
                logger.info { "Written Backup to $bPath" }
            }


            //deletes the previous walls, if the keepWallsFlag is false
            if (!keepWalls) {
                difficulty.component1()._obstacles.clear()
                logger.info { "cleared old Difficulty" }
            }


            //with each difficulty, add all walls
            with(difficulty.component1()) {
                for (bookmark in _bookmarks) {

                    //find the right bpm
                    val tempBpm =
                        _BPMChanges.findLast { bpmChanges -> bpmChanges._time <= bookmark._time }?._BPM ?: beatsPerMinute

                    //create an empty list of _obstacles, we will use
                    val list = arrayListOf<_obstacles>()

                    //put the offset in a variable
                    val timeOffset = bookmark._time

                    //implement the commandList
                    val commandList = bookmark.getCommandList("/bw")

                    //add the for each command to the obstacle list
                    for (command in commandList) {
                        list.addAll(WallStructureManager.get(command))
                        WallCounter++
                    }

                    //adjust the bpm for each _obstacle
                    for (obstacle in list) {
                        obstacle.adjustBPM(beatsPerMinute, tempBpm, timeOffset)
                        _obstacles.add(obstacle)
                    }
                }
            }

            //writes the difficulty
            if (!dryRun) {
                writeDifficulty(difficulty.toPair())
                logger.info { "written new Difficulty" }
            }
        }

        logger.info { "\nfinished run, written $WallCounter Wall Structures" }
        println("press enter to exit")
        readLine()
    }
}

