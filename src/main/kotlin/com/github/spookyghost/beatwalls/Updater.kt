package com.github.spookyghost.beatwalls

const val currentVersion = "v0.7.5"

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
    class GithubApi( val tag_name: String)
    TODO()
}

fun buildUpdater(version: String): String{
    TODO()
}

fun executeUpdater(script: String){
    TODO()
}