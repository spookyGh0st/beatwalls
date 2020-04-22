package compiler.parser.types

import compiler.parser.Assigns
import compiler.parser.Interface
import compiler.parser.InvalidLineExpression
import compiler.parser.Line
import compiler.property.BwProperty
import structure.Define
import structure.WallStructure
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun addPropertytoWs(ws: WallStructure, l: Line){
    val props = bwPropertiesOfElement(ws)
    val name = propNameOfLine(l)
    val assign = assignOfLine(l)
    val value = propValueOfLine(l)
        ?: throw InvalidLineExpression(l)
    val prop = props[name]
    when {
        ws is Define && prop== null -> addProperty(ws.structures.first(), l)
        prop == null -> throw  InvalidLineExpression(l)
        else -> assignProperty(prop, assign, value)
    }
}

fun propNameOfLine(l: Line)= l.trimKey(*Assigns.values().map { "${it.identifier}=" }.toTypedArray())

fun propValueOfLine(l: Line)= l.trimValue(*Assigns.values().map { "${it.identifier}=" }.toTypedArray())

fun assignOfLine(l: Line): Assigns {
    val name = propNameOfLine(l)
        ?: throw InvalidLineExpression(l)
    val identifierString =  l.trimKey("=")?.substringAfter(name)?.trim() ?: throw InvalidLineExpression(
        l
    )
    return Assigns.values().find { it.identifier == identifierString }?:
    throw InvalidLineExpression(
        l,
        "Could not parse operator $identifierString\nallowed Operators are ${Assigns.values()
            .map { it.identifier }} "
    )
}

fun assignProperty(prop: BwProperty, assign: Assigns, value: String) = when(assign){
    Assigns.Equals -> prop.setExpr(value)
    Assigns.PlusAssign -> prop.plusExpr(value)
    Assigns.TimesAssign -> prop.timesExpr(value)
    Assigns.PowAssign -> prop.powExpr(value)
}

fun<E: Any> addProperty(ws: E, l: Line) {
    when (ws) {
        is WallStructure -> addPropertytoWs(ws, l)
        is Interface -> ws.lines.add(l)
        else -> throw InvalidLineExpression(l)
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