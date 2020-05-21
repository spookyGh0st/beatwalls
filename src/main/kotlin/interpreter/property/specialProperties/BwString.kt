package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.InvalidExpressionException
import net.objecthunter.exp4j.Expression
import structure.WallStructure
import kotlin.reflect.KProperty

class BwString(private var exprString: String) : BwProperty() {
    // simply returns the expression string
    override fun getValue(thisRef: WallStructure, property: KProperty<*>): String = exprString

    override fun setExpr(e: String) {
        exprString = e
    }

    override fun plusExpr(e: String) {
        exprString += e
    }

    override fun timesExpr(e: String) {
        throw TODO()
        //InvalidExpressionException(
         //   Expression(e),
          //  "how the fuck should this work"
        //)
    }

    override fun powExpr(e: String) {
        TODO()
        //throw InvalidExpressionException(
         //   Expression(e),
          //  "how the fuck should this work"
        //)
    }
}