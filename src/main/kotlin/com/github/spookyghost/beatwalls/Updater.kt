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


const val currentVersion = "v0.7.5"
private val logger = KotlinLogging.logger {}

fun update(){
    //retrieves the latest version
    val latestVersion = getLatestVersion()

    // breaks if up to date
    if(latestVersion == currentVersion)
        return

    // download the latest version from the github release page
    downloadUpdate(latestVersion)

    // builds the update script
    val s = buildUpdater()

    //executes the update script
    executeUpdater(s)
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
            sleep 2
            rm beatwalls.exe
            mv beatwals.exe.temp beatwalls.exe
            ./beatwalls.exe
        """.trimIndent() //todo download jar
        "Windows" -> """
        Start-Sleep -s 2
        del beatwalls.exe
        ren beatwalls.exe.temp beatwalls.exe
        .\beatwalls.exe
        """.trimIndent()
        else -> "".also {  errorExit { "Only" } }
    }
    val file = when(os){
        "Linux" -> File("bwUpdater.sh")
        "Windows" -> File("bwUpdater.ps1")
        else -> TODO()
    }
    file.writeText(text)
    file.setExecutable(true)
    return file
}

fun executeUpdater(file: File){
    try {
        Runtime.getRuntime().exec("./${file}")
        exitProcess(0)
    }catch (e:Exception){
        errorExit { "Failed to launch updater. Please download the latest version manually" }
    }
}


