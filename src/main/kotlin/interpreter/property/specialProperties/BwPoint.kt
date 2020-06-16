package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import structure.WallStructure
import structure.helperClasses.Point
import javax.swing.text.StyledEditorKit
import kotlin.reflect.KProperty

open class BwPoint(var x: String = "0.0", var y: String="0.0",var z: String="0.0"): BwProperty() {
    constructor(x:Number,y:Number,z:Number): this(x.toString(),y.toString(),z.toString())
    val xExpr by lazy { buildExpression(x) }
    val yExpr by lazy { buildExpression(y) }
    val zExpr by lazy { buildExpression(z) }

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Point {
        // throws an exception if one Expression is not valid
        wsRef = thisRef // this is needed so we pass the right ws in the functions
        return Point(
            setVarsAndCalcExprForWs(xExpr,thisRef),
            setVarsAndCalcExprForWs(yExpr,thisRef),
            setVarsAndCalcExprForWs(zExpr,thisRef)
        )
    }

    override fun setExpr(e: String) {
        // splits the 3 values separated by commas
        val el = e.split(Regex(",(?![^(]*\\))"))
        x = el.getOrNull(0)?:x
        y = el.getOrNull(1)?:y
        z = el.getOrNull(2)?:z
    }

    open fun parseExpression(e: String,f: (String,String)->String) {
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

}

class BwPointOrNull(var n:Boolean = true, var x: String = "0.0", var y: String="0.0",var z: String="0.0"): BwProperty() {
    val xExpr by lazy { buildExpression(x) }
    val yExpr by lazy { buildExpression(y) }
    val zExpr by lazy { buildExpression(z) }
    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Point? {
        return if (n) null
        else{
            // throws an exception if one Expression is not valid
            wsRef = thisRef // this is needed so we pass the right ws in the functions
            return Point(
                setVarsAndCalcExprForWs(xExpr,thisRef),
                setVarsAndCalcExprForWs(yExpr,thisRef),
                setVarsAndCalcExprForWs(zExpr,thisRef)
            )

        }
    }

    override fun setExpr(e: String) {
        if (e.toLowerCase().trim() != "null"){
            n = false
            // splits the 3 values separated by commas
            val el = e.split(Regex(",(?![^(]*\\))"))
            x = el.getOrNull(0)?:x
            y = el.getOrNull(1)?:y
            z = el.getOrNull(2)?:z
        }
    }

    fun parseExpression(e: String,f: (String,String)->String) {
        if (e.toLowerCase().trim() != "null"){
            n = false
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
}
