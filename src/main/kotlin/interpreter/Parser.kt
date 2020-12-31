package interpreter

import java.lang.IllegalArgumentException
import kotlin.Exception

class Parser(val blocks: List<TokenBlock>, val bw: Beatwalls) {
    val includeList = mutableListOf<String>()
    fun parse(){
        TODO()
    }

    fun parseOptions(){
        val optionBlock = blocks[0]
        if (optionBlock.type != BlockType.Options){
            bw.error(optionBlock.file, optionBlock.line, "Missing Options")
            return
        }
        optionBlock.properties.forEach {
            try {
                parseOptionProperty(it)
            } catch (e:Exception){
                bw.error(it.file, it.line, "Could not parse Option Parameter")
            }
        }
    }

    fun parseOptionProperty(tp: TokenPair){
        when(tp.k.toLowerCase()) {
            "characteristic" -> bw.options.Characteristic = tp.v
            "clearwalls" -> bw.options.clearWalls = tp.v.toLowerCase().toBoolean()
            "clearnotes" -> bw.options.clearNotes = tp.v.toLowerCase().toBoolean()
            "clearbombs" -> bw.options.clearWalls = tp.v.toLowerCase().toBoolean()
            "halfjumpduration" -> bw.options.halfJumpDuration = tp.v.toLowerCase().toDouble()
            "modtype" -> try {
                bw.options.modType = Options.ModType.valueOf(tp.v)
            } catch (e: IllegalArgumentException) {
                bw.error(tp.file, tp.line, "${tp.k} is not a supported Value. (only ${Options.ModType.values()}")
            }
            "difficulty" -> try {
                bw.options.difficulty = Options.DifficultyType.valueOf(tp.v)
            } catch (e: IllegalArgumentException) {
                bw.error(tp.file, tp.line, "${tp.k} is not a supported Value. (only ${Options.DifficultyType.values()})")
            }
        }
    }
}
