package structure.helperClasses

import org.junit.Assert.*
import org.junit.Test

class CuboidConstrainsTest{
    class Table(
        val p1: Point,
        val p2: Point,
        val sx:Double,
        val ex: Double,
        val sy : Double,
        val ey : Double,
        val sz : Double,
        val ez : Double,
        val duration : Double,
        val height : Double,
        val width : Double
    )
    @Test
    fun testCuboidConstrains(){
        val delta = 0.0

        val tables=listOf(
            Table(Point(0,0,0),Point(1,1,1),0.0, 1.0,0.0,1.0,0.0,1.0,1.0,1.0,1.0),
            Table(Point(1,1,1),Point(0,0,0),0.0, 1.0,0.0,1.0,0.0,1.0,1.0,1.0,1.0),
            Table(Point(-1,-1,-1),Point(0,0,0),-1.0, 0.0,-1.0,0.0,-1.0,0.0,1.0,1.0,1.0),
            Table(Point(0,0,0),Point(0,0,0),0.0, 0.0000001,0.0,0.0000001,0.0,0.0000001,0.0000001,0.0000001,0.0000001)
        )
        for(t in tables){
            val c= CuboidConstrains(t.p1,t.p2)
            assertEquals(t.sx,c.sx,delta)
            assertEquals(t.ex,c.ex,delta)
            assertEquals(t.sy,c.sy,delta)
            assertEquals(t.ey,c.ey,delta)
            assertEquals(t.sz,c.sz,delta)
            assertEquals(t.ez,c.ez,delta)
            assertEquals(t.duration,c.duration,delta)
            assertEquals(t.height,c.height,delta)
            assertEquals(t.width,c.width,delta)
        }
    }
}