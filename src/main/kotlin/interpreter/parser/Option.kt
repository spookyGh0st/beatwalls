package interpreter.parser

import interpreter.Options

fun Parser.parseOption(){
    currentBlock.properties.forEach {
        currentTP = it
        try {
            parseOptionProperty()
        } catch (e:Exception){
            bw.error(it.file, it.line, "Could not parse Option Parameter")
        }
    }
}

fun Parser.parseOptionProperty(){
    when(currentTP.k.toLowerCase().trim()) {
        "characteristic" -> bw.options.Characteristic = currentTP.v
        "clearwalls" -> bw.options.clearWalls = currentTP.v.toLowerCase().toBoolean()
        "clearnotes" -> bw.options.clearNotes = currentTP.v.toLowerCase().toBoolean()
        "clearbombs" -> bw.options.clearWalls = currentTP.v.toLowerCase().toBoolean()
        "halfjumpduration" -> {
            val hjd = currentTP.v.toLowerCase().toDoubleOrNull()
            if (hjd != null) bw.options.halfJumpDuration = hjd
            else errorTP("HalfJumpDuration must be a number, not ${currentTP.v}")
        }
        "modtype" -> try {
            bw.options.modType = Options.ModType.valueOf(currentTP.v)
        } catch (e: IllegalArgumentException) {
            bw.error(currentTP.file, currentTP.line, "${currentTP.v} is not a supported Value. (only ME/NE)")
        }
        "difficulty" -> try {
            bw.options.difficulty = Options.DifficultyType.valueOf(currentTP.v)
        } catch (e: IllegalArgumentException) {
            bw.error(currentTP.file, currentTP.line, "${currentTP.v} is not a supported Value. (only Easy, Normal, ....)")
        }
    }
}
