package compiler.parser.types

import compiler.parser.InvalidLineExpression
import compiler.parser.Line
import compiler.parser.OperationsHolder
import compiler.property.BwProperty
import structure.Define
import structure.WallStructure
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun parseProperty(l: Line, oh: OperationsHolder){
    val a = Assign(l.s)
    oh.operations.add{
        val bwProp = it.delOfPropName(a.name)?: throw InvalidLineExpression(l,"Property ${a.name} does not exist")
        val f = a.assignBwProp()
        f(bwProp)
    }
}

// wrapper of the propOfName functions
// has the define-logic of WallStructures
// returns null if no BwProperty exists for this name
fun WallStructure.delOfPropName(name: String): BwProperty? {
    val p = this.propOfName(name)
    return if (this is Define){
        if (p.second != null && p.second is BwProperty)
            p.second as BwProperty
        else
            structures.firstOrNull()?.delOfPropName(name)
    }else{
        if (p.second != null && p.second is BwProperty)
            p.second as BwProperty
        else
            null
    }
}

// gets the property of a WallStructure of a given tame and its delegate
fun WallStructure.propOfName(name: String): Pair<KProperty1<out WallStructure, Any?>?, Any?> {
    val props = this::class.memberProperties
    val p = props.find { it.name.toLowerCase() == name.toLowerCase() }
    p?.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    p as KProperty1<WallStructure,Any>?
    return p to p?.getDelegate(this)
}

typealias BwPropFactory = (WallStructure) -> BwProperty

typealias BwPropFactPicker = (name: String)->BwPropFactory
