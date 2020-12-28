package beatwalls

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.net.URL
import java.net.UnknownHostException


// This get's overwritten in the build pipeline
const val currentVersion = "CyanIsAFurry"
const val apiURL = "https://api.github.com/repos/spookygh0st/beatwalls/releases/latest"

fun update(){
    //retrieves the latest version
    println("trying to retrieve latest version from github")
    val latestVersion = getLatestVersion()
    println("local version: $currentVersion, latest version: $latestVersion")

    // breaks if up to date
    if (currentVersion == latestVersion || currentVersion == "CyanIsAFurry" || GlobalConfig.noUpdate)
        return

    println("To update Beatwalls to the latest version go to https://github.com/spookyGh0st/beatwalls/releases/latest")
    println("Press Enter to continue without updating")
    readLine()
}

fun getLatestVersion(): String {
    val url =URL(apiURL)
    return try {
        val json = url.readText()
        val latestVersion = Gson().fromJson(json, GithubApi::class.java)!!
        return latestVersion.tag_name
    }catch (e: UnknownHostException){
        logger.warn { "failed to retrieve new version" }
        currentVersion
    }
}

data class GithubApi (
    @SerializedName("tag_name") val tag_name : String
)