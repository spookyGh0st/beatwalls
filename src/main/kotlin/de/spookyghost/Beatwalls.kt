package de.spookyghost

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.file
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Beatwalls : CliktCommand() {

    val keepFiles by option ("--keepFiles", "-k",help = "keeps original files as backups").flag(default = false)

    val dryRun by option("--dryRun", "-d",help = "Do not modify filesystem, only log output").flag(default = false)

    val keepWalls by option("--keepWalls","-w",help = "keeps the original walls instead of deleting them").flag(default = false)

    val quiet by option("--quiet", "-q",help = "Do not print to sdtout").flag(default = false)

    val allDirs by option("--allDirs", "-a",help = "Run on all subfolders of given directory").flag(default = false)

    val file: File by argument(help = "difficulty File (e.G expertPlus.dat)").file().validate {
       require((it.isFile && !allDirs && it.toString().contains(".dat")) || (file.isDirectory && allDirs)) { "Specify one Difficulty or use -a for a Folder"}
    }

    init {
        versionOption("1.0 Snapshot")
        context {
            helpFormatter = CliktHelpFormatter(showDefaultValues = true)
        }
    }

    override fun run() {
        val difficulties = arrayListOf<Difficulty>()
        if(allDirs)
            TODO("ADD EACH DIRECTORY")
        else
            difficulties.add(readDifficulty(file))

    }
}


fun readDifficulty(f:File):Difficulty{
    val reader = BufferedReader(FileReader(f))
    val json = reader.readText()
    reader.close()
    //TODO return the difficulty
    return Gson().fromJson(json, Difficulty::class.java)
}
fun checkName(s:String) = s.contains(".dat") && !s.contains("info")