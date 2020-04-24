package compiler.parser

import beatwalls.GlobalConfig
import java.io.File

/**
 * looks a bit nicer
 */
class InvalidLineExpression(l: Line, message: String? =null): Exception(){
    override val message: String = "EROOR: ${message?: "Invalid Line"}\n$l"
}

/**
 * stores the key, value line and file of a line
 * key and value are just needed and line and file are for better error messages
 */
data class Line(val s: String, val line: Int, val file: File){
    fun toLowercase() = copy(s=s.toLowerCase())
    fun replaceWithIncludes() = if(s.toLowerCase()=="include") includes() else listOf(this)

    private fun includes():List<Line>{
        val pf = GlobalConfig.bwFile.parentFile
        val fn = sAfter(" ")
        val f= File(pf, fn)
        return parseFileToLines(f)
    }

    override fun toString(): String = "Line $line in ${file.absolutePath}: $s"
}


fun Line.sBefore(delimiter: String, missingDelimiterValue: String = "") =
    s.sBefore(delimiter, missingDelimiterValue)
fun Line.sAfter(delimiter: String, missingDelimiterValue: String = "") =
    s.sAfter(delimiter, missingDelimiterValue)
fun Line.sBetween(startDelimiter: String, endDelimiter: String, missingDelimiterValue: String = "") =
    s.sBetween(startDelimiter,endDelimiter, missingDelimiterValue)

fun String.sBefore(delimiter: String,missingDelimiterValue: String = "")=
    this.substringBefore(delimiter,missingDelimiterValue).trim()
fun String.sAfter(delimiter: String,missingDelimiterValue: String = "")=
    this.substringAfter(delimiter,missingDelimiterValue).trim()
fun String.sBetween(startDelimiter: String,endDelimiter: String,missingDelimiterValue: String = "")=
    this.substringBetween(startDelimiter,endDelimiter,missingDelimiterValue).trim()

fun String.substringBetween(start: String = "", end: String = "",missingDelimiterValue: String = "") =
    this.substringAfter(start,missingDelimiterValue).substringBefore(end,missingDelimiterValue)
