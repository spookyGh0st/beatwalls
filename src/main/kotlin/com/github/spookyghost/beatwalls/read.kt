package com.github.spookyghost.beatwalls

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.*
import java.nio.file.Paths

fun readInfo(f: File):Info{
    val reader = BufferedReader(FileReader(f))
    val json = reader.readText()
    reader.close()
    return Gson().fromJson(json,Info::class.java)
}

fun readDifficulty(f:File): Difficulty {
    val reader = BufferedReader(FileReader(f))
    val json = reader.readText()
    reader.close()
    return Gson().fromJson(json, Difficulty::class.java)
}

fun readAssets():ArrayList<CustomWallStructure>{
    val file =  Paths.get(System.getProperty("user.dir"),"BeatwallAssets.json").toFile()
    if(!file.exists()) {
        writeAssets()
    }
    val reader = BufferedReader(FileReader(file))
    val json = reader.readText()
    val base = Gson().fromJson(json, Json4Kotlin_Base::class.java)
    return ArrayList(base.customWallStructure)
}


fun File.isDifficulty() =
    this.isFile && (this.name == "Easy.dat" || this.name == "Normal.dat" || this.name == "Hard.dat" || this.name == "Expert.dat" || this.name == "ExpertPlus.dat" )

fun File.isSong() =
    this.isDirectory && this.list()?.contains("info.dat")?:false

fun writeDifficulty(pair: Pair<Difficulty,File>){
    try {
        val text = Gson().toJson(pair.component1())
        val writer = BufferedWriter(FileWriter(pair.component2()))
        writer.write(text)
        writer.close()
    }catch (e:Exception){
        logger.error { "Failed to write Difficulty" }
    }
}

fun writeAssets(){
    try {
        val json = ""
        val writer = BufferedWriter(FileWriter(Paths.get(System.getProperty("user.dir"),"BeatwallAssets.json").toFile()))
        writer.write(json)
        writer.close()
    }catch (e:Exception){
        logger.error { "Failed to write Assets" }
    }
}
