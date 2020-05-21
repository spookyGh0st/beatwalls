package interpreter.property.functions

import net.objecthunter.exp4j.function.Function
import structure.WallStructure
import structure.helperClasses.minValue
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class BwRandom0(ws:WallStructure?): Function("random",0){
    val r = ws?.r?:Random(Random.nextInt())
    override fun apply(vararg args: Double): Double {
        return r.nextDouble()
    }
}
class BwRandom1(ws:WallStructure?): Function("random",1){
    val r = ws?.r?:Random(Random.nextInt())
    override fun apply(vararg args: Double): Double {
        return r.nextDouble(args[0])
    }
}
class BwRandom2(ws:WallStructure?): Function("random",2){
    val r = ws?.r?:Random(Random.nextInt())
    override fun apply(vararg args: Double): Double {
        val min= min(args[0],args[1])
        val max= max(args[0],args[1]).coerceAtLeast(min + minValue)
        return r.nextDouble(min,max)
    }
}
