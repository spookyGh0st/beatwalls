package interpreter

import java.io.File

data class TokenBlock (
    val type: BlockType,
    val name: String,
    val file: File,
    val line: Int
){
    val properties = mutableListOf<TokenPair>()
}


enum class BlockType{
    Structure,
    CustomStructure,
    Options,
    Interface,
    Color,
    Variable,
}

data class TokenPair(
    val k: String,
    var v: String,
    val file: File,
    val line: Int
)
