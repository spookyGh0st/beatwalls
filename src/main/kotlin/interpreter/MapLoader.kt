package interpreter

import beatwalls.logError
import beatwalls.logInfo
import beatwalls.logWarning
import chart.difficulty.*
import chart.info.Info
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*

/**
 * I don't want to keep up with all possible changes in the Json Format.
 * I still however want to write to one or more difficulty without deleting all the new stuff.
 * That's why this class is used to handle all the Reading/writing of the map
 */
class MapLoader(val workingDirectory: File) {
    private val info: Info
    private val infoJson: JsonElement
    private val diffs = mutableMapOf<File, JsonElement>()
    private val gson = Gson()
    private val autosaveDir = File(workingDirectory,"autosaves")

    init {
        val infoPath = File(workingDirectory, "Info.dat")
        try {
            var json = infoPath.readText()
            if (!json.startsWith("{"))
                json = infoPath.readText(charset("UTF16"))
            info = gson.fromJson(json, Info::class.java)
            infoJson = gson.toJsonTree(json)
        }catch (e:Exception){
            logWarning("Failed to read in the song info.")
            logWarning("Exception message: ${e.message}")
            throw Exception(e)
        }
        if (!autosaveDir.isDirectory){
            logInfo("Creating autosave Directory")
            autosaveDir.mkdir()
        }
    }

    fun loadInfo() = info

    fun loadDifficulty(characteristic: String, difficultyType: Options.DifficultyType): Difficulty? {
        var diffFile: File? = null
        var offset = 0.0

        // find the target Difficulty
        for (bms in info._difficultyBeatmapSets){
            for (bm in bms._difficultyBeatmaps){
                if (bms._beatmapCharacteristicName.equals(characteristic, ignoreCase = true) && bm._difficultyRank == difficultyType.rank){
                    if (bm._customData?._requirements?.contains("Noodle Extensions") == false && !bm._customData._requirements.contains("Mapping Extensions"))
                        logWarning("The Difficulty does not have the requirements set. Please add Noodle Extensions or Mapping Extensions")
                    if (bm._customData?._requirements?.contains("Chroma") == false)
                        logWarning("The Difficulty does not have the requirement Chroma set. Please add it to support colors.")
                    // load specific options
                    offset = (info._songTimeOffset + (bm._customData?._editorOffset?:0))
                    diffFile = File(workingDirectory, bm._beatmapFilename)
                }
            }
        }

        if (diffFile == null) {
            logError("The Difficulty $characteristic ${difficultyType.name} does not exist.")
            var s = "All available Difficulties:"
            for (d in info._difficultyBeatmapSets){
                s+="\n              Characteristic: ${d._beatmapCharacteristicName}:"
                for(m in d._difficultyBeatmaps){
                    s += "\n               - ${m._difficulty}"
                }
            }
            logError(s)
            return null
        }

        val diffText = try{
            diffFile.readText()
        }catch (e:java.lang.Exception){
            logWarning("Failed to read in the Difficulty $diffFile.")
            logWarning("It seems like your Info.dat is corrupt")
            logWarning("Exception message: ${e.message}")
            return null
        }

        val diff = try {
            gson.fromJson(diffText, Difficulty::class.java)
        }catch (e:Exception){
            logWarning("Failed to parse the difficulty to the model $diffFile.")
            logWarning("Do you use an up to date Map Editor? If yes, leave me a message")
            logWarning("Exception message: ${e.message}")
            return null
        }

        diffs[diffFile] = gson.fromJson(diffText, JsonObject::class.java)

        diff.bpm = info._beatsPerMinute
        diff.offset = offset
        diff.file = diffFile

        return diff
    }

    fun writeDiff(difficulty: Difficulty, obstacles: List<_obstacles>, notes: List<_notes>, events: List<_events>) {
        val diffFile = difficulty.file
        val diffJson = diffs[diffFile]
        if (diffJson == null) {
            logWarning("Cannot write ${diffFile.name}, did not properly load difficulty")
            return
        }

        runBlocking {
            launch {
                val backupFile = File(autosaveDir,"${Calendar.getInstance().time.time}-${diffFile.name}")
                val backupDiffJson = gson.toJson(diffJson)
                logInfo("Creating backupfile under ${backupFile.name}")
                backupFile.writeText(backupDiffJson)
            }
            launch {
                val text = createDiff(diffJson, obstacles, notes, events)
                diffFile.writeText(text)
                logInfo("Successfully written new Difficulty to ${diffFile.name}")
            }
        }
    }

    internal fun createDiff(diff: JsonElement, obstacles: List<_obstacles>, notes: List<_notes>, events: List<_events>): String {
        if (obstacles.isNotEmpty()){
            val json = gson.toJsonTree(obstacles)
            diff.asJsonObject.add("_obstacles", json)
        }
        if (notes.isNotEmpty()){
            val json = gson.toJsonTree(notes)
            diff.asJsonObject.add("_notes", json)
        }
        if (events.isNotEmpty()){
            val json = gson.toJsonTree(events)
            diff.asJsonObject.add("_events", json)
        }

        logInfo("Added ${obstacles.size} Walls, ${notes.size} notes and ${events.size} events to difficulty.")
        return gson.toJson(diff)
    }
}