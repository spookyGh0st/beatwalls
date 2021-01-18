package interpreter.parser

import structure.bwElements.Color


fun Parser.parseColor(){
    var r = 0.0
    var g = 0.0
    var b = 0.0
    var a = 1.0

    for (tp in currentBlock.properties){
        val errorMsg = { d:Double -> bw.error(tp.file,tp.line, "Only direct Values are allowed. Sorry").let { d }}
        when (tp.k.toLowerCase()){
            "r"     -> r = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "red"   -> r = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "g"     -> g = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "green" -> g = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "b"     -> b = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "blue"  -> b = tp.v.toDoubleOrNull()?: errorMsg(0.0)
            "a"     -> a = tp.v.toDoubleOrNull()?: errorMsg(1.0)
            "alpha" -> a = tp.v.toDoubleOrNull()?: errorMsg(1.0)
        }
    }

    val c = Color(r,g,b,a)
    colors[currentBlock.name.toLowerCase()] = c

}

