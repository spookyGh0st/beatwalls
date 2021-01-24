package interpreter

import beatwalls.logError
import beatwalls.logInfo
import beatwalls.mainFileSuffix
import interpreter.parser.Parser
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import structure.bwElements.BwElement
import java.io.File


class Beatwalls(val workingDirectory: File, val bwFiles: List<File>) {
    var hadError = false
    val options = Options(workingDirectory)

    fun run(){

        hadError = false
        val ml = try { MapLoader(workingDirectory) }catch (e: Exception){ return }

        val elements = mutableListOf<BwElement>()
        for (currentFile in bwFiles){
                logInfo("Adding elements from ${currentFile.name}")
                val l = loadElements(currentFile)?: return
                elements.addAll(l)
        }

        logInfo("Translating ${elements.size} elements to ingame Objects")
        val tr = Translator(elements, this)
        tr.translate()
        val diff = ml.loadDifficulty(options.Characteristic, options.difficulty)?: return

        logInfo("Correcting timing to bmp changes")
        val bpmAdjuster = BpmAdjuster(diff)
        bpmAdjuster.correctElements(tr.obst, tr.notes, tr.events)

        ml.writeDiff(diff,tr.obst,tr.notes, tr.events)
    }

    fun loadElements(file: File): List<BwElement>? {
        val s = Scanner(file.readText(), this, file)
        val blocks = s.scan()

        val p = Parser(blocks, this)
        val structs = p.parse()

        if (hadError){
            logError("Looks like you have some Errors. Come back if you fixed them")
            return null
        }

        val ev = Evaluator(structs, this)
        return ev.evaluate()

    }


    fun error(file: File, line: Int, message: String) {
        report(file, line, "", message)
    }

    fun report(file: File, line: Int, where: String, message: String) {
        logError("[${file.name}: line $line] Error $where: $message")
        hadError = true
    }
}
