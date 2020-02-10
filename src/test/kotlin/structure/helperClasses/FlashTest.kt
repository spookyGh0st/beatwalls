package structure.helperClasses

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class FlashTest {
    private val l = mutableListOf<SpookyWall>()
    @Before
    fun createWalls() {
        repeat(100) {
            l.add(SpookyWall(0.0,0.0,0.0,0.0,0.0,0.0))
        }
    }
    @Test
    fun `one Color`() {
        Flash(listOf(red)).colorWalls(l)
        val countRed=l.filter { it.color==red }.size
        val countNull=l.filter { it.color==null }.size
        assertEquals(50,countRed)
        assertEquals(50,countNull)
        assertEquals(red,l.first().color)
        assertEquals(null,l[1].color)
        l.fold(green){
                acc: Color?, spookyWall: SpookyWall ->  assertNotEquals(acc,spookyWall.color).run { spookyWall.color }
        }
    }

    @Test
    fun `two colors`() {
        Flash(listOf(red,green)).colorWalls(l)
        val countRed=l.filter { it.color==red }.size
        val countGreen=l.filter { it.color==green }.size
        assertEquals(50,countRed)
        assertEquals(50,countGreen)
        assertEquals(red,l.first().color)
        assertEquals(green,l[1].color)
        l.fold(green){
            acc: Color, spookyWall: SpookyWall ->  assertNotEquals(acc,spookyWall.color).run { spookyWall.color }!!
        }
    }

    @Test
    fun `three colors`() {
        Flash(listOf(red,green,blue)).colorWalls(l)
        val countRed=l.filter { it.color==red }.size
        val countGreen=l.filter { it.color==green }.size
        val countBlue=l.filter { it.color==green }.size
        assertEquals(34,countRed)
        assertEquals(33,countGreen)
        assertEquals(33,countBlue)
        assertEquals(red,l.first().color)
        assertEquals(green,l[1].color)
        assertEquals(blue,l[2].color)
        l.fold(green){
                acc: Color, spookyWall: SpookyWall ->  assertNotEquals(acc,spookyWall.color).run { spookyWall.color }!!
        }
    }
}