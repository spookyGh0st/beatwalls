package beatwalls

import java.io.File
import java.lang.Exception

object GlobalConfig{
    //flags
    var clearAll: Boolean = false
    var deleteAllPrevious: Boolean = false
    var noUpdate: Boolean = false

    // char options
    var neValues: Boolean = true
    var bwFile: File = File("")
    var ceFile: File = File("")

    var bpm: Double = 0.0
    var hjsDuration = 0.0
    var offset = 0.0


    operator fun invoke(args: Array<String>){
        val charFolder = args.find { File(it).exists() }
        if (charFolder != null) {
            initConfig(charFolder)
            logger.info { "finished Setup, open the file at ${readPath()} now." }
        }
        if (args.contains("--clearAll"))
            clearAll = true
        if (args.contains("--deleteAllPrevious"))
            deleteAllPrevious = true
        if (args.contains("--noUpdate"))
            deleteAllPrevious = true

        reload()
    }

    fun reload() {
        try {
            bwFile = readPath()
            bpm = readBpm()
            hjsDuration = readHjsDuration()
            offset = readOffset()
            neValues = readNeValues()
        }catch (e:Exception){
            errorExit(e) { "Failed to read some parameters. Please drag your song into beatwalls.exe" }
        }
        check()
    }

    private fun check(){
        if(!bwFile.canRead())
            errorExit { "cant read bw file:$bwFile, setup this program by dragging a Song into it" }
        if(!ceFile.canRead())
            errorExit { "cant read customEvent File:$bwFile, setup this program by dragging a Song into it" }
        if(bpm<=0.0)
            errorExit { "Failed to read in the bpm. It cannot be negative or null" }
        if(hjsDuration<=0.0)
            errorExit { "Failed to read in the hjsDuration. It cannot be negative or null" }
        if(hjsDuration<=0.0)
            errorExit { "Failed to read in the hjsDuration. It cannot be negative or null" }
    }
}