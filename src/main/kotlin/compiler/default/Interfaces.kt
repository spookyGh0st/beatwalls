package compiler.default

import beatwalls.GlobalConfig
import compiler.Compiler
import structure.Interface

val defaultInterface = Interface()
val hyperInterface = Interface().also { it.changeDuration = { -1.5 * GlobalConfig.hjsDuration } }
val groundedInterface = Interface().also { it.fitStartHeight = { 0.0 } }

