package compiler.parser

import structure.WallStructure


typealias operation = (ws: WallStructure)-> Unit
// The
interface OperationsHolder{
    val operations: MutableList<operation>
}

// a WsFactory holds a WallStructure and operations for that WallStructure
data class WsFactory(val ws: ()->WallStructure, override val operations: MutableList<operation> = mutableListOf()):OperationsHolder{
    // create the WallStructe and parse all the properties
    fun create(): WallStructure {
        val struct = ws()
        for(o in operations){
            o(struct)
        }
        return struct
    }
}

// a BwInterface simply holds operations
data class BwInterface(override val operations: MutableList<operation> = mutableListOf()): OperationsHolder





