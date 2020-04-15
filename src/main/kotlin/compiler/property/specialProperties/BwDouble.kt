package compiler.property.specialProperties

import compiler.property.BwProperty
import structure.WallStructure
import kotlin.reflect.KProperty

class BwDouble(private var exprString: String = "0.0"): BwProperty() {
    constructor(e: Double): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double {
        setWsConstants(thisRef)
        return calcExpression(exprString)
    }

    override fun setExpr(e: String) {
        exprString = e
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

    override fun toString(): String = exprString
}

class BwDoubleOrNull(private var exprString: String = "null"): BwProperty() {
    constructor(e: Double?): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Double? {
        if (strExpressesNull(exprString)) return null
        setWsConstants(thisRef)
        return calcExpression(exprString)
    }

    override fun setExpr(e: String) {
        exprString = e
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

    override fun toString(): String = exprString
}
