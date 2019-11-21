package com.github.spookyghost.beatwalls

import java.io.File
import java.util.prefs.Preferences

// Preference key
private const val path = ""
private const val bpm = ""
private const val hjsDuration = ""
private const val offset = ""

class Main
val prefs: Preferences = Preferences.userNodeForPackage(Main::class.java)

fun savePath(path: File) {
    prefs.put("path", path.absolutePath)
}
fun readPath(): File {
    return File(prefs.get("path", path))
}
fun saveBpm(bpm: Double) {
    prefs.put("bpm", bpm.toString())
}
fun readBpm(): Double {
    return prefs.get("bpm", bpm).toDouble()
}
fun saveHjsDuration(hjsDuration: Double) {
    prefs.put("hjsDuration", hjsDuration.toString())
}
fun readHjsDuration(): Double {
    return prefs.get("hjsDuration", hjsDuration).toDouble()
}
fun saveOffset(offset: Double) {
    prefs.put("offset", offset.toString())
}
fun readOffset(): Double {
    return prefs.get("offset", offset).toDouble()
}
