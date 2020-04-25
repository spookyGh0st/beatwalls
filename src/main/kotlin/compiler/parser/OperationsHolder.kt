package compiler.parser

import structure.WallStructure

// simply an Function that can be performed on a WallStructure
typealias operation = (ws: WallStructure)-> Unit

// hold operations of some type
// currently only for WallStructures, but could be done generally with generics
sealed class OperationsHolder{
    abstract val operations: MutableList<operation>
}


// a WsFactory holds a WallStructure and operations for that WallStructure
data class WsFactory(val ws: ()->WallStructure, override val operations: MutableList<operation> = mutableListOf()):OperationsHolder(){
    // create the WallStructe and parse all the properties
    fun create(): WallStructure {
        val struct = ws()
        //todo define-logic
        for(o in operations){
            o(struct)
        }
        return struct
    }
}

// a BwInterface simply holds operations
data class BwInterface(override val operations: MutableList<operation> = mutableListOf()): OperationsHolder()
