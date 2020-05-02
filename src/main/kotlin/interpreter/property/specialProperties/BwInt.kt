package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import structure.WallStructure
import kotlin.math.roundToInt
import kotlin.reflect.KProperty


class BwInt(private var es: String = "0.0"): BwProperty() {
    constructor(e: Int): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int {
        return calcExpression(es,thisRef).roundToInt()
    }

    override fun setExpr(e: String) {
        es = e
    }

    override fun plusExpr(e: String) {
        es = strPlusExprStr(es, e)
    }

    override fun timesExpr(e: String) {
        es = strTimesExprStr(es, e)
    }

    override fun powExpr(e: String) {
        es = strPowExprStr(es, e)
    }

    override fun toString(): String = es
}

class BwIntOrNull(private var es: String = "null"): BwProperty() {
    constructor(e: Int?): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int? {
        if (strExpressesNull(es)) return null
        return calcExpression(es,thisRef).roundToInt()
    }

    override fun setExpr(e: String) {
        es = e
    }

    override fun plusExpr(e: String) {
        es = strPlusExprStr(es, e)
    }

    override fun timesExpr(e: String) {
        es = strTimesExprStr(es, e)
    }

    override fun powExpr(e: String) {
        es = strPowExprStr(es, e)
    }

    override fun toString(): String = es
}