package compiler.parser

import java.io.File

/**
 * parses a complete file to Lines
 */
fun parseFile(f: File): List<Line>{
    val text = f.readText()
    val formattedText = parseText(text)
    val lines = formattedText.map { it.toLine(f) }
    val includedLines = lines.flatMap { it.replaceWithIncludes() }
    return includedLines.map { it.toLowercase() }
}

/**
 * parses an Text to Indexed Values with comments removed
 */
fun parseText(s: String): List<IndexedValue<String>> {
    val lines = s.lines().withIndex()
    val trimmedLines = lines.map { it.trim() }
    val parsedLines= trimmedLines.filterNot { it.isComment() }
    return parsedLines.map { it.removeComments("#") }
}

/**
 * returns true, if the String is a comment or empty line (and therefore should be ignored)
 */
fun IndexedValue<String>.isComment() = this.value.startsWith("#") || this.value.isEmpty()

/**
 * trims the value, just syntactic sugar
 */
fun IndexedValue<String>.trim() = this.copy(value=value.trim())

/**
 * parses an Indexed String Value to a Line
 */
fun IndexedValue<String>.toLine(file: File): Line {
    return Line(value, index, file)
}

/**
 * removes the comments from an Indexed String Value
 */
fun IndexedValue<String>.removeComments(identifier: String): IndexedValue<String> {
    val uncommentedValue = value
        .replaceAfter(identifier, "")
        .replace(identifier, "")
    return this.copy(value = uncommentedValue)
}

