package assetFile

import com.github.spookyghost.beatwalls.errorExit
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import reader.isSong
import structure.CustomWallStructure
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.nio.file.Paths
import kotlin.system.exitProcess



class AssetFile(
    @SerializedName("Version")
    @Expose
    val version: Double = 1.0,
    @SerializedName("Active Song")
    @Expose
    var songPath: String ="",
    @SerializedName("Directory")
    @Expose
    var directory: String = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\Beat Saber\\Beat Saber_Data\\CustomWIPLevels\"",
    @SerializedName("SongList")
    @Expose
    val songList: ArrayList<SavedSongData> = arrayListOf(),
    @SerializedName("Mixed Structure List")
    @Expose
    val mixedWallStructure: ArrayList<MixedStructure> = arrayListOf(),
    @SerializedName("Wall Structure List")
    @Expose
    val customWallStructure: ArrayList<CustomWallStructure> = arrayListOf()
) {

    /** Storing the songPath everytime this is set */
    var currentSong:SavedSongData?
        get() {
            return songList.find { it.SongPath == songPath }
        }set(value) {
        songPath= value?.SongPath ?: ""
    }
    @Transient
    private val logger = KotlinLogging.logger {}


//    ____        _     _ _        _____                 _   _
//   |  _ \ _   _| |__ | (_) ___  |  ___|   _ _ __   ___| |_(_) ___  _ __  ___
//   | |_) | | | | '_ \| | |/ __| | |_ | | | | '_ \ / __| __| |/ _ \| '_ \/ __|
//   |  __/| |_| | |_) | | | (__  |  _|| |_| | | | | (__| |_| | (_) | | | \__ \
//   |_|    \__,_|_.__/|_|_|\___| |_|   \__,_|_| |_|\___|\__|_|\___/|_| |_|___/




    /** adds the directory to the file */
    fun changeDirectory(){
        println("Please enter the Path to the directory, leave blank to default to default CustomWIP Levels")
        val input = readLine()?:"C:\\Program Files (x86)\\Steam\\steamapps\\common\\Beat Saber\\Beat Saber_Data\\CustomWIPLevels\""
        val file = File(input.removeSuffix("\"").removePrefix("\""))
        when {
            file.isDirectory && file.walk().mapNotNull { it.isSong() }.toList().isNotEmpty() -> this.directory = file.absolutePath
            else -> errorExit { "Failed to read Directory ${file.absolutePath}. Is it really a directory?" }
        }
        save()
    }

    /** Asks to change the Song */
    fun changeSong(){
        currentSong = if(songList.size==1){
            songList.first()
        }else{
            pickSong()
        }
    }

    /** Saves the AssetFile to its Default Location */
    fun save(){
        val file: File = Paths.get(File("").absoluteFile.path, "Asset.json").toFile()
        try {
            val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create()
            val json = gson.toJson(this)
            val writer = BufferedWriter(FileWriter(file))
            writer.write(json)
            writer.close()
            if (!file.exists())
                throw FileNotFoundException()
            logger.info { "saved AssetFile at ${file.absolutePath}" }
        }catch (e:Exception){
            logger.error { "Failed to save Assets" }
            logger.error { e.message }
            exitProcess(1)
        }
    }

//    ____       _            _         _____                 _   _
//   |  _ \ _ __(_)_   ____ _| |_ ___  |  ___|   _ _ __   ___| |_(_) ___  _ __  ___
//   | |_) | '__| \ \ / / _` | __/ _ \ | |_ | | | | '_ \ / __| __| |/ _ \| '_ \/ __|
//   |  __/| |  | |\ V / (_| | ||  __/ |  _|| |_| | | | | (__| |_| | (_) | | | \__ \
//   |_|   |_|  |_| \_/ \__,_|\__\___| |_|   \__,_|_| |_|\___|\__|_|\___/|_| |_|___/


    /** Adding a new Song to the List */
    private fun addSong(file: File) {
        val p = file.absolutePath
        if(!songList.any {it.SongPath == p}){
            val folderName = file.name
            logger.info { "adding $folderName to the Songlist" }
            val n = getNJSOffset()
            songList.add(SavedSongData(p,n))
        }
    }

    fun scanDirectory() {
        File(directory)
            .walk()
            .filter { it.isSong() }
            .forEach { addSong(it) }
    }

    /** prints the Songs with Indixes */
    private fun printSongList(){
        println("\nSaved Songs:")
        for((index, s) in songList.withIndex()){
            println("\t$index: ${s.SongPath}")
        }
        println()
    }

    /** Asking for NJS */
    private fun getNJSOffset(): Double {
        return try {
            println("Enter the NJS Offset for the File (usually 2)")
            val njsInput = readLine()?:"2"
            val njs = njsInput.toDoubleOrNull()?:2.0
            logger.info { "Set the NJS Offset to $njs" }
            njs
        }catch (e:Exception){
            logger.error { "Something went wrong, try again" }
            getNJSOffset()
        }
    }

    /** Asking for a Path */
    private fun readSongPath(): File {
        println("Please Enter the Path of the Song you want to work on")
        val str = readLine()
        return try {
            val songP = str!!.removePrefix("\"").removeSuffix("\"")
            val f = File(songP).absoluteFile
            if(!f.isSong()){
                throw FileNotFoundException("File is not a Song")
            }
            f
        }catch (e:Exception){
            logger.error { "Failed to Read Song" }
            logger.error { e.message }
            readSongPath()
        }
    }

    /** Picks a Song and is mean if you try to be funny */
    private fun pickSong():SavedSongData{
        printSongList()
        println("\nPlease pick the Song you want to use\n")
        print("index: ")
        val index = readLine()?.toIntOrNull()
        val song =  songList.getOrNull(index = index?:-1)
        if(song == null)
            println("""
 .----.     .----.   __.....__                                                                 _..._      _..._                  
  \    \   /    /.-''         '.                 .-.          .-               _.._          .'     '.  .'     '..-.          .- 
   '   '. /'   //     .-''"'-.  `. .-,.--. .-,.--.\ \        / /             .' .._|        .   .-.   ..   .-.   .\ \        / / 
   |    |'    //     /________\   \|  .-. ||  .-. |\ \      / /              | '            |  '   '  ||  '   '  | \ \      / /  
   |    ||    ||                  || |  | || |  | | \ \    / /             __| |__  _    _  |  |   |  ||  |   |  |  \ \    / /   
   '.   `'   .'\    .-------------'| |  | || |  | |  \ \  / /             |__   __|| '  / | |  |   |  ||  |   |  |   \ \  / /    
    \        /  \    '-.____...---.| |  '- | |  '-    \ `  /                 | |  .' | .' | |  |   |  ||  |   |  |    \ `  /     
     \      /    `.             .' | |     | |         \  /                  | |  /  | /  | |  |   |  ||  |   |  |     \  /      
      '----'       `''-...... -'   | |     | |         / /                   | | |   `'.  | |  |   |  ||  |   |  |     / /       
                                   |_|     |_|     |`-' /                    | | '   .'|  '/|  |   |  ||  |   |  | |`-' /        
                                                    '..'                     |_|  `-'  `--' '--'   '--''--'   '--'  '..'         
        """.trimIndent())
        return song?:pickSong()

    }
}



