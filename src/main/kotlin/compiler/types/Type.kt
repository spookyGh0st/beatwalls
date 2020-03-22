package compiler.types

import compiler.Compiler
import compiler.Properties.fillProperty
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.*

sealed class TypeEnum {
    abstract fun checkType(c: Compiler, key: String, value: String): Boolean
    abstract fun Compiler.createType(key: String, value: String)
    fun createTypeFor(compiler: Compiler, key: String, value: String) {
        if (checkType(compiler, key, value))
            compiler.createType(key, value)

    }
}

internal object StructType : TypeEnum() {
    override fun checkType(c: Compiler, key: String, value: String): Boolean =
        isDouble(key) && c.isValidWallStructure(value)

    override fun Compiler.createType(key: String, value: String) {
        val structPreset: WallStructure =
            storedStructures[value] ?: defaultStructures[value] ?: throw NoSuchElementException()
        val struct = structPreset.deepCopy()

        struct.beat = key.toDouble()
        wallStructures.add(struct)
        lastStructure = struct
    }
}

internal object InterfaceType : TypeEnum() {
    override fun checkType(c: Compiler, key: String, value: String): Boolean =
        key == "interface"

    override fun Compiler.createType(key: String, value: String) {
        val i = Interface()
        lastStructure = i
        storedInterfaces[value] = i
    }
}

internal object DefineType : TypeEnum() {
    override fun checkType(c: Compiler, key: String, value: String): Boolean =
        when {
            isDouble(key) -> false
            c.isValidWallStructure(key) -> false
            key in protectedStrings -> false
            c.isValidWallStructure(value) -> true
            value.isEmpty() -> true
            else -> false
        }

    override fun Compiler.createType(key: String, value: String) {
        val structPreset: WallStructure =
            storedStructures[value] ?: defaultStructures[value] ?: throw NoSuchElementException()
        val baseStruct = structPreset.deepCopy()

        val struct = Define()
        struct.baseStructure = baseStruct

        storedStructures[key] = struct
        lastStructure = struct
    }
}

internal object PropertyType : TypeEnum() {
    override fun checkType(c: Compiler, key: String, value: String): Boolean =
        key in wallStructurePropertyNames

    override fun Compiler.createType(key: String, value: String) {
        try {
            fillProperty(lastStructure, key, value)
        } catch (e: NoSuchElementException) {
            if (lastStructure is Define)
                fillProperty((lastStructure as Define).baseStructure, key, value)
            else
                throw e
        }
    }
}

internal object FunType : TypeEnum() {
    override fun checkType(c: Compiler, key: String, value: String): Boolean =
        key == "fun"

    override fun Compiler.createType(key: String, value: String) {
        addArg(Function(value, *args.values.toTypedArray()))
    }
}

internal object ValType : TypeEnum() {
    override fun checkType(c: Compiler, key: String, value: String): Boolean =
        key == "val"

    override fun Compiler.createType(key: String, value: String) {
        addArg(Constant(value, *args.values.toTypedArray()))
    }
}

fun main() {
    val hm = hashMapOf<Int, String>()
    hm[10] = "sdja;"
}