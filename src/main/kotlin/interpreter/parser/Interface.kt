package interpreter.parser

import structure.Structure
import structure.baseStructures

/**
 * An Interface is just a function that does something with a Structure
 * Currently it holds some properties, that it can applly to the Structure
 * The correct type get's checked at Runtime
 */
fun Parser.parseInterface(){
    val p = currentBlock.properties
    val i = { s: Structure ->
        parseStructureProperties(s,p)
    }
    interfaces[currentBlock.name.toLowerCase()] = i
}

fun Parser.baseInterfaces(): MutableMap<String, BwInterface>{
    val m = mutableMapOf<String, BwInterface>()
    m["default"] = {}
    for (name in baseStructures.keys){
        m[name.toLowerCase()] = {}
    }
    return m
}
