package structure.wallStructures

import interpreter.property.specialProperties.BwString
import org.junit.Test
import structure.RawWs
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertEquals

class RawWsTest {
    @Test
    fun createWalls(){
        val ws = RawWs()
        val json= """
           [{"_time":103.6875,"_lineIndex":11000,"_type":203601,"_duration":-2.625,"_width":2000,"_customData":{"_position":[8.0,4.0],"_scale":[1.0,1.0],"_rotation":[90.0,0.0,0.0]}},{"_time":103.6875,"_lineIndex":-8000,"_type":203601,"_duration":-2.625,"_width":2000,"_customData":{"_position":[-9.0,4.0],"_scale":[1.0,1.0],"_rotation":[90.0,-0.0,-0.0]}},{"_time":103.8125,"_lineIndex":11000,"_type":203601,"_duration":-2.625,"_width":2000,"_customData":{"_position":[8.0,4.0],"_scale":[1.0,1.0],"_rotation":[90.0,0.0,0.0]}}] 
        """.trimIndent()
        ws::json.isAccessible = true
        val del = ws::json.delegateAs<BwString>()!!
        del.setExpr(json)
        val walls = ws.walls
        assertEquals(walls.size,3)
        assertEquals(walls.first().rotationX,90.0)
    }
}

inline fun <reified R> KProperty0<*>.delegateAs(): R? {
    isAccessible = true
    return getDelegate() as? R
}