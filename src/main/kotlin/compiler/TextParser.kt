package compiler

import beatwalls.GlobalConfig
import java.io.File

/**
 * stores the key, value line and file of a line
 * key and value are just needed and line and file are for better error messages
 */
data class Line(val key: String, val value: String, val identifier: String, val line: Int, val file: File){
    fun toLowercase() = copy(key=key.toLowerCase(),value = value.toLowerCase())
    fun replaceWithIncludes() = if(key.toLowerCase()=="include") includes() else listOf(this)

    private fun includes():List<Line>{
        val pf = GlobalConfig.bwFile.parentFile
        val f=File(pf,value)
        return parseFile(f)
    }

    override fun toString(): String = "Line $line in ${file.absolutePath}: key: $key, value: $value"
}

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
    val list = value.split(":")
    val key = list.getOrNull(0)?: throw NoSuchElementException("missing key in line $index")
    val value = list.getOrNull(1)?: throw NoSuchElementException("missing value in line $index")
    val k = key.trim()
    val v= value.trim()
    return Line(k, v, index, file)
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

