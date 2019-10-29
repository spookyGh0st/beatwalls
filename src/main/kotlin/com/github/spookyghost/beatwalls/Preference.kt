package com.github.spookyghost.beatwalls

import java.util.prefs.Preferences

// Preference key
private const val path = ""
private const val bpm = ""
private const val hjsDuration = ""
private const val offset = ""

fun savePath(path: String) {
    val prefs = Preferences.userNodeForPackage(String::class.java)
    prefs.put(com.github.spookyghost.beatwalls.path, path)
}
fun readPath(): String {
    val prefs = Preferences.userNodeForPackage(String::class.java)
    return prefs.get(path, "")
}

fun saveBpm(bpm: String) {
    val prefs = Preferences.userNodeForPackage(String::class.java)
    prefs.put(com.github.spookyghost.beatwalls.bpm, bpm)
}
fun readBpm(): String {
    val prefs = Preferences.userNodeForPackage(String::class.java)
    return prefs.get(bpm, "")
}
fun saveHjsDuration(hjsDuration: String) {
    val prefs = Preferences.userNodeForPackage(String::class.java)
    prefs.put(com.github.spookyghost.beatwalls.hjsDuration, hjsDuration)
}
fun readHjsDuration(): String {
    val prefs = Preferences.userNodeForPackage(String::class.java)
    return prefs.get(hjsDuration, "")
}
fun saveOffset(offset: String) {
    val prefs = Preferences.userNodeForPackage(String::class.java)
    prefs.put(com.github.spookyghost.beatwalls.offset, offset)
}
fun readOffset(): String {
    val prefs = Preferences.userNodeForPackage(String::class.java)
    return prefs.get(offset, "")
}
