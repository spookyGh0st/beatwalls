package interpreter.parser

import structure.Structure
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability

fun Parser.parseStructureProperty(ws: Structure){
    val p: KProperty1<out Structure, *>? = property(ws, currentTP.k)
    if (p == null){ errorTP("Property ${currentTP.k} does not exist"); return }

    if (p.returnType.isMarkedNullable && currentTP.v == "null"){
        writeProb(ws,p,null)
        return
    }

    val type = p.returnType.withNullability(false)
    val tb = TypeBuilder(currentTP.v,ws.structureState,colors)
    val value = tb.build(type)

    if (value == null) {
        errorTP("Failed to parse Property of ${currentTP.v} into a correct typ.")
        return
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
private fun property (struct: Structure, name: String): KProperty1<out Structure, *>? {
    val props = struct::class.memberProperties
    return props.find { it.name.equals(name, ignoreCase = true) }
}