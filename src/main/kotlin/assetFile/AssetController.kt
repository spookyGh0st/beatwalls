package assetFile

import com.google.gson.Gson
import mu.KotlinLogging
import song.Song
import structure.CustomWallStructure
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths
import kotlin.system.exitProcess


@Suppress("unused", "MemberVisibilityCanBePrivate")
object AssetController{
    private val logger = KotlinLogging.logger {}
    private val file: File = Paths.get(File("").absoluteFile.path, "Asset.json").toFile()
    private val asset = readAssetFile()

//    ____        _     _ _        _____                 _   _
//   |  _ \ _   _| |__ | (_) ___  |  ___|   _ _ __   ___| |_(_) ___  _ __  ___
//   | |_) | | | | '_ \| | |/ __| | |_ | | | | '_ \ / __| __| |/ _ \| '_ \/ __|
//   |  __/| |_| | |_) | | | (__  |  _|| |_| | | | | (__| |_| | (_) | | | \__ \
//   |_|    \__,_|_.__/|_|_|\___| |_|   \__,_|_| |_|\___|\__|_|\___/|_| |_|___/

    /** Adds a Song to the Asset File */
    fun addSong() {
        asset.addSong()
        asset.save()
    }

    /** changes the Song */
    fun changeSong() {
        if(asset.songList.isEmpty())
            addSong()
        asset.changeSong()
        asset.save()
    }

    fun currentSong():Song{
        val str = asset.currentSong?.SongPath
        //Adds a Song if none exist
        if(str == null){
            asset.changeSong()
            return currentSong()
        }
        return try {
            Song(File(str))
        }catch (e:Exception){
            errorExit(e) { "Failed to read the current Song, check the syntax of $str"}
            currentSong()
        }
    }

    fun customWallStructures() =
        asset.currentSong!!.customWallStructure

    fun mixedWallStructures() =
        asset.currentSong!!.mixedWallStructure

//    ____       _            _         _____                 _   _
//   |  _ \ _ __(_)_   ____ _| |_ ___  |  ___|   _ _ __   ___| |_(_) ___  _ __  ___
//   | |_) | '__| \ \ / / _` | __/ _ \ | |_ | | | | '_ \ / __| __| |/ _ \| '_ \/ __|
//   |  __/| |  | |\ V / (_| | ||  __/ |  _|| |_| | | | | (__| |_| | (_) | | | \__ \
//   |_|   |_|  |_| \_/ \__,_|\__\___| |_|   \__,_|_| |_|\___|\__|_|\___/|_| |_|___/

    /** reads in the Asset File */
    private fun readAssetFile(): AssetFile {
        return try {
            if(!file.exists()) {
                logger.info { "AssetFile not found, trying to create Default File" }
                writeDefaultAssetFile()
            }
            val reader = BufferedReader(FileReader(file))
            val json = reader.readText()
            val asset = Gson().fromJson(json, AssetFile::class.java)
            reader.close()
            asset!!
        }catch (e:Exception){
            errorExit(e) { "Failed to Read AssetFile, something is wrong with it" }
            readAssetFile()
        }
    }

    /** Creates a new Asset File */
    private fun writeDefaultAssetFile(){
        val f = AssetFile()
        f.addSong()
        f.changeSong()
        f.save()
    }

    fun errorExit(e:Exception? = null, msg: () -> Any){
        println(msg.invoke())
        if(e != null){
            logger.info { "See Error Log below" }
            logger.error { e.message }
        }
        println("\nPress Enter to Exit")
        readLine()
        exitProcess(1)
    }
}
