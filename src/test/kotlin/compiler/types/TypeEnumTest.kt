package compiler.types

import org.junit.Test
import structure.Define
import kotlin.test.assertEquals

class TypeEnumTest {
    @Test
    fun testTypes() {
        testTypeEnum(StructType)
        testTypeEnum(InterfaceType)
        testTypeEnum(ValType)
        testTypeEnum(FunType)
        testTypeEnum(DefineType)
        testTypeEnum(PropertyType)
    }

    fun testTypeEnum(t: TypeEnum) {
        val table = listOf(
            Row("10.0", "line", StructType),
            Row("interface", "hallo", InterfaceType),
            Row("val", "hallo", ValType),
            Row("fun", "hallo", FunType),
            Row("myWall", "line", DefineType),
            Row("myWall", "", DefineType),
            Row("myWall", "testDefine", DefineType),
            Row("changeduration", "10.0", PropertyType),
            Row("p1", "10.0", PropertyType)
        )
        val c = testCompiler()
        c.storedStructures["testDefine"] = Define()
        for (r in table) {
            val actual = t.checkType(c, r.key, r.value)
            assertEquals(t === r.type, actual, "with key ${r.key} and value ${r.value} $t should be ${r.type}?")
        }
    }
}

data class Row(
    val key: String,
    val value: String,
    val type: TypeEnum
)

