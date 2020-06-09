package interpreter.property.variables

import assetFile.propOfName
import interpreter.parser.types.delOfPropName
import interpreter.property.specialProperties.BwNumber
import interpreter.property.specialProperties.BwPoint
import structure.WallStructure

// returns the calculated expression for a property.
// todo make this faster and possible for all Properties.
fun valueOfProperty(ws: WallStructure, name: String): Double {
    return when (val del = ws.delOfPropName(name)?: ws.delOfPropName(name.getPointString())) {
        is BwNumber -> {
            del.setVarsAndCalcExprForWs(del.expression,ws)
        }
        is BwPoint -> {
            val lastChar = name.last()
            when(lastChar){
                'x' -> del.setVarsAndCalcExprForWs(del.xExpr,ws)
                'y' -> del.setVarsAndCalcExprForWs(del.yExpr,ws)
                'z' -> del.setVarsAndCalcExprForWs(del.zExpr,ws)
                else -> throw Exception("You can only use point.x, point.y or point.z, not $name")
            }
        }
        else -> throw Exception("$name is invalid, Only Numbers are currently supported ")
    }
}
fun String.getPointString() = this.removeSuffix(".x").removeSuffix(".y").removeSuffix(".z")

val pointVariables = mutableSetOf("p1.x","p1.y","p1.z","p2.x","p2.y","p2.z","testpoint.x","testpoint.y","testpoint.z") //todo this is hacky and bad, but whatever
