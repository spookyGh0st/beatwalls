package interpreter.property.variables

import kotlin.math.PI

val buildInVariables = hashMapOf(
    "pi" to PI,
    "e" to Math.E,
    "false" to 0.0,
    "true" to 1.0
)
val buildInVariablesNames = mutableSetOf(
    "pi" ,
    "e",
    "false",
    "true"
)
