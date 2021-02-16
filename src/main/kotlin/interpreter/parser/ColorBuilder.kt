package interpreter.parser

import beatwalls.logError
import structure.StructureState
import structure.bwElements.Color
import types.*
import kotlin.math.PI
import kotlin.math.sin

class ColorBuilder(s: String, ss: StructureState, colors: Map<String, Color>, structFactories: Map<String,StructFactory>): TypeBuilder(
    s,
    ss,
    colors,
    structFactories
) {
    fun buildColor(): BwColor? {
        val sanitizedString = s.toLowerCase().replace(" ", "")
        val c = colors[sanitizedString]
        if (c != null) return { c } else return buildColorFunction()
    }

    fun buildColorFunction() : BwColor? {
        val head = s.substringBefore("(")
        val argsList = s
            .substringAfter(head)
            .removeSurrounding("(", ")")
        val args = splitExpression(argsList)

        return when (head){
            "gradient" -> buildGradient(args)
            "rainbow" -> buildRainbow(args)
            "random" -> buildRandom(args)
            "switch" -> buildSwitch(args)
            "between" -> between(args)
            else -> { logError("Color function $head does not exist"); null }
        }
    }

    private fun between(args: List<String>): BwColor? {
        val colorList = colorList(args)?: return null
        if (colorList.size != 2) { logError("Only 2 colors allowed"); return null }
        val first = colorList[0]
        val second = colorList[1]
        val diff = second - first
        return { first + diff * ss.rand.nextDouble() }
    }

    private fun buildSwitch(args: List<String>): BwColor? {
        val colors = colorList(args)?: return null
        var i = 0
        return { colors[(i++)%colors.size] }
    }

    private fun buildRandom(args: List<String>): BwColor?{
        val colors = colorList(args)?: return null
        return { colors.random(ss.rand) }
    }
    private fun buildRainbow(args: List<String>): BwColor? {
        val repString = args.getOrNull(0)?: "1.0"
        val repetitions = TypeBuilder(repString, ss, colors, structFactories).buildBwDouble()?.invoke()
        val alphaString = args.getOrNull(1)?: "255.0"
        val alpha = TypeBuilder(alphaString, ss, colors, structFactories).buildBwDouble()
        if (alpha == null || repetitions == null) return null
        return {
            val r =  255 * sin(ss.progress*2* PI * repetitions + 0.0/3.0 * PI) /2
            val g =  255 * sin(ss.progress*2* PI * repetitions + 2.0/3.0 * PI) /2
            val b =  255 * sin(ss.progress*2* PI * repetitions + 4.0/3.0 * PI) /2
            val a = alpha.invoke()
            Color(r,g,b,a)
        }
    }

    private fun buildGradient(args: List<String>): BwColor? {
        val colorList = colorList(args)
        if (colorList == null ||colorList.size < 2){
            logError("Gradient can only use 2 or more Colors")
            return null
        }
        return {
            val index = (ss.progress * (colorList.size-1)).toInt()
            val progress = (ss.progress*(colorList.size-1)) % 1.0
            val start = colorList[index]
            val end = colorList.getOrElse(index+1){colorList.last()}
            val change = end - start
            val c = start + change * ease(progress)
            c
        }
    }

    fun colorList(args: List<String>): List<Color>? {
        val l = mutableListOf<Color>()
        args.forEach {
            val c = colors[it.toLowerCase().trim()]
            if (c != null)
                l.add(c)
            else{
                logError("The Color $it does not exist. Available Colors:\n ${colors.keys}")
                return null
            }
        }
        return  l.toList()
    }

    fun ease(x: Double)=
        if(x<0.5) 2*x*x else -1+(4-2*x)*x

}