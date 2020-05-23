package interpreter.property.variables

import assetFile.propOfName
import interpreter.parser.types.delOfPropName
import interpreter.property.specialProperties.BwNumber
import structure.WallStructure

// returns the calculated expression for a property.
// todo make this faster and possible for all Properties.
fun valueOfProperty(ws: WallStructure, name: String): Double {
    val del = ws.delOfPropName(name)
    if (del is BwNumber){
        return del.setVarsAndCalcExprForWs(del.expression,ws)
    }else{
        throw Exception("$name is invalid, Only Numbers are currently supported ")
    }
}