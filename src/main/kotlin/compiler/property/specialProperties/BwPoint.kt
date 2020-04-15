package compiler.property.specialProperties

import compiler.property.BwProperty
import structure.WallStructure
import structure.helperClasses.Point
import kotlin.reflect.KProperty

class BwPoint(var x: String = "0.0", var y: String="0.0",var z: String="0.0"): BwProperty() {
    constructor(x:Number,y:Number,z:Number): this(x.toString(),y.toString(),z.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Point {
        // constants of the wallstructures properties
        setWsConstants(thisRef)
        val valueExpressions = calcExpression(x)
        // throws an exception if one Expression is not valid
        return Point(
            calcExpression(x),
            calcExpression(y),
            calcExpression(z)
        )
    }

    override fun setExpr(e: String) {
        // splits the 3 values separated by commas
        val el = e.split(Regex(",(?![^(]*\\))"))
        x = el.getOrNull(0)?:x
        y = el.getOrNull(1)?:y
        z = el.getOrNull(2)?:z
    }

    override fun plusExpr(e: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun timesExpr(e: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun powExpr(e: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String = "$x,$y,$z"
}