package interpreter.property.specialProperties

import chart.difficulty.PointDefinition
import interpreter.property.BwProperty
import structure.WallStructure
import kotlin.reflect.KProperty

class BwPointDefinition(
    var data: PointDefinition = listOf()
): BwProperty(){
    override fun getValue(thisRef: WallStructure, property: KProperty<*>): PointDefinition? {
        return data
    }

    override fun setExpr(e: String) {
        val newD = mutableListOf<MutableList<Double>>()
        val el = e.split(Regex(",(?![^(]*\\))"))
        for ((i, it) in el.withIndex()) {
            newD.add(mutableListOf())
            val s = e.split(Regex(",(?![^(]*\\))"))
            s.forEach {
                newD.last().add(it.toDoubleOrNull()?:0.0)
            }
        }
        data = newD.map { it.toMutableList() }
    }

    override fun plusExpr(e: String) {
        TODO("currently not supported") //To change body of created functions use File | Settings | File Templates.
    }

    override fun timesExpr(e: String) {
        TODO("currently not supported") //To change body of created functions use File | Settings | File Templates.
    }

    override fun powExpr(e: String) {
        TODO("currently not supported") //To change body of created functions use File | Settings | File Templates.
    }

}