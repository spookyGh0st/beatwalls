package interpreter.parser

import assetFile.toPoint
import structure.CustomStructInterface
import structure.Structure
import structure.helperClasses.Point
import types.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
fun Parser.parseStructureProperty(ws: Structure){
    val p: KProperty1<out Structure, *>? = property(ws, currentTP.k)
    if (p == null && ws is CustomStructInterface) return parseStructureProperty(ws.superStructure)
    if (p == null){ errorTP("Property ${currentTP.k} does not exist"); return }

    if (p.returnType.isMarkedNullable && currentTP.v == "null"){
        writeProb(ws,p,null)
        return
    }

    val ss = ws.structureState
    val value: Any? = when (p.returnType.withNullability(false)) {
        typeOf<Boolean>()       -> currentTP.v.toBoolean()
        typeOf<BwDouble>()      -> bwDouble(currentTP.v,ss)
        typeOf<BwPoint>()       -> bwPoint(currentTP.v, ss)
        typeOf<BwInt>()         -> bwInt(currentTP.v,ss)
        typeOf<Int>()           -> currentTP.v.toIntOrNull()?:0.0
        typeOf<Double>()        -> currentTP.v.toDoubleOrNull()
        typeOf<String>()        -> currentTP.v
        typeOf<List<String>>()  -> currentTP.v.toLowerCase().replace(" ","").split(',')
        typeOf<Point>()         -> currentTP.v.toPoint() // todo remove
        typeOf<BwColor>()       -> bwColor(currentTP.v, ss)
        else -> errorTP("Unknown type: ${p.returnType}")
    }
    writeProb(ws, p, value)
}

// wrapper of the property functions
// has the define-logic of WallStructures
private fun Parser.writeProb(struct: Structure, p: KProperty1<out Structure, *>, value: Any?) {
    when (p) {
        !is KMutableProperty<*> -> errorTP("Property ${p.name} is is not mutable")
        else -> p.setter.call(struct, value)
    }
}

// gets the property of a Structure of a given name
private fun<T: Any> property (struct: T, name: String): KProperty1<out T, *>? {
    val props = struct::class.memberProperties
    return props.find { it.name.equals(name, ignoreCase = true) }?: return null
}