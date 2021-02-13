package interpreter

import beatwalls.logError
import beatwalls.logInfo
import de.javagl.jgltf.model.GltfModel
import de.javagl.jgltf.model.io.GltfModelReader
import java.io.File
import java.nio.file.Files

const val cubeName = "Cube"
const val settingsName = "settings.json"
const val assetDirName = "assets"
const val modelsDirName = "models"
const val fontsDirName = "fonts"
class AssetLoader(wd: File) {
    private val assetDir = File(wd, assetDirName)
    private val modelDir = File(assetDir, modelsDirName)
    private val fontsDir = File(assetDir, fontsDirName)

    private val mr = GltfModelReader()

    fun loadModell(name: String): GltfModel? {
        val f = File(modelDir,name)
        if (!f.isFile){
            logError("Model under the path $f does not exist. Have you spelled it right?")
            return null
        }
        return try {
            val uri = f.toURI()
            mr.read(uri)
        }catch (e: Exception){
            logError("Could not load model $name")
            logError(e.message)
            null
        }
    }

    init {
        if (!assetDir.isDirectory){
            logInfo("Adding default assets")
            try {
                Files.createDirectory(assetDir.toPath())
                Files.createDirectory(modelDir.toPath())
                Files.createDirectory(fontsDir.toPath())
                addBaseModels()
            }catch (e:Exception){
                logError("Something went wrong while writing the default assets")
                logError(e.message)
            }
        }
    }

    private fun addBaseModels(){
        val resources = AssetLoader::class.java.getResource("/$assetDirName/$modelsDirName")
        if (resources == null){ logError("Resources are null, please report this"); return }

        val modelFolder = File(resources.toURI())
        if (!modelFolder.isDirectory){ logError("Could not load base Models, please report this"); return }

        val models = modelFolder.listFiles()
        if (models == null){ logError("No Models "); return}

        for (m in models){
            val target = File(modelDir, m.name)
            m.copyTo(target)
        }
    }
}

