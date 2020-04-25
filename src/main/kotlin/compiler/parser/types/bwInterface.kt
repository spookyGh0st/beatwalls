package compiler.parser.types

import compiler.parser.*


fun String.toOperatorHolderList(picker: (name: String) -> OperationsHolder): List<OperationsHolder> {
    val names = this.toLowerCase().trim().split(",").filterNot { it.isBlank() }
    return names.map { picker(it.trim()) }
}

fun String.toInterfaceList(bwInterface: (name: String) -> BwInterface): List<BwInterface> =
    toOperatorHolderList(bwInterface).filterIsInstance<BwInterface>()



