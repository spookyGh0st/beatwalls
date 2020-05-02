package interpreter.property.constantFactory

import interpreter.property.BwProperty
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.WallStructure
import structure.helperClasses.SpookyWall
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class ConstantController(private val ws :WallStructure) {

    var progress: Double  = Double.NaN
        set(value) {
            progressConstant.constantValue = progress
            field = value
        }

    var wall: SpookyWall? =null
        set(value) {
            if(value == null)
                field = value
            else{
                wallConstants = value::class.memberProperties.map {
                    val n = "wall${it.name.toLowerCase()}"
                    val s = (it as KProperty1<SpookyWall,Any?>).get(value)
                    val v = s.toString().toDoubleOrNull()?: 0.0
                    Constant(n,v)
                }
            }
        }



    val structureConstants: List<Argument>
        get() {
            return getWsArguments(ws)
        }

    val customConstants = mutableListOf<Constant>()

    val easingFunctions by lazy {  getEasingFunctions(this) }

    //todo add Random functions

    val customFunctions = mutableListOf<Function>()

    val progressConstant = Constant("progress",progress)

    var wallConstants = listOf<Constant>()

}



fun getWsArguments(ws: WallStructure) =
    getWsProperties(ws)
        .map { it.toArguments(ws) }
        .flatten()
        .toList()

@Suppress("UNCHECKED_CAST")
fun getWsProperties(ws: WallStructure) = ws::class.memberProperties
    .asSequence()
    .map { it as KProperty1<WallStructure, Any?> }
    .map { it.also { it.isAccessible=true } }
    .filter { it.getDelegate(ws) is BwProperty }

fun KProperty1<WallStructure, Any?>.toArguments(ws: WallStructure): List<Argument> {
    val key = this.name.toLowerCase()
    val value = (this.getDelegate(ws) as BwProperty)
    return value.toArguments(key)
}
