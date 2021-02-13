package interpreter

import beatwalls.logError
import beatwalls.logWarning
import de.javagl.jgltf.model.GltfModel
import de.javagl.jgltf.model.io.GltfModelReader
import structure.bwElements.BwObstacle
import math.*
import java.io.File
import kotlin.math.PI

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
                scale = n.scale?.toVec3()?: Vec3(1.0,1.0,1.0),
                translation = n.translation?.toVec3()?: Vec3(),
                rotation = n.rotation.toQuat()
            )
            bwObstacles.add(o)
        }
        return bwObstacles.toList()
    }

    private fun FloatArray.toVec3(): Vec3 =
        Vec3(getEl(0),getEl(1),getEl(2))

    private fun FloatArray.toQuat(): Quaternion =
        Quaternion(getEl(0),getEl(1),getEl(2),getEl(3))

    private fun FloatArray.getEl(index: Int): Double =
        elementAtOrElse(index) { logWarning("$this does not have enough values, using 0.0");0.0F }.toDouble()
}


fun main(){
    val f = File("C:\\Users\\user\\projects\\beatsaber\\beatwalls\\src\\main\\resources\\map\\assets")
    val l = ModelLoader(f).loadBwObst("cubeRotY.gltf")
    val bwEuler = Vec3(0.0, 0.25*PI)
    val bwRotation = Quaternion(bwEuler)
    val glRotation = l!![0].rotation
    val glEuler = glRotation.toEuler()
    println("------------------------------------------------")
    println("GL: $glRotation")
    println("BW: $bwRotation")
    println("------------------------------------------------")
    println("GLEuler: $glEuler")
    println("BWEuler: $bwEuler")
}