package compiler

import beatwalls.errorExit
import compiler.parser.LineParser
import compiler.parser.parseFileToLines
import structure.WallStructure
import java.io.File

class Compiler {

    fun compile(file: File): MutableList<WallStructure> {
        //we need one factory
        val wf = LineParser()
        //format the lines into the correct lines
        val lines = try {
            parseFileToLines(file)
        }catch (e:Exception){ errorExit(e){ "Parsing failed for $file"} }

        // try to parse each line
        lines.forEach {
            try {  wf.parseLine(it) }catch (e:Exception){ errorExit(e) { "Failed to parse $it" } }
        }

        // returns the structlist
        return wf.structList
    }
}
