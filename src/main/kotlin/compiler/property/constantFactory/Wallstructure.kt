package compiler.property.constantFactory

import compiler.property.BwProperty
import org.mariuszgromada.math.mxparser.Constant
import structure.WallStructure
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

// file for helper functions for wall Structures

fun getWsConstants(ws: WallStructure) =
    getWsProperties(ws)
    .map { it.toConstant(ws) }
    .toList()

@Suppress("UNCHECKED_CAST")
fun getWsProperties(ws: WallStructure) = ws::class.memberProperties
    .asSequence()
    .map { it as KProperty1<WallStructure, Any?> }
    .map { it.also { it.isAccessible=true } }
    .filter { it.getDelegate(ws) is BwProperty }

//this currently does not work with points. TODO
fun KProperty1<WallStructure, Any?>.toConstant(ws: WallStructure): Constant {
    val key = this.name.toLowerCase()
    val value = (this.getDelegate(ws) as BwProperty).toString()
    return Constant("${key}=${value}")
}