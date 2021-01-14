package chart.difficulty

import interpreter.BpmAdjuster
import model.difficulty._customData
import org.junit.Assert.assertEquals
import org.junit.Test

class BpmAdjusterTest {

    private val bpmAdjuster : BpmAdjuster

    init {
        val c = _customData(1.0, arrayListOf(
            _BPMChanges(120.0, 3.0,4,4),
            _BPMChanges(130.0, 5.0,4,4),
            _BPMChanges(80.0, 6.615384101867676,4,4),
            _BPMChanges(
                120.0, 8.115384101867676,4,4
            )
        ), arrayListOf())

        val diff = Difficulty(
            _version = "2.0.0",
            _notes = listOf(),
            _obstacles = listOf(),
            _events = listOf(),
            _customEvents = null,
            _customData = c,
            _waypoints = listOf(),
        )
        diff.bpm = 120.0
        diff.offset = 0.0
        bpmAdjuster = BpmAdjuster(diff)
    }

    @Test
    fun `beat 3 should be  _time 3`() {
        val expected = 3.0
        val actual = bpmAdjuster.findTime(3.0)
        assertEquals(expected, actual,0.0)
    }
    @Test
    fun `beat 5 should be  _time 5`() {
        val expected = 5.0
        val actual = bpmAdjuster.findTime(5.0)
        assertEquals(expected, actual,0.0)
    }
    @Test
    fun `beat 6 should be  _time 6,0833`() {
        val expected = 6.083333333333333
        val actual = bpmAdjuster.findTime(6.0)
        assertEquals(expected, actual,0.0)
    }
    @Test
    fun `beat 7 should be  _time 6,61`() {
        val expected = 6.6153841018676758
        val actual = bpmAdjuster.findTime(7.0)
        assertEquals(expected, actual,0.0)
    }
    @Test
    fun `beat 8 should be  _time 8,11`() {
        val expected = 8.1153841018676758
        val actual = bpmAdjuster.findTime(8.0)
        assertEquals(expected, actual,0.0)
    }
}