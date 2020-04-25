package compiler.parser.types

import compiler.parser.Line
import compiler.parser.OperationsHolder
import compiler.property.BwProperty
import structure.WallStructure

fun parseProperty(l: Line, oh: OperationsHolder, bwPropFactPicker: BwPropFactPicker){
    val a = Assign(l.s)
    val prop = bwPropFactPicker(a.name)
    oh.operations.add{
        val f = a.assignBwProp()
        f(prop(it)) }
}

typealias BwPropFactory = (WallStructure) -> BwProperty

typealias BwPropFactPicker = (name: String)->BwPropFactory
