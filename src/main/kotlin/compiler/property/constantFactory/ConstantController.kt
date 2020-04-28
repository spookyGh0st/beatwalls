package compiler.property.constantFactory

import compiler.property.BwProperty
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



    val structureConstants: List<Constant>
        get() {
            return getWsConstants(ws)
        }

    val customConstants = mutableListOf<Constant>()

    val easingFunctions by lazy {  getEasingFunctions(this) }

    val customFunctions = mutableListOf<Function>()

    val progressConstant = Constant("progress",progress)

    var wallConstants = listOf<Constant>()
}



fun getWsConstants(ws: WallStructure) =
    getWsProperties(ws)
        .map { it.toConstant(ws) }
        .toList()

@Suppress("UNCHECKED_CAST")
fun getWsProperties(ws: WallStructure) = ws::class.memberProperties
    .asSequence()
    .map { it as KProperty1<WallStructure, Any?> }
    .map { it.also { it.isAccessible=true } }
    .filter { it.getDelegate(ws) is BwProperty }

//this currently does not work with points. TODO
fun KProperty1<WallStructure, Any?>.toConstant(ws: WallStructure): Constant {
    val key = this.name.toLowerCase()
    val value = (this.getDelegate(ws) as BwProperty).toString()
    return Constant("${key}=${value}")
}
