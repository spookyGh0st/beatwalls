package compiler.property.specialProperties

import compiler.property.BwProperty
import structure.WallStructure
import kotlin.math.roundToInt
import kotlin.reflect.KProperty


class BwInt(private var exprString: String = "0.0"): BwProperty() {
    constructor(e: Int): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int {
        setWsConstants(thisRef)
        return calcExpression(exprString).roundToInt()
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

class BwIntOrNull(private var exprString: String = "null"): BwProperty() {
    constructor(e: Int?): this(e.toString())

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Int? {
        if (strExpressesNull(exprString)) return null
        setWsConstants(thisRef)
        return calcExpression(exprString).roundToInt()
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