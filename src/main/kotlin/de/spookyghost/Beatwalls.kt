package de.spookyghost

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

class Beatwalls : CliktCommand() {

    val keepFiles by option ("--keepFiles", "-k",help = "keeps original files as backups").flag(default = false)

    val dryRun by option("--dryRun", "-d",help = "Do not modify filesystem, only log output").flag(default = false)

    val keepWalls by option("--keepWalls","-w",help = "keeps the original walls instead of deleting them").flag(default = false)

    val quiet by option("--quiet", "-q",help = "Do not print to sdtout").flag(default = false)

    val allDirs by option("--allDirs", "-a",help = "Run on all subfolders of given directory").flag(default = false)

    val file: File by argument(help = "difficulty File (e.G expertPlus.dat)").file().validate {
       require((it.isFile && !allDirs && it.toString().contains(".dat")) || (file.isDirectory && allDirs)) { "Specify one Difficulty or use -a for a Folder"}
        //TODO if directory check if it contains difficulty, if it is file check if it is a difficulty
    }

    init {
        versionOption("1.0 Snapshot")
        context {
            helpFormatter = CliktHelpFormatter(showDefaultValues = true)
        }
    }

    override fun run() {
        if(allDirs){


        }


    }
}