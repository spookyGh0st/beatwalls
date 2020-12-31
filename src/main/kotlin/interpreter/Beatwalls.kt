package interpreter

import java.io.File

class Beatwalls {
    var hadError = false
    val options = Options()

    fun run(f: File){
        val s = Scanner(f.readText(), this, f)
        val blocks = s.scan()
        val p = Parser(blocks,this)

    }

    fun error(file: File, line: Int, message: String) {
        report(file, line, "", message)
    }

    fun report(file: File, line: Int, where: String, message: String) {
        println("[${file.name}: line $line] Error $where: $message")
        hadError = true
    }
}
