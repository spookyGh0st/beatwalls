package interpreter.parser

import interpreter.TokenPair
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import structure.CustomStructInterface
import structure.Structure

/**
 * To generate the Structure we just have to find it and build it.
 * After generating and parsing it, we apply all interfaces plus the default Interface on it.
 */
fun Parser.parseStructure(){
    val func = structFactories[currentBlock.name]
    if (func == null) { errorBlock("Structure ${currentBlock.name} does not exist"); return }
    val struct = func()
    for (i in struct.interfaces + "default" + currentBlock.name){
        interfaces[i]?.invoke(struct)?: errorBlock("Interface $i does not exist")
    }
    parseStructureProperties(struct, currentBlock.properties)
    structures.add(struct)
}

fun Parser.parseStructureProperties(struct: Structure, properties: List<TokenPair>){
    struct.structureState.variables += variables
    // If we directly inherent from a basestructure, pass the Property below
    // Otherwise, you could not access count or other variables in it.
    if (struct is CustomStructInterface && struct.superStructure !is CustomStructInterface)
        return parseStructureProperties(struct.superStructure, properties)
    runBlocking {
        for (tp in properties) {
            //todo make this save
            launch { parseStructureProperty(struct, tp) }
        }
    }
}

