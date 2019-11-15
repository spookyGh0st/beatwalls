package difficulty

import assetFile.MetaData
import org.junit.Assert.assertEquals
import org.junit.Test

class BpmAdjusterTest {

    private val bpmAdjuster : BpmAdjuster

    init {
        val meta = MetaData(120.0,2.0,0.0)
        val c = _customData(1, arrayListOf(
            _BPMChanges(120.0, 3.0,4,4),
            _BPMChanges(130.0, 5.0,4,4),
            _BPMChanges(80.0, 6.6153841018676758,4,4),
            _BPMChanges(120.0,8.1153841018676758,4,4
            )
        ), arrayListOf())

        val diff = Difficulty("2.0.0", arrayListOf(), arrayListOf(), arrayListOf(),c)
        bpmAdjuster = BpmAdjuster(diff,meta)
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
    fun `beat 6 should be  _time 5,92`() {
        val expected = 5.923076923076923
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

    @Test
    fun mapChanges() {
    }

    @Test
    fun findTime() {
    }
}