package compiler.property.specialProperties

import compiler.property.BwProperty
import compiler.property.strPlusExprStr
import compiler.property.strPowExprStr
import compiler.property.strTimesExprStr
import structure.WallStructure
import kotlin.reflect.KProperty

class BwDouble(private var es: String = "0.0"): BwProperty() {
    constructor(e: Number): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double {
        return calcExpression(es,thisRef)
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

class BwDoubleOrNull(private var es: String = "null"): BwProperty() {
    constructor(e: Double?): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double? {
        if (strExpressesNull(es)) return null
        return calcExpression(es,thisRef)
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
