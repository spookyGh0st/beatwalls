package compiler.parser

import beatwalls.GlobalConfig
import java.io.File

data class Interface(val superInterfaces: List<Interface>) {
    val lines : MutableSet<Line> = superInterfaces.flatMap { it.lines }.toMutableSet()
}

data class LastStructure(val s: Any, val interfaces: List<Interface> = mutableListOf(), val initialize: Boolean = false)

enum class Assigns(val identifier: String){
    Equals(""),
    PlusAssign("+"),
    TimesAssign("*"),
    PowAssign("^")
}

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
        val fn = trimValue(" ")
        val f= File(pf, fn)
        return parseFile(f)
    }

    fun trimKey(delimiter: String) = s.split(delimiter).getOrNull(0)?.trim()
    fun trimKey(vararg delimiters:String) = s.split(*delimiters).getOrNull(0)?.trim()
    fun trimValue(delimiter: String) = s.split(delimiter).getOrNull(1)?.trim()
    fun trimValue(vararg delimiters:String) = s.split(*delimiters).getOrNull(1)?.trim()

    override fun toString(): String = "Line $line in ${file.absolutePath}: $s"
}