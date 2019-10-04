package assetFile

import com.github.spookyghost.beatwalls.errorExit
import com.google.gson.Gson
import mu.KotlinLogging
import song.Song
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths


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


    /** changes the Song */
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

    fun njsOffset() = asset.currentSong?.njsOffset?:2.0
    fun changeSong(){
        asset.changeSong()
        asset.save()
    }
    fun changeDirectory()  {
        asset.changeDirectory()
        asset.scanDirectory()
        asset.save()
    }

    fun customWallStructures() =
        asset.customWallStructure

    fun mixedWallStructures() =
        asset.mixedWallStructure

    fun getBPM() =
        currentSong().info._beatsPerMinute

    fun save() = asset.save()

//    ____       _            _         _____                 _   _
//   |  _ \ _ __(_)_   ____ _| |_ ___  |  ___|   _ _ __   ___| |_(_) ___  _ __  ___
//   | |_) | '__| \ \ / / _` | __/ _ \ | |_ | | | | '_ \ / __| __| |/ _ \| '_ \/ __|
//   |  __/| |  | |\ V / (_| | ||  __/ |  _|| |_| | | | | (__| |_| | (_) | | | \__ \
//   |_|   |_|  |_| \_/ \__,_|\__\___| |_|   \__,_|_| |_|\___|\__|_|\___/|_| |_|___/

    /** reads in the Asset File */
    private fun readAssetFile(): AssetFile {
        try {
            if(!file.exists()) {
                logger.info { "AssetFile not found, trying to create Default File" }
                writeDefaultAssetFile()
            }
            val reader = BufferedReader(FileReader(file))
            val json = reader.readText()
            val asset = Gson().fromJson(json, AssetFile::class.java)
            reader.close()
            asset.scanDirectory()
            asset.save()
            return asset
        }catch (e:Exception){
            errorExit(e) { "Failed to Read AssetFile, something is wrong with it" }
            return readAssetFile()
        }
    }

    /** Creates a new Asset File */
    private fun writeDefaultAssetFile(){
        val f = AssetFile()
        f.changeDirectory()
        f.scanDirectory()
        f.changeSong()
        f.customWallStructure.addAll(defaultWallStructure())
        f.save()
    }

}

