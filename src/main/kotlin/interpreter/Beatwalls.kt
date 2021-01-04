package interpreter

import interpreter.parser.Parser
import java.io.File

class Beatwalls(val workingDirectory: File) {
    var hadError = false
    val options = Options(workingDirectory)
    val mainFile = File(workingDirectory, "main.bw")

    fun run(){
        hadError = false
        val s = Scanner(mainFile.readText(), this, mainFile)
        val blocks = s.scan()
        val p = Parser(blocks,this)
        val structs = p.parse()
    }

    fun error(file: File, line: Int, message: String) {
        report(file, line, "", message)
    }

    fun report(file: File, line: Int, where: String, message: String) {
        println("[${file.name}: line $line] Error $where: $message")
        hadError = true
    }
}
