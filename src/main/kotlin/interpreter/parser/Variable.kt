package interpreter.parser

fun Parser.parseVariable() {
    if (
        currentBlock.properties.size != 1 ||
        currentBlock.properties[0].k.toLowerCase().trim() != "value" ||
        currentBlock.properties[0].v.toDoubleOrNull() == null
    ) {
        errorBlock("The variable Block must consist of one value Property with a Number")
        return
    }
    variables[currentBlock.name] = currentBlock.properties[0].v.toDouble()
}

