package assetFile

import com.github.spookyghost.beatwalls.errorExit
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import mu.KotlinLogging
import structure.TestWallStructure
import structure.Wall
import structure.WallStructure
import java.io.File
import java.lang.reflect.Type
private val logger = KotlinLogging.logger {}

class AssetFile(
    @SerializedName("Version")
    val version: Double = 1.0,
    @SerializedName("last Song")
    var currentSong:String = "",
    @SerializedName("Saved Structures")
    val savedStructures: ArrayList<SavedWallStructure> = arrayListOf()
)

private fun readAssetFile(): AssetFile {
    //val fil = File("A:\\Files\\projects\\beatwalls\\target\\BeatWallAsset.json")
   //return serializeAsset(fil.readText())

    val f = File(System.getProperty("java.class.path"))
    val dir = f.absoluteFile.parentFile
    val file = File(dir,"BeatWallAsset.json")
    if(!file.exists()){
        val s1 = SavedWallStructure("Floor", listOf(Wall(-1.0,1.0,2.0,0.0,0.0,0.0)))
        val s2 = SavedWallStructure("Ceiling", listOf(Wall(-1.0,1.0,2.0,0.0,4.0,0.0)))

        val default = AssetFile(1.0,"", arrayListOf(s1,s2))
        val json = GsonBuilder().setPrettyPrinting().create().toJson(default)
        file.writeText(json)
        errorExit { "No Asset File detected, created example one" }
    }
    val json =file.readText()
    logger.info { "Read Asset File" }
    return serializeAsset(json)
}

//todo make private -> DifficultyAsset add Structure Type -> add Define Structrue -> add lists
private fun writeAssetFile(a:AssetFile){
    val f = File(System.getProperty("java.class.path"))
    val dir = f.absoluteFile.parentFile
    val file = File(dir,"BeatWallAsset.json")
    val json =  deserializeAsset(a)
    file.writeText(json)
    logger.info { "saved AssetFile to ${file.path}" }
}

//    ____             _
//   / __ )____  _____(_)___  ____ _
//  / __  / __ \/ ___/ / __ \/ __ `/
// / /_/ / /_/ / /  / / / / / /_/ /
///_____/\____/_/  /_/_/ /_/\__, /
//                         /____/

fun serializeAsset(s: String): AssetFile {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(WallStructure::class.java, InterfaceAdapter<WallStructure>())
        .create()
    return gson.fromJson(s, AssetFile::class.java)
}

fun deserializeAsset(s : AssetFile) : String{
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(WallStructure::class.java, InterfaceAdapter<WallStructure>())
        .create()
    return gson.toJson(s, AssetFile::class.java)

}

internal class InterfaceAdapter<T : Any> : JsonSerializer<T>, JsonDeserializer<T> {
    override fun serialize(`object`: T, interfaceType: Type, context: JsonSerializationContext): JsonElement {
        val wrapper = JsonObject()
        wrapper.addProperty("type", `object`.javaClass.name)
        wrapper.add("data", context.serialize(`object`))
        return wrapper
    }

    @Throws(JsonParseException::class)
    override fun deserialize(elem: JsonElement, interfaceType: Type, context: JsonDeserializationContext): T {
        val wrapper = elem as JsonObject
        val typeName = get(wrapper, "type")
        val data = get(wrapper, "data")
        val actualType = typeForName(typeName)
        return context.deserialize(data, actualType)
    }

    private fun typeForName(typeElem: JsonElement): Type {
        try {
            return Class.forName(typeElem.asString)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e)
        }

    }

    private operator fun get(wrapper: JsonObject, memberName: String): JsonElement {
        return wrapper.get(memberName)
            ?: throw JsonParseException("no '$memberName' member found in what was expected to be an interface wrapper")
    }
}
object AssetFileAPI {
    var assetFile:AssetFile? = null
    fun assetFile() = assetFile?:readAssetFile()
    fun writeAssetFile() = writeAssetFile(assetFile?:readAssetFile())
}

fun main(){
    val a = AssetFile()
    val savedStructures = SavedWallStructure("test", structureList = listOf(TestWallStructure(true)))
    a.savedStructures.add(savedStructures)


    val json = deserializeAsset(a)
    println(json)
    val b = serializeAsset(json)
    println(b.savedStructures)
}



