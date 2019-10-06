package com.github.spookyghost.beatwalls

import assetFile.AssetController
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import mu.KotlinLogging
import reader.writeDifficulty
import structure.CustomWallStructure
import structure.StructureManager
import structure.Wall

class BeatWalls: CliktCommand(){
    private val logger = KotlinLogging.logger {}
    val c by option().flag()
    val d by option().flag()


    override fun run() {
        if(c) AssetController.changeSong()
        val song = AssetController.currentSong()
        val difficultyList =song.difficultyList
        //with each difficulty, add all walls
        for(difficulty in difficultyList) {
            with(difficulty.component1()) {
                //todo clears the obstacles
                _obstacles.clear()

                for (bookmark in _bookmarks) {
                    //gets all the walls
                    val walls = bookmark
                        .toCommandList("/bw")
                        .flatMap { StructureManager.walls(it,this) }

                    //converts the list to _obstacle and adds it
                    _obstacles.addAll(walls.map { it.to_obstacle() })
                }

                /** TODO make this better */
                for (bookmark in _bookmarks) {
                    val walls = bookmark
                        .toCommandList("/bw-save")
                    for(command in walls){
                        val name = command.command.split(" ").filterNot { it.isEmpty() }[0]
                        val duration = command.command.split(" ").filterNot { it.isEmpty() }.getOrNull(1)?.toDoubleOrNull()?:1.0
                        val offset =  if(command.command.contains("-t")) AssetController.njsOffset() else 0.0
                        val list = ArrayList(_obstacles
                            .filter {
                                it._time in (command.beatStartTime+offset)..(command.beatStartTime+duration+offset)
                            }
                            .map { it.toWall() }
                        )
                        val struct =CustomWallStructure(name,list)
                        println(struct.wallList.size)
                        if(struct.wallList.isNotEmpty())
                        AssetController.customWallStructures().add(struct)
                        AssetController.save()
                    }
                }
            }
            writeDifficulty(difficulty.toPair())
        }
    }
}
