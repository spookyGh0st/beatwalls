package com.github.spookyghost.beatwalls

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import java.io.File
import java.net.URL
import java.net.UnknownHostException

const val currentVersion = "v0.7.5"
private val logger = KotlinLogging.logger {}

fun update(){
    //retrieves the latest version
    val latestVersion = getLatestVersion()

    // breaks if up to date
    if(latestVersion == currentVersion)
        return

    // builds the update script
    val s = buildUpdater(latestVersion)

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

fun buildUpdater(version: String): File {
    val os = System.getProperty("os.name")
    val link = "https://github.com/spookyGh0st/beatwalls/releases/download/$version/beatwalls.exe"
    val text = when (os) {
        "Linux" -> """
            sleep 2
            rm beatwalls.exe
            wget $link
            echo "downloaded latest version"
        """.trimIndent()

        "Windows" -> """
        Start-Sleep -s 2
        Remove-Item beatwalls.exe
        Import-Module BitsTransfer
        Start-BitsTransfer -Source $link -Destination beatwalls.exe
        .\beatwalls.exe
        """.trimIndent()

        else -> TODO()
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
    TODO()
}
fun main(){
    buildUpdater(getLatestVersion())
}