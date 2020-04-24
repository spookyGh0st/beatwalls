package structure

import kotlin.reflect.*
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters


abstract class Ws(val a: Int = 10) {
}

data class MyWs(val P1: Int): Ws(){
}


fun<E:Any> deepCopy(e: E){
    val properties = e::class.memberProperties
    val constructor = e::class.primaryConstructor!!
    constructor.call(properties)
}


fun main(){
    val ws = MyWs(12)
    val ws2 = deepCopy(ws)
    println(ws)
    println(ws2)
    println(ws2)
}