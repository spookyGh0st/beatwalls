package interpreter

import java.io.File

class Beatwalls {
    var hadError = false

    fun run(f: File){
        val s = Scanner(f.readText(), this, f)
        val blocks = s.scan()
    }

    fun error(line: Int, message: String) {
        report(line, "", message)
    }

    fun report(line: Int, where: String, message: String) {
        println("[line $line] Error $where: $message")
        hadError = true
    }
}
