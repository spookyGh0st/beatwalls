package interpreter.parser

import structure.Structure

/**
 * An Interface is just a function that does something with a Structure
 * Currently it holds some properties, that it can applly to the Structure
 * The correct type get's checked at Runtime
 */
fun Parser.parseInterface(){
    val p = currentBlock.properties
    val i = { s: Structure ->
        p.forEach {
            currentTP = it
            parseStructureProperty(s) }
    }
    interfaces[currentBlock.name.toLowerCase()] = i
}
