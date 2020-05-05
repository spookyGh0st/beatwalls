package interpreter.property.elements

import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.ArgumentExtension
import org.mariuszgromada.math.mxparser.PrimitiveElement
import structure.helperClasses.SpookyWall
import java.lang.Exception
import kotlin.reflect.full.memberProperties


fun wallArguments(w: SpookyWall): HashMap<String, Argument> {
    val hm = hashMapOf<String, Argument>()
    SpookyWall::class.memberProperties.forEach {
        val n = "wall${it.name.toLowerCase()}"
        val v = WallArgument(w) { ws -> it.get(ws).toDoubleOrZero() }
        hm[n] = Argument(n, v)
    }
    return hm
}

fun Any?.toDoubleOrZero() =
    if (this is Number?) this?.toDouble() ?: 0.0
    else 0.0

data class WallArgument(var sw: SpookyWall, val w: (sw: SpookyWall) -> Double) :
    ArgumentExtension {
    override fun clone(): ArgumentExtension {
        return this.clone()
    }

    override fun getArgumentValue(): Double {
        return w(sw)
    }
}