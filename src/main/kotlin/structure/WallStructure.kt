package structure

import com.google.gson.*
import mu.KotlinLogging
import java.lang.reflect.Type


private val logger = KotlinLogging.logger {}



abstract class WallStructure(
    var beat: Double = 0.0,
    val mirror: Boolean = false,
    val walls: ArrayList<Wall> = arrayListOf()
) {
    init {
        beat = adjustBeat()
    }
    open fun run(){}
    private fun adjustBeat()  = beat++
}

class CustomWallStructure: WallStructure()

class Test(beat: Double): WallStructure(beat = beat)

//    ____             _
//   / __ )____  _____(_)___  ____ _
//  / __  / __ \/ ___/ / __ \/ __ `/
// / /_/ / /_/ / /  / / / / / /_/ /
///_____/\____/_/  /_/_/ /_/\__, /
//                         /____/

fun serializeStructure(s: String):WallStructure{
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(WallStructure::class.java, InterfaceAdapter<WallStructure>())
        .create()
    return gson.fromJson(s,WallStructure::class.java)
}
fun deserializeStructure(s : WallStructure) : String{
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(WallStructure::class.java, InterfaceAdapter<WallStructure>())
        .create()
    return gson.toJson(s, WallStructure::class.java)

}

internal class InterfaceAdapter<T : Any> : JsonSerializer<T>, JsonDeserializer<T> {
    override fun serialize(`object`: T, interfaceType: Type, context: JsonSerializationContext): JsonElement {
        val wrapper = JsonObject()
        wrapper.addProperty("type", `object`.javaClass.getName())
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

fun main(){
    val a = CustomWallStructure()
    val b = deserializeStructure(a)
    println(b)
}