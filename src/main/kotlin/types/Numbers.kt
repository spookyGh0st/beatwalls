package types

import structure.bwElements.Color


typealias BwDouble = () -> Double
typealias BwInt = () -> Int
typealias BwNumber = () -> Number
typealias BwColor = () -> Color

fun bwDouble(num: Number):BwDouble = { num.toDouble()}
fun bwInt(num: Number):BwInt = { num.toInt()}


