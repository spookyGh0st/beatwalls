package compiler.StringParser

import compiler.Compiler

/**
 * creates the list of key-value Pairs
 */
internal fun Compiler.parseText(s: String): List<IndexedValue<String>> =
    s
        .lines()
        .withIndex()
        .map { it.copy(value = it.value.trim()) }
        .filterNot { it.value.startsWith("#") || it.value.isEmpty() }
        .map { it.copy(value = it.value.formatLine()) }

internal fun String.formatLine(): String = this
    .replace("\\t".toRegex(), "")
    .replaceAfter("#", "")
    .replace("#", "")

internal fun Compiler.parseLine(s: String) =
    s.toLowerCase()
        .replace(" ", "")
        .split(":")[0] to s.split(":")[1]
