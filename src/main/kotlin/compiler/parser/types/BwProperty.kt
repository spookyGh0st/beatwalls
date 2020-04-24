package compiler.parser.types

import compiler.property.BwProperty
import structure.WallStructure
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun<E: Any> addProperty(ws: E, f: (WallStructure)-> Unit) {
    when (ws) {
        is WallStructure -> f(ws)
        is BwInterface -> ws.operations.add(TODO())
        else -> throw Exception("tried to set Property to $ws, please report this error")
    }
}


fun <E : Any> bwPropertiesOfElement(element: E): Map<String, BwProperty> {
    val s = memberPropertiesOfElement(element)
    @Suppress("UNCHECKED_CAST") // we can ignore this, since we check for it.
    return s.filterValues { it is BwProperty } as Map<String, BwProperty>
}

fun <E : Any> memberPropertiesOfElement(element: E): Map<String, Any?> {
    val m = element::class.memberProperties.toList()
    m.forEach { it.isAccessible = true }
    @Suppress("UNCHECKED_CAST")
    m as List<KProperty1<E, Any?>>
    return m.associate<KProperty1<E, *>, String, Any?> { it.name to it.getDelegate(element) }
}