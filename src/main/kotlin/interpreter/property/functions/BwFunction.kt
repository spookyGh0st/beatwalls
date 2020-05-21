package interpreter.property.functions

data class BwFunction(val name: String,val args: List<String> = emptyList())
data class BwDoubleFunction(val name: String,val args: List<Double> = emptyList())

fun List<Double>.getOrZero(index:Int) = getOrElse(index) { 0.0}

internal fun String.toBwFunction(): BwFunction {
    val s = this.replace(" ","").toLowerCase()
    val head = headFromString(s)
    val args = argsFromString(s,head)
    return BwFunction(head,args)
}

internal fun String.toBwDoubleFunction(): BwDoubleFunction {
    val s = this.replace(" ","").toLowerCase()
    val head = headFromString(s)
    val args = argsFromString(s,head).map { it.toDouble() }
    return BwDoubleFunction(head,args)
}

fun headFromString(s: String) = s
    .substringBefore("(")
    .ifEmpty { throw NullPointerException("Function head cant be empty") }

fun argsFromString(s: String, head: String) = s
    .substringAfter(head)
    .removeSurrounding("(", ")")
    .replace("(","")
    .replace(")","")
    .split(",")
    .filter { it.isNotEmpty() }

