package compiler.property

import structure.WallStructure
import kotlin.reflect.KProperty

class BwString(default:String=""): BwProperty<String>(default) {
    // simply returns the expression string
    override fun getValue(thisRef: WallStructure, property: KProperty<*>): String = expressionString
}