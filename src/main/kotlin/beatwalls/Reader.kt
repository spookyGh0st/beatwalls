package beatwalls

import com.google.gson.Gson
import com.worldturner.medeia.api.UrlSchemaSource
import com.worldturner.medeia.api.gson.MedeiaGsonApi
import com.worldturner.medeia.schema.validation.SchemaValidator
import chart.difficulty.Difficulty
import chart.difficulty._obstacles
import mu.KotlinLogging
import chart.song.Info
import java.io.File

private val logger = KotlinLogging.logger {}

object AssetReader {
    private val api = MedeiaGsonApi()
    private val gson = Gson()

    private fun getSongInfo(file: File): Info {
        val infoValidator = loadSchema("info")
        val dataReader = file.reader()
        val validatedReader = api.createJsonReader(infoValidator, dataReader)
        return gson.fromJson<Info>(validatedReader, Info::class.java)
    }

    //  ____  _  __  __ _            _ _
    // |  _ \(_)/ _|/ _(_) ___ _   _| | |_ _   _
    // | | | | | |_| |_| |/ __| | | | | __| | | |
    // | |_| | |  _|  _| | (__| |_| | | |_| |_| |
    // |____/|_|_| |_| |_|\___|\__,_|_|\__|\__, |
    //                                     |___/
    fun getDifficulty(): Difficulty {
        val diffFile = readDifficultyFile()
        return try {
            parseDifficulty(diffFile)
        } catch (e: Exception) {
            errorExit(e) { "Failed to parse the Difficulty. Do you use an up to date editor?" }
        }
    }

    fun writeDifficulty(diff: Difficulty) {
        try {
            val file = readDifficultyFile()
            val json = Gson().toJson(diff)
            //https://stackoverflow.com/questions/11119094/switch-off-scientific-notation-in-gson-double-serialization#18892735
            file.writeText(json)
            logger.info { "written Chart.difficulty file to $file" }
        } catch (e: Exception) {
            errorExit(e) { "Failed to write Chart.difficulty" }
        }
    }

    private fun parseDifficulty(file: File): Difficulty {
        val difficultyValidator = loadSchema("difficulty")
        val dataReader = file.reader()
        val validatedReader = api.createJsonReader(difficultyValidator, dataReader)
        return gson.fromJson<Difficulty>(validatedReader, Difficulty::class.java)
    }

    private fun readDifficultyFile(): File = try {
        val file = readPath()
        val name = file.nameWithoutExtension + ".dat"
        val directory = file.parentFile
        File(directory, name)
    } catch (e: Exception) {
        errorExit(e) { "Failed to read in the AssetFile" }
    }


    private fun loadSchema(name: String): SchemaValidator {
        val source = UrlSchemaSource(
            javaClass.getResource("/schemas/$name.schema.json")
        )
        return api.loadSchema(source)

    }


    //        _     _  ___  _         _             _
    //   ___ | | __| |/ _ \| |__  ___| |_ __ _  ___| | ___
    //  / _ \| |/ _` | | | | '_ \/ __| __/ _` |/ __| |/ _ \
    // | (_) | | (_| | |_| | |_) \__ \ || (_| | (__| |  __/
    //  \___/|_|\__,_|\___/|_.__/|___/\__\__,_|\___|_|\___|
    fun getOldObstacles(): Array<_obstacles> {
        val file = readOldObstacleFile()
        return try {
            val json = file.readText()
            Gson().fromJson(json, Array<_obstacles>::class.java)!!
        } catch (e: Exception) {
            arrayOf()
        }
    }

    fun writeOldObstacle(l: Array<_obstacles>) {
        val file = readOldObstacleFile()
        try {
            val json = Gson().toJson(l)
            file.writeText(json)
        } catch (e: Exception) {
            errorExit(e) { "Failed to write Old Obstacles" }
        }
    }

    private fun readOldObstacleFile(): File = try {
        val file = readPath()
        val name = file.nameWithoutExtension + ".oldObst"
        val directory = file.parentFile
        File(directory, name)
    } catch (e: Exception) {
        errorExit(e) { "Failed to read in the oldObst" }
    }
}




