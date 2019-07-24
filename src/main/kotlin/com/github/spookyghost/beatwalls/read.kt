package com.github.spookyghost.beatwalls

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
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
        //todo create temp file
    }
    val reader = BufferedReader(FileReader(file))
    val json = reader.readText()
    val base = Gson().fromJson(json, Json4Kotlin_Base::class.java)
    return ArrayList(base.walls)
    //todo return file
}


fun main(){
    val w = WallStructureManager(readAssets())
    println(w.get(arrayListOf("floor")))
}


fun File.isDifficulty() =
    this.isFile && (this.name == "Easy.dat" || this.name == "Normal.dat" || this.name == "Hard.dat" || this.name == "Expert.dat" || this.name == "ExpertPlus.dat" || this.name == "Test.dat") //TODO remove test.dat

fun File.isMap() =
    this.isDirectory && this.list()?.contains("info.dat")?:false


data class Json4Kotlin_Base (

    @SerializedName("walls") val walls : List<CustomWallStructure>
)
