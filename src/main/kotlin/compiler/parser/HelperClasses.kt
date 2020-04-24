package compiler.parser

import compiler.property.BwProperty
import structure.WallStructure

data class Assign(val s: String){
    private val eqI = s.indexOf("=")
    val operator = if(s[eqI-1] in "+*^") s.substring(eqI-1,eqI) else "="
    val name = s.sBefore(operator)
    val value = s.sAfter(operator)
    val parseProperty: (BwProperty) -> Unit = when(operator){
        "=" -> { prop: BwProperty -> prop.setExpr(value)  }
        "+=" ->{ prop: BwProperty ->  prop.plusExpr(value) }
        "*=" ->{ prop: BwProperty ->  prop.timesExpr(value) }
        "^=" ->{ prop: BwProperty ->  prop.powExpr(value) }
        else -> throw Exception()
    }
}

