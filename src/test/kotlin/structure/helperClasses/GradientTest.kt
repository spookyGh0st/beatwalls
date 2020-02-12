package structure.helperClasses

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class GradientTest {
    private val l = mutableListOf<SpookyWall>()
    @Before
    fun createWalls() {
        repeat(5) {
            l.add(SpookyWall(0.0,0.0,0.0,0.0,0.0,0.0))
        }
    }

    @Test
    fun colorWalls() {
        class T(
            val startColor: Color,
            val endColor: Color,
            val colorList: List<Color>)
        val tables = listOf<T>(
            T(red, red, listOf(red,red,red,red,red)),
            T(red, green, listOf(red, Color(0.75,0.25,0.0), Color(0.5,0.5,0.0), Color(0.25,0.75,0.0), green)),
            T(green, red, listOf(green, Color(0.25,0.75,0.0), Color(0.5,0.5,0.0), Color(0.75,0.25,0.0), red)),
            T(black, white, listOf(black, Color(0.25,0.25,0.25),Color(0.5,0.5,0.5),Color(0.75,0.75,0.75),white))
        )
        for (t in tables){
            Gradient(t.startColor,t.endColor).colorWalls(l)
            repeat(5){
                assertEquals(t.colorList[it],l[it].color)
            }
        }
    }
}