package interpreter

import beatwalls.logInfo
import interpreter.parser.Parser
import java.io.File

class Beatwalls(val workingDirectory: File) {
    var hadError = false
    val options = Options(workingDirectory)
    val mainFile = File(workingDirectory, "main.bw")

    fun run(){
        hadError = false

        val ml = try { MapLoader(workingDirectory) }catch (e:Exception){ return }
        val s = Scanner(mainFile.readText(), this, mainFile)
        val blocks = s.scan()

        val p = Parser(blocks,this)
        val structs = p.parse()

        if (hadError){
            logInfo("Looks like you have some Errors. Come back if you fixed them")
            return
        }

        val ev = Evaluator(structs,this)
        val elements = ev.evaluate()

        val tr = Translator(elements, this)
        tr.translate()
        val diff = ml.loadDifficulty(options.Characteristic, options.difficulty)?: return

        val bpmAdjuster = BpmAdjuster(diff)
        bpmAdjuster.correctElements(tr.obst, tr.notes, tr.events)

        ml.writeDiff(diff,tr.obst,tr.notes, tr.events)
    }


    fun error(file: File, line: Int, message: String) {
        report(file, line, "", message)
    }

    fun report(file: File, line: Int, where: String, message: String) {
        println("[${file.name}: line $line] Error $where: $message")
        hadError = true
    }
}
