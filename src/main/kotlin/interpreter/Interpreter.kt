package interpreter

import beatwalls.GlobalConfig
import beatwalls.errorExit
import interpreter.parser.LineParser
import interpreter.parser.parseFileToLines
import structure.WallStructure

class Interpreter {
    fun compile(): List<WallStructure> {
        //we need one factory
        val wf = LineParser()
        //format the lines into the correct lines
        val lines = try {
            parseFileToLines(GlobalConfig.bwFile)
        }catch (e:Exception){ errorExit(e){ "Parsing failed for ${GlobalConfig.bwFile}"} }

        // try to parse each line
        return try {  wf.create(lines) }catch (e:Exception){ errorExit(e) { "FAILED PARSING A LINE" } }

    }
}
