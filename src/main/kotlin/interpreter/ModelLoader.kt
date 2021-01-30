package interpreter

import beatwalls.logError
import beatwalls.logWarning
import de.javagl.jgltf.model.GltfModel
import de.javagl.jgltf.model.io.GltfModelReader
import structure.bwElements.BwObstacle
import structure.math.Vec3
import structure.math.Vec4
import java.io.File

const val cubeName = "Cube"
class ModelLoader(val wd: File) {
    fun loadModell(name: String): GltfModel? {
        val f = File(wd,name)
        if (!f.isFile){
            logError("Model under the path $f does not exist. Have you spelled it right?")
            return null
        }
        val mr = GltfModelReader()
        return try {
            val uri = f.toURI()
            mr.read(uri)
        }catch (e: Exception){
            logError("Could not load model $name")
            logError(e.message)
            null
        }
    }
    fun loadBwObst(name: String): List<BwObstacle>? {
        val m = loadModell(name)?: return null
        val nodes = m.nodeModels
        val bwObstacles = arrayListOf<BwObstacle>()
        for (n in nodes){
            if (n.name != cubeName){
                logWarning("Found ${n.name} in Model, Non Cube Elements will look weird.")
            }
            val o = BwObstacle(
                scale = n.scale.toVec3(),
                translation = n.translation.toVec3(),
                // todo localRotation = n.rotation.toVec4()
            )
            bwObstacles.add(o)
        }
        return bwObstacles.toList()
    }

    private fun FloatArray.toVec3(): Vec3 =
        Vec3(getEl(0),getEl(1),getEl(2))

    private fun FloatArray.toVec4(): Vec4 =
        Vec4(getEl(0),getEl(1),getEl(2),getEl(3))

    private fun FloatArray.getEl(index: Int): Float =
        elementAtOrElse(0) { logWarning("$this does not have enough values, using 0.0");0.0F }
}


fun main(){
    val f = File("C:\\Users\\user\\projects\\beatsaber\\beatwalls\\src\\main\\resources\\map\\assets")
    ModelLoader(f).loadBwObst("testcube.glb")
}