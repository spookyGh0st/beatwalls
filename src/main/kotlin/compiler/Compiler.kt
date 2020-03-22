package compiler

import compiler.StringParser.parseLine
import compiler.StringParser.parseText
import compiler.types.createType
import org.mariuszgromada.math.mxparser.PrimitiveElement
import structure.Define
import structure.Interface
import structure.WallStructure
import kotlin.reflect.full.createInstance

class Compiler {
    lateinit var lastStructure: WallStructure

    val wallStructures = mutableListOf<WallStructure>()

    val defaultStructures = getDefaultStructures()
    val storedStructures = hashMapOf<String, Define>()
    val storedInterfaces = hashMapOf<String, Interface>()

    val args = hashMapOf<Int, PrimitiveElement>()
    val valList = hashMapOf<String, Int>()
    val funList = hashMapOf<String, Int>()

    fun compile(text: String): MutableList<WallStructure> {
        //format the lines into the correct format
        val lines = parseText(text)

        //check the syntax
        //todo check syntax

        // parse the lines to key/value pairs
        val parsedLine = lines.map { parseLine(it.value) }

        // parse the line and fill the hashmaps
        parsedLine.forEach { createType(it.first, it.second) }

        // returns the wallstructures
        return wallStructures
    }
}

fun getDefaultStructures(): HashMap<String, WallStructure> {
    val l = WallStructure::class.sealedSubclasses
    val hm = hashMapOf<String, WallStructure>()
    l.forEach { hm += Pair(it.simpleName?.toLowerCase() ?: "", it.createInstance()) }
    return hm
}

fun main() {
    val a = getDefaultStructures()
    println(a)
}