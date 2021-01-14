package interpreter

import beatwalls.logInfo
import beatwalls.logWarning
import chart.difficulty.Difficulty
import chart.difficulty._events
import chart.difficulty._notes
import chart.difficulty._obstacles
import chart.info.Info
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.File

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

    init {
        val infoPath = File(workingDirectory, "Info.dat")
        try {
            val json = infoPath.readText()
            info = gson.fromJson(json, Info::class.java)
            infoJson = gson.toJsonTree(json)
        }catch (e:Exception){
            logWarning("Failed to read in the song info.")
            logWarning("Exception message: ${e.message}")
            throw Exception(e)
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
                    // load specific options
                    offset = (info._songTimeOffset + (bm._customData?._editorOffset?:0))
                    diffFile = File(workingDirectory, bm._beatmapFilename)
                }
            }
        }

        if (diffFile == null) {
            logWarning("The Difficulty $characteristic ${difficultyType.name} does not exist")
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
            logWarning("Do you use an up to date Map Editor?")
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
        val text = createDiff(diffJson, obstacles, notes, events)
        diffFile.writeText(text)
        logInfo("Successfully written new Difficulty to ${diffFile.name}")
    }

    internal fun createDiff(diff: JsonElement, obstacles: List<_obstacles>, notes: List<_notes>, events: List<_events>): String {
        val d = diff
        val obsJson = gson.toJsonTree(obstacles)
        val notesJson = gson.toJsonTree(notes)
        val eventsJson = gson.toJsonTree(events)

        // d = d.asJsonObject.remove("_obstacles")
        // d = d.asJsonObject.remove("_notes")
        // d = d.asJsonObject.remove("_events")

        d.asJsonObject.add("_obstacles", obsJson)
        d.asJsonObject.add("_notes", notesJson)
        d.asJsonObject.add("_events", eventsJson)

        return gson.toJson(d)
    }
}