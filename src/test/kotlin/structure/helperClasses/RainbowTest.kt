package structure.helperClasses

import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import structure.Line
import structure.specialStrucures.run
import structure.walls

class RainbowTest {
    private val l = mutableListOf<SpookyWall>()
    @Before
    fun createWalls() {
        repeat(10) {
            l.add(SpookyWall(0.0,0.0,0.0,0.0,0.0,0.0))
        }
    } @Test
    fun rainbowTest(){
        Rainbow().colorWalls(l)
        val actual =l.map { it.color }
        println(actual)
        val expected = listOf(
            Color(red=0.5, green=0.9330127018922194, blue=0.06698729810778081),
            Color(red=0.7938926261462366, green=0.7033683215379002, blue=0.0027390523158632996),
            Color(red=0.9755282581475768, green=0.39604415459112047, blue=0.1284275872613027),
            Color(red=0.9755282581475768, green=0.128427587261303, blue=0.3960441545911201),
            Color(red=0.7938926261462367, green=0.002739052315863355, blue=0.7033683215378999),
            Color(red=0.5000000000000001, green=0.06698729810778048, blue=0.9330127018922192),
            Color(red=0.2061073738537635, green=0.29663167846209954, blue=0.9972609476841368),
            Color(red=0.024471741852423234, green=0.6039558454088793, blue=0.8715724127386977),
            Color(red=0.02447174185242318, green=0.8715724127386968, blue=0.6039558454088805),
            Color(red=0.20610737385376332, green=0.9972609476841366, blue=0.2966316784621006)
        )
        assertEquals(expected, actual)

    }
}