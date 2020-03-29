package compiler.property

import structure.WallStructure
import kotlin.reflect.KProperty

class BwString: BwProperty<String>() {
    // simply returns the expression string
    override fun getValue(thisRef: WallStructure, property: KProperty<*>): String = expressionString
}