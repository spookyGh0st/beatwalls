package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import structure.WallStructure
import structure.helperClasses.Point
import kotlin.reflect.KProperty

class BwPoint(var x: String = "0.0", var y: String="0.0",var z: String="0.0"): BwProperty() {
    constructor(x:Number,y:Number,z:Number): this(x.toString(),y.toString(),z.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Point {
        // throws an exception if one Expression is not valid
        return Point(
            calcExpression(x,thisRef),
            calcExpression(y,thisRef),
            calcExpression(z,thisRef)
        )
    }

    override fun setExpr(e: String) {
        // splits the 3 values separated by commas
        val el = e.split(Regex(",(?![^(]*\\))"))
        x = el.getOrNull(0)?:x
        y = el.getOrNull(1)?:y
        z = el.getOrNull(2)?:z
    }

    fun parseExpression(e: String,f: (String,String)->String) {
        val el = e.split(Regex(",(?![^(]*\\))"))
        when(el.size){
            1 -> {
                x = f(x,el[0])
                y = f(y, el[0])
                z = f(z, el[0])
            }
            3 -> {
                x = f(x,el[0])
                y = f(y, el[1])
                z = f(z, el[2])
            }
            else -> throw Exception("Expression $e is invalid, should have one or three parameter")
        }
    }


    override fun plusExpr(e: String) {
        parseExpression(e) { s: String, s2: String -> strPlusExprStr(s,s2) }
    }
    override fun timesExpr(e: String) {
        parseExpression(e) { s: String, s2: String -> strTimesExprStr(s,s2) }
    }

    override fun powExpr(e: String) {
        parseExpression(e) { s: String, s2: String -> strPowExprStr(s,s2) }
    }

    override fun toString(): String = "$x,$y,$z"
}