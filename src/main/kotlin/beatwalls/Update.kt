package beatwalls

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.net.URL
import java.net.UnknownHostException
import java.time.LocalDateTime
import java.time.Month


// This get's overwritten in the build pipeline
const val currentVersion = "CyanIsAFurry"
const val apiURL = "https://api.github.com/repos/spookygh0st/beatwalls/releases/latest"

fun hello(){
    println("""
__________  __      __ 
\______   \/  \    /  \
 |    |  _/\   \/\/   /
 |    |   \ \        / 
 |______  /  \__/\  /  
        \/        \/   
        
    """.trimIndent())
    println(welcomeMessage())
    println()

    //retrieves the latest version
    logInfo(    "trying to retrieve latest version from github")
    val latestVersion = getLatestVersion()
    logInfo("local version: $currentVersion, latest version: $latestVersion")

    // breaks if up to date
    if (currentVersion == latestVersion || currentVersion == "CyanIsAFurry")
        return

    logInfo("To update Beatwalls to the latest version go to https://github.com/spookyGh0st/beatwalls/releases/latest")
    logInfo("Press Enter to continue without updating")
    readLine()
}

fun getLatestVersion(): String {
    val url =URL(apiURL)
    return try {
        val json = url.readText()
        val latestVersion = Gson().fromJson(json, GithubApi::class.java)!!
        return latestVersion.tag_name
    }catch (e: UnknownHostException){
        logWarning("failed to retrieve new version")
        currentVersion
    }
}

data class GithubApi (
    @SerializedName("tag_name") val tag_name : String
)


fun welcomeMessage(): String{
    val t = LocalDateTime.now()
    return when{
        t.dayOfYear == 1                                        -> "Happy new Year! I hope you had a great year!"
        t.month == Month.DECEMBER && t.dayOfMonth in 24..25     -> "Merry Christmas! Go call your mother, she cares (:"
        t.month == Month.JULY && t.dayOfMonth == 18             -> "It is Beatwalls Birthday! It is now ${t.year - 2018} years old!"
        else -> loadingMessages.random()
    }
}

val loadingMessages = listOf(
    "Beatwalls has now Welcome too!",
    "Did you know that Cyan is a cat?",
    "Cat's are better then Dogs",
    "Now with even more bugs!",
    "get some ice cream. NOW!",
    "(╯°□°）╯︵ ┻━┻",
    "ppinbutt",
    "Go donate some money to a animal shelter!",
    "Go donate some money to you favourite mod author!",
    "You gotta pump those up, those are rookie numbers in this racket",
    "Go ahead, call the cops. They can't un-Vengeful Spirit Nailsmith",
    "No cost too great",
    "NoodleExtensions.dll? More like NoodleGAYtensions.dickll - JohnIsDie",
    "I need to stop sucking - Me everytime I code",
    "Keep me informed on the behaviour of this Program.  As the \"BugFree(tm)\" series didn't turn out too well, I'm starting a new series called the \"ItWorksForMe(tm)\" series, of which Beatwalls is yet another shining example.",
    "Now with extra spice!",
    "DRINK MOAR WATA",
    "Random anime recommendation! Go watch Space Dandy",
    "I'm sad about Facebook",
    "Remember when people where editing lightning events by hand?",
    "If you ever come to germany, visit me and we will go on a roller coaster together!",
    "Remember the human!",
    "The first 90 percent of the code accounts for the first 90 percent of the development time. The remaining 10 percent of the code accounts for the other 90 percent of the development time - Tom Cargill, Bell Labs",
    "XML is a classic political compromise: it balances the needs of man and machine by being equally unreadable to both. - Matthew Might",

)