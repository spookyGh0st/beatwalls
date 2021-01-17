package types


typealias BwDouble = () -> Double
typealias BwInt = () -> Int
typealias BwNumber = () -> Number

fun bwDouble(num: Number):BwDouble = { num.toDouble()}
fun bwInt(num: Number):BwInt = { num.toInt()}


