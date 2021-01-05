package interpreter.parser

import structure.*
import structure.lightStructure.CustomLightStructure
import structure.lightStructure.LightStructure
import structure.noteStructure.CustomNoteStructure
import structure.noteStructure.NoteStructure
import structure.wallStructures.CustomWallStructure
import structure.wallStructures.WallStructure

/**
 * A Custom Structure is a structure designed to hold other structures
 * This way you can play building blocks with them to build your own stuff
 */
@Suppress("UNCHECKED_CAST")
fun Parser.parseCustomStructure(){
    if (currentBlock.properties.isEmpty() || currentBlock.properties[0].k.toLowerCase() != "structures") {
        errorBlock("The first property of a define Blocks must be the structures it inherents from")
        return
    }

    currentTP = currentBlock.properties[0]
    val structures = currentTP.v.toLowerCase().replace(" ","").split(',').filter { it.isNotBlank() }
    val facts: MutableList<() -> Structure> = mutableListOf()

    for (s in structures){
        val fac = structFactories[s]
        if (fac == null) errorTP("Structure $s does not exist")
        else facts.add(fac)
    }

    if (facts.isEmpty()){
        errorBlock("The define block must inherent from at least one other Wallstructure")
        return
    }

    val strcts = facts.subList(1, facts.size).map { it() }
    val name = currentBlock.name.toLowerCase()

    val properties= currentBlock.properties.subList(1,currentBlock.properties.size)
    val f = {
        val inh = facts[0]()
        val ws = when {
            inh is WallStructure && strcts.all { it is WallStructure }  ->
                CustomWallStructure(name,inh, strcts as List<WallStructure>) // this is not unsave you bodo compiler
            inh is LightStructure && strcts.all { it is LightStructure }  ->
                CustomLightStructure(name,inh, strcts as List<LightStructure>) // this is not unsave you bodo compiler
            inh is NoteStructure && strcts.all { it is NoteStructure }  ->
                CustomNoteStructure(name,inh, strcts as List<NoteStructure>) // this is not unsave you bodo compiler
            else -> CustomStructure(name, inh, strcts)
        }
        parseStructureProperties(ws, properties)
        ws
    }
    structFactories[name] = f
}
