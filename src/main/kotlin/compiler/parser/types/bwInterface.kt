package compiler.parser.types

import compiler.parser.*
import compiler.property.BwProperty


fun String.toBwInterfaceOrNull(possibleInterfaces: HashMap<String, BwInterface>): BwInterface? {
    val name = this.trim().toLowerCase()
    return possibleInterfaces.getOrElse(name) { null }
}

fun String.toBwInterface(possibleInterfaces: HashMap<String, BwInterface>) =
    toBwInterfaceOrNull(possibleInterfaces)?:throw  InvalidBwInterfaceException(
        this
    )

fun String.toInterfaceList(possibleInterfaces: HashMap<String, BwInterface>): List<BwInterface> {
    val interfaceNames = this.trim().split(",").filterNot { it.isBlank() }
    return interfaceNames.map { it.toBwInterface(possibleInterfaces) }
}
fun Line.parseBwInterface(possibleInterfaces: HashMap<String, BwInterface>): Pair<String, BwInterface> {
    val name = sBetween("interface",":").ifEmpty {  throw InvalidLineExpression(
        this
    )
    }
    val superInterfaces = s.substringAfter(":","").toInterfaceList(possibleInterfaces)
    val superProperties = superInterfaces.flatMap { it.operations }.toMutableList()
    return name to BwInterface(superProperties)
}


fun isBwInterface(l: Line) = l.s.startsWith("interface ")

class InvalidBwInterfaceException(name: String, cause: Throwable? = null): Exception("Interface `$name` does not exist",cause)

