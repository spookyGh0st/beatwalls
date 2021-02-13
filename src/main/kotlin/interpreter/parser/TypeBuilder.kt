package interpreter.parser

import beatwalls.logError
import net.objecthunter.exp4j.ExpressionBuilder
import structure.ObjectStructure
import structure.Structure
import structure.StructureState
import structure.bwElements.Color
import math.PointConnectionType
import math.Vec2
import math.Vec3
import types.*
import kotlin.math.roundToInt
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
open class TypeBuilder(
    val s: String,
    val ss: StructureState,
    val colors: Map<String, Color>,
    val structFactories: Map<String, StructFactory>
) {
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
        typeOf<ObjectStructure>()             -> buildStructure()
        typeOf<BwColor>()               -> ColorBuilder(s,ss,colors,structFactories).buildColor()
        else -> { logError("Unknown type: $type"); null }
    }

    fun buildStructure(): Structure? {
        return structFactories[s.toLowerCase().trim()]?.invoke()
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

    fun buildVec2(): Vec2? {
        val l = splitExpression(s).filter { it.isNotBlank() }
        val exprX = l.getOrNull(0)
        val exprY = l.getOrNull(1)
        if (exprX == null || exprY == null){
            logError("Not enough values provides. Format: x,y")
            return null
        }

        val valX = TypeBuilder(exprX, ss, colors, structFactories).buildBwDouble()?.invoke()
        val valY = TypeBuilder(exprY, ss, colors, structFactories).buildBwDouble()?.invoke()

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

        val valX = TypeBuilder(exprX, ss, colors, structFactories).buildBwDouble()?.invoke()
        val valY = TypeBuilder(exprY, ss, colors, structFactories).buildBwDouble()?.invoke()
        val valZ = TypeBuilder(exprZ, ss, colors, structFactories).buildBwDouble()?.invoke()

        if (valX == null || valY == null || valZ == null){
            logError("One Expression is null")
            return null
        }

        return Vec3(valX, valY, valZ)
    }

    inline fun <reified T> buildList(): List<Any?> {
        val s = splitExpression(s)
        return s.map { TypeBuilder(it, ss, colors, structFactories).build(typeOf<T>()) }
    }
}
