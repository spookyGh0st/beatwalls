package interpreter.parser

import interpreter.TokenPair
import structure.Structure

/**
 * To generate the Structure we just have to find it and build it.
 * After generating and parsing it, we apply all interfaces plus the default Interface on it.
 */
fun Parser.parseStructure(){
    val func = structFactories[currentBlock.name]
    if (func == null) { error("Structure ${currentBlock.name} does not exist"); return }
    val ws = func()
    parseStructureProperties(ws, currentBlock.properties)
    structures.add(ws)
}

fun Parser.parseStructureProperties(struct: Structure, properties: List<TokenPair>){
    for (tp in properties){
        currentTP = tp
        parseStructureProperty(struct)
    }
    for (i in struct.interfaces + "default"){
        interfaces[i]?.invoke(struct)?: error("Interface $i does not exist")
    }
}

