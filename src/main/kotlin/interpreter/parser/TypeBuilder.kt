package interpreter.parser

import beatwalls.logError
import net.objecthunter.exp4j.ExpressionBuilder
import structure.StructureState
import structure.math.PointConnectionType
import structure.math.Vec2
import structure.math.Vec3
import types.*
import kotlin.math.roundToInt
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
class TypeBuilder(val s: String, val ss: StructureState, val colors: Map<String, BwColor>) {
    fun build(type: KType): Any? = when (type) {
        typeOf<List<String>>()          -> buildList<String>()
        typeOf<List<Vec2>>()            -> buildList<Vec2>()
        typeOf<List<Vec3>>()            -> buildList<Vec3>()
        typeOf<Boolean>()               -> buildBoolean()
        typeOf<BwDouble>()              -> buildBwDouble()
        typeOf<BwInt>()                 -> buildBwInt()
        typeOf<Int>()                   -> buildInt()
        typeOf<Double>()                -> buildDouble()
        typeOf<String>()                -> buildString()
        typeOf<List<String>>()          -> buildList<String>()
        typeOf<Vec2>()                  -> buildVec2()
        typeOf<Vec3>()                  -> buildVec3()
        typeOf<PointConnectionType>()   -> buildPointConnectionType()
        typeOf<BwColor>()               -> buildBwColor()
        else -> { logError("Unknown type: $type"); null }
    }

    fun buildString() = s

    fun buildPointConnectionType(): PointConnectionType? {
        val types = PointConnectionType.values()
        return types.find { it.name.equals(s.trim(), true) }
    }

    fun buildBoolean(): Boolean? = when {
        s.toLowerCase() == "true" -> true
        s.toLowerCase() == "false" -> false
        else -> { logError("Only true or false allowed"); null }
    }

    fun buildInt(): Int?{
        return buildBwInt()?.invoke()
    }

    fun buildDouble(): Double?{
        return buildBwDouble()?.invoke()
    }

    fun buildBwDouble(): BwDouble? {
        val num = bwNumber() ?: return null
        return { num.invoke().toDouble() }
    }

    fun buildBwInt(): BwInt? {
        val num = bwNumber() ?: return null
        return { num.invoke().toDouble().roundToInt() }
    }

    private fun bwNumber(): BwNumber? {
        val e = try {
            ExpressionBuilder(s.toLowerCase())
                .functions(ss.functions)
                .variables(ss.variables.keys)
                .build()
        }catch (e: Exception){
            logError(e.message.toString())
            return null
        }
        e.setVariables(ss.variables)
        if (!e.validate().isValid){
            logError("The expression $s has errors:\n ${e.validate().errors}")
            return null
        }
        return {
            e.setVariables(ss.variables)
            e.evaluate()
        }
    }

    /**
     * Translation from a string to the color
     * This is a bit more difficult, since we can't use our cool framework directly.
     * Instead we will do a bit of manual Parsing of the few functions we have
     */
    fun buildBwColor(): BwColor? {
        val sanitizedString = s.toLowerCase().replace(" ", "")
        return colors[sanitizedString]?: colorFunc(s, ss)
    }

    fun colorFunc(s: String, ss: StructureState) : BwColor? {
        val head = s.substringBefore("(")
        val args = s
            .substringAfter(head)
            .removeSurrounding("(", ")")
            .replace("(","")
            .replace(")","")
            .split(",")
            .filter { it.isNotEmpty() }

        when (head){
            "gradient" -> {
                val colorList: List<BwColor> = colorList(args)?: return null
                if (colorList.size != 2) { logError("Only 2 values are allowed for the gradient"); return null }
                return gradient(ss,colorList[0],colorList[1])
            }
            "rainbow" -> {
                val r = args.getOrNull(0)?.toDoubleOrNull()?: 1.0
                val a = args.getOrNull(1)?.toDoubleOrNull()?: 1.0
                return rainbow(ss,r,a)
            }
            "random" -> {
                val colorList = colorList(args)?: return null
                return random(colorList)
            }
            else -> {
                logError("Color function $head is invalid. if you were looking for switch, it is now random()")
                return null
            }
        }
    }

    fun colorList(args: List<String>): List<BwColor>? {
        val l = mutableListOf<BwColor>()
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

    fun buildVec2(): Vec2? {
        val l = splitExpression(s).filter { it.isNotBlank() }
        val exprX = l.getOrNull(0)
        val exprY = l.getOrNull(1)
        if (exprX == null || exprY == null){
            logError("Not enough values provides. Format: x,y")
            return null
        }

        val valX = TypeBuilder(exprX,ss,colors).buildBwDouble()?.invoke()
        val valY = TypeBuilder(exprY,ss,colors).buildBwDouble()?.invoke()

        if (valX == null || valY == null){
            logError("One Expression is null")
            return null
        }

        return Vec2(valX, valY)
    }

    fun buildVec3(): Vec3? {
        val l = splitExpression(s).filter { it.isNotBlank() }
        val exprX = l.getOrNull(0)
        val exprY = l.getOrNull(1)
        val exprZ = l.getOrNull(2)
        if (exprX == null || exprY == null || exprZ == null){
            logError("Not enough values provides. Format: x,y,z")
            return null
        }

        val valX = TypeBuilder(exprX,ss,colors).buildBwDouble()?.invoke()
        val valY = TypeBuilder(exprY,ss,colors).buildBwDouble()?.invoke()
        val valZ = TypeBuilder(exprZ,ss,colors).buildBwDouble()?.invoke()

        if (valX == null || valY == null || valZ == null){
            logError("One Expression is null")
            return null
        }

        return Vec3(valX, valY, valZ)
    }

    inline fun <reified T> buildList(): List<Any?> {
        val s = splitExpression(s)
        return s.map { TypeBuilder(it,ss,colors).build(typeOf<T>()) }
    }

    fun splitExpression(s: String): List<String> {
        var start = 0
        var pCount = 0
        val l = mutableListOf<String>()

        for ((i, c) in s.withIndex()){
            when(c){
                ',' -> if (0 == pCount) {
                    l.add(s.substring(start,i))
                    start = i+1
                }
                '(' -> pCount++
                ')' -> pCount--
            }
        }
        l.add(s.substring(start).trim())
        return l.toList()
    }

}