package interpreter

import chart.difficulty._obstacles
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import java.io.File
import kotlin.test.assertNotEquals

class MapLoaderTest {
    val uri = this::class.java.getResource("/map").toURI()
    val ml = MapLoader(File(uri))

    @Test
    fun loadInfo() {
        val info = ml.loadInfo()
        assertEquals(105.0, info._beatsPerMinute, 0.0)
    }

    @Test
    fun loadDifficulty() {
        val diff = ml.loadDifficulty("Standard", Options.DifficultyType.Easy)
        assertNotNull(diff)
    }

    @Test
    fun `Test Complete Difficult writing`() {
        val oDiff = ml.loadDifficulty("Standard", Options.DifficultyType.Easy)

        assertNotNull(oDiff)
        ml.writeDiff(
            oDiff!!, listOf(_obstacles(
                _time = 0.0,
                _lineIndex = 1,
                _duration = 1.0,
                _type = 1,
                _width = 1,
                _obstacleCustomData = null
            )), listOf(), listOf()
        )

        val nDiff = ml.loadDifficulty("Standard", Options.DifficultyType.Easy)
        oDiff.file.writeText(Gson().toJson(oDiff))
        assertNotEquals(oDiff, nDiff, "The Difficulties should have changed")
        assertEquals(1, nDiff!!._obstacles.size)
        assertNotEquals(1, oDiff._obstacles.size)

        assertEquals(0, nDiff._notes.size)
        assertNotEquals(0, oDiff._notes.size)

        assertEquals(0, nDiff._events.size)
        assertNotEquals(0, oDiff._events.size)
    }
}