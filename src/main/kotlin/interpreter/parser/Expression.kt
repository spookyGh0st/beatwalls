package interpreter.parser

import beatwalls.logError
import interpreter.TokenPair
import structure.Structure
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability

fun Parser.parseStructureProperty(ws: Structure, tp: TokenPair){
    val p: KProperty1<out Structure, *>? = property(ws, tp.k)
    if (p == null){
        bw.error(tp.file,tp.line,"Property ${tp.k} does not exist. All available Properties:")
        logError(ws::class.memberProperties.map { it.name }.toString())
        return
    }

    if (p.returnType.isMarkedNullable && tp.v == "null"){
        writeProb(ws,p,null)
        return
    }

    val type = p.returnType.withNullability(false)
    val tb = TypeBuilder(tp.v,ws.structureState,colors, structFactories)
    val value = tb.build(type)

    if (value == null) {
        bw.error(tp.file,tp.line, "Failed to parse Property of ${tp.v} into a correct typ.")
        return
    }
    writeProb(ws, p, value)
}

// splits along the ,
fun splitExpression(s: String): List<String> {
    var start = 0
    var pCount = 0
    val l = mutableListOf<String>()

    for ((i, c) in s.withIndex()){
        when(c){
            ',' -> if (0 == pCount) {
                l.add(s.substring(start,i))
                start = i+1
            }
            '(' -> pCount++
            ')' -> pCount--
        }
    }
    l.add(s.substring(start).trim())
    return l.toList()
}


// wrapper of the property functions
// has the define-logic of WallStructures
private fun Parser.writeProb(struct: Structure, p: KProperty1<out Structure, *>, value: Any?) {
    when (p) {
        !is KMutableProperty<*> -> errorTP("Property ${p.name} is is not mutable, please report this, it's most likely a bug")
        else -> p.setter.call(struct, value)
    }
}

// gets the property of a Structure of a given name
private fun property (struct: Structure, name: String): KProperty1<out Structure, *>? {
    val props = struct::class.memberProperties
    return props.find { it.name.equals(name, ignoreCase = true) }
}