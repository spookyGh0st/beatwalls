package interpreter

import java.io.File

class Scanner(val source: String, val bw: Beatwalls, val file: File) {
    private val tokenBlocks = mutableListOf(TokenBlock(BlockType.Options,"global",file, 0))
    private var currentLine = 1
    private val lines = source.lines()

    // mathces all comments
    private val commentRegex = Regex("(#.*)")
    // Clears multiple whitespaces
    private val spaceRegex = Regex("(\\s+)")

    fun scan(): MutableList<TokenBlock> {
        lines.forEach { scanLine(it) }
        return tokenBlocks
    }

    private fun scanLine(line: String){
        val words = splitLine(line)

        when (words.size){
            0 -> Unit
            1 -> scanTokenBlock(words[0])
            2 -> scanTokenPair(words[0], words[1])
            else -> bw.error(file, currentLine, "Unexpected Character")
        }
        currentLine++
    }

    private fun splitLine(line: String): List<String> {
        val sanitizedLine =  line
            .replace(commentRegex,"")
            .replace(spaceRegex," ")
        val words = sanitizedLine.split(":").filter { it.isNotBlank() }
        return words.map { it.trim() }
    }


    private fun scanTokenBlock(k: String){
        val w = k.toLowerCase().trim().split(" ").filter { it.isNotBlank() }
        val type: BlockType
        val name: String

        when(w.size){
            1 -> when (w[0]){
                "default" -> {
                    type = BlockType.Interface
                    name = w[0]
                }
                else -> {
                    type = BlockType.Structure
                    name = w[0]
                }
            }
            2 -> when(w[0]){
                "define" -> {
                    type = BlockType.CustomStructure
                    name = w[1]
                }
                "interface" -> {
                    type = BlockType.Interface
                    name = w[1]
                }
                "color" -> {
                    type = BlockType.Color
                    name = w[1]
                }
                "var" -> {
                    type = BlockType.Variable
                    name = w[1]
                }
                else -> {
                    bw.error(file, currentLine, "Unexpected Identifiere")
                    return
                }
            }
            else -> {
                bw.error(file, currentLine, "Unexpected char")
                return
            }
        }

        val tb = TokenBlock(type, name,file ,currentLine)
        tokenBlocks.add(tb)
    }

    private fun scanTokenPair(k: String, v: String){
        val tp = TokenPair(k.trim(), v.trim(), file, currentLine)
        if (tp.k.toLowerCase() == "include"){
            includeFile(tp.v)
        }else{
            tokenBlocks.last().properties.add(tp)
        }
    }

    private fun includeFile(fileName: String){
        val f = File(bw.options.workingDir,fileName)
        try {
            val s = Scanner(f.readText(), bw, f)
            tokenBlocks.addAll(s.scan().filterNot { it.type == BlockType.Options })
        }catch (e:Error){
            bw.error(file, currentLine, "Could not include File. Have you made a type?")
        }
    }
}

