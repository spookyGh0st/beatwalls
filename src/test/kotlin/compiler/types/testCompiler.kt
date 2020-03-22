package compiler.types

import compiler.Compiler
import compiler.default.includeDefaultFunctions
import compiler.default.includeDefaultInterfaces

fun testCompiler(): Compiler {
    val c = Compiler()
    c.includeDefaultInterfaces()
    c.includeDefaultFunctions()
    return c
}

