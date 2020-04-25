package compiler.parser.types

import compiler.parser.sAfter
import compiler.parser.sBefore
import compiler.property.BwProperty

// holds
data class Assign(val s: String){
    private val eqI = s.indexOf("=")
    val operator = try {
        if(s[eqI-1] in "+*^") s.substring(eqI-1,eqI+1) else "="
    }catch (e:Exception) { "" }
    val name = s.sBefore(operator)
    val value = s.sAfter(operator)
    fun assignBwProp(): (BwProperty) -> Unit = when(operator){
        "=" -> { prop: BwProperty -> prop.setExpr(value)  }
        "+=" ->{ prop: BwProperty ->  prop.plusExpr(value) }
        "*=" ->{ prop: BwProperty ->  prop.timesExpr(value) }
        "^=" ->{ prop: BwProperty ->  prop.powExpr(value) }
        else -> throw Exception()
    }
}

