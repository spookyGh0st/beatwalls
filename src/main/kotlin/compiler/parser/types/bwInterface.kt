package compiler.parser.types

import compiler.parser.*

fun String.toBwInterfaceOrNull(possibleInterfaces: HashMap<String, Interface>): Interface? {
    val name = this.trim().toLowerCase()
    return possibleInterfaces.getOrElse(name) { null }
}

fun String.toBwInterface(possibleInterfaces: HashMap<String, Interface>) =
    toBwInterfaceOrNull(possibleInterfaces)?:throw  InvalidBwInterfaceException(
        this
    )

fun String.toInterfaceList(possibleInterfaces: HashMap<String, Interface>): List<Interface> {
    val interfaceNames = this.trim().split(",").filterNot { it.isBlank() }
    return interfaceNames.map { it.toBwInterface(possibleInterfaces) }
}
fun Line.parseBwInterface(possibleInterfaces: HashMap<String, Interface>): Pair<String, Interface> {
    val name = s.substringBetween("interface",":").ifEmpty {  throw InvalidLineExpression(
        this
    )
    }
    val superInterfaces = s.substringAfter(":","").toInterfaceList(possibleInterfaces)
    return name to Interface(superInterfaces)
}

fun LastStructure.addInterfaces(vararg additionalInterfaces: Interface) {
    for(r in interfaces + additionalInterfaces.toList())
        for(l in r.lines){
            addProperty(s, l)
        }
    }

fun isBwInterface(l: Line) = l.s.startsWith("interface ")

class InvalidBwInterfaceException(name: String, cause: Throwable? = null): Exception("Interface `$name` does not exist",cause)

