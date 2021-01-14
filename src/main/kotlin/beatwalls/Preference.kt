package beatwalls

import java.io.File
import java.util.prefs.Preferences


class Main
val prefs: Preferences = Preferences.userNodeForPackage(Main::class.java)

fun saveWorkingDirectory(wd: File) {
    prefs.put("workingDirectory", wd.absolutePath)
}

fun readWorkingDirectory(): File {
    return File(prefs.get("workingDirectory", ""))
}
