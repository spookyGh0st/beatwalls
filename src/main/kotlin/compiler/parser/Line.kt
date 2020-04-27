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
data class Line(val s: String, val line: Int = 0, val file: File = File("")){
    fun toLowercase() = copy(s=s.toLowerCase())
    fun replaceWithIncludes() = try {
        if(s.sBefore(":")=="include") parseInclude() else listOf(this)
    }catch (e: Exception){
        throw InvalidLineExpression(this,"Failed to import file, check if this file exist in your song folder")
    }

    fun parseInclude():List<Line>{
        val pf = GlobalConfig.bwFile.parentFile
        val fn = sAfter(":")
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
    this.trim().substringBefore(delimiter,missingDelimiterValue).trim()
fun String.sAfter(delimiter: String,missingDelimiterValue: String = "")=
    this.trim().substringAfter(delimiter,missingDelimiterValue).trim()
fun String.sBetween(startDelimiter: String,endDelimiter: String,missingDelimiterValue: String = "")=
    this.trim().substringBetween(startDelimiter,endDelimiter,missingDelimiterValue).trim()

fun String.substringBetween(start: String = "", end: String = "",missingDelimiterStartValue: String= "", missingDelimiterEndValue: String? = null): String {
    val saft = this.substringAfter(start,missingDelimiterStartValue)
    return saft.substringBefore(end,missingDelimiterEndValue?: saft )
}
