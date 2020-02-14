package structure.helperClasses

import org.junit.Assert.*
import org.junit.Test

class ColorTest(){
    @Test
    fun testConstructor(){
        assertEquals(Color(1.0,1.0,1.0),Color(255,255,255))
        assertEquals(Color(1.0,1.0,1.0), white)
        assertEquals(white,Color(255,255,255))
    }
}
