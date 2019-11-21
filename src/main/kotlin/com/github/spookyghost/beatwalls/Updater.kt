package com.github.spookyghost.beatwalls

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.net.UnknownHostException
import java.nio.channels.Channels
import kotlin.system.exitProcess


const val currentVersion = "CyanIsAFurry"
private val logger = KotlinLogging.logger {}

fun update(){
    //delete the updater
    deleteUpdater()

    //retrieves the latest version
    val latestVersion = getLatestVersion()

    // breaks if up to date
    if(latestVersion == currentVersion || currentVersion == "CyanIsAFurry")
        return

    // download the latest version from the github release page
    downloadUpdate(latestVersion)

    // builds the update script
    val s = buildUpdater()

    //executes the update script
    executeUpdater(s)
}

fun deleteUpdater(){
    val f = File("bwUpdater.bat")
    if(f.exists())
        f.deleteOnExit()
}

fun getLatestVersion(): String {
    val url =URL("https://api.github.com/repos/spookygh0st/beatwalls/releases/latest")
    return try {
        val json = url.readText()
        val latestVersion =  Gson().fromJson(json,GithubApi::class.java)!!
        return latestVersion.tag_name
    }catch (e: UnknownHostException){
        logger.warn { "failed to retrieve new version" }
        currentVersion
    }
}

data class GithubApi (
    @SerializedName("tag_name") val tag_name : String
)

fun downloadUpdate(version: String) {
    val website = URL("https://github.com/spookyGh0st/beatwalls/releases/download/$version/beatwalls.exe")
    val file = File("beatwalls.exe.temp")
    try {
        logger.info { "Downloading latest version" }
        val rbc = Channels.newChannel(website.openStream())
        val fos = FileOutputStream("beatwalls.exe.temp")
        fos.channel.transferFrom(rbc, 0, java.lang.Long.MAX_VALUE)
        file.setExecutable(true)
        if(!file.canExecute()) throw Exception()
    }catch (e:Exception){
        errorExit { "Failed to download the latest version. Please download it manually" }
    }
}

fun buildUpdater(): File {
    val os = System.getProperty("os.name")
    val text = when (os) {
        "Linux" -> """
            sleep 1
            rm beatwalls.exe
            mv beatwals.exe.temp beatwalls.exe
            ./beatwalls.exe
        """.trimIndent() //todo download jar
        "Windows 10" -> """
        @ping -n 1 localhost> nul
        del /Q beatwalls.exe
        ren beatwalls.exe.temp beatwalls.exe
        .\beatwalls.exe
        """.trimIndent()
        else -> "".also {  errorExit { "Only Windows 10 or linux updater are supported at the moment. Pleas download the latest version manually" } }
    }
    val file = when(os){
        "Linux" -> File("bwUpdater.sh")
        "Windows 10" -> File("bwUpdater.bat")
        else ->File("").also{ errorExit { "Only Windows 10 or linux updater are supported at the moment. Pleas download the latest version manually" }}
    }
    file.writeText(text)
    file.setExecutable(true)
    return file
}

fun executeUpdater(file: File){
    try {
        val os = System.getProperty("os.name")
        if(os == "Linux")
            Runtime.getRuntime().exec("./${file}")
        if(os == "Windows 10")
            Runtime.getRuntime().exec("cmd /c start \"\" $file")
        exitProcess(0)
    }catch (e:Exception){
        errorExit { "Failed to launch updater. Please download the latest version manually" }
    }
}


