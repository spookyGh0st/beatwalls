package com.github.spookyghost.beatwalls

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
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

fun getLatestVersion():String{
    val url =URL("https://api.github.com/repos/spookygh0st/beatwalls/releases/latest")
    return try {
        val json = url.readText()
        val latestVersion = Gson().fromJson(json,GithubApi::class.java)
        latestVersion.tag_name
    }catch (e: UnknownHostException){
        logger.warn { "failed to retrieve new version" }
        currentVersion
    }
}

data class GithubApi (
    @SerializedName("tag_name") val tag_name : String
)

fun buildUpdater(version: String): String{
    TODO()
}

fun executeUpdater(script: String){
    TODO()
}
fun main(){
    getLatestVersion()
}