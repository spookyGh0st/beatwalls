package structure.helperClasses

import beatwalls.GlobalConfig
import chart.difficulty._obstacleCustomData
import chart.difficulty._obstacles
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SpookyWallTest {
    @Before
    fun setGLobalConfig(){
        GlobalConfig.hjsDuration = 2.0
    }
    @Test
    fun standardToValidWall(){
        val expected = SpookyWall(
            x = 1.0,
            duration = 1.0,
            width = 1.0,
            height = 1.0,
            y = 1.0,
            z = 1.0,
            color = red
        )
        val actual = expected.toValidWall()
        assertEquals(expected,actual)
    }

    @Test
    fun negativeToValidWall(){
        val w = SpookyWall(
            x = -1.0,
            duration = -5.0,
            width = -1.0,
            height = -1.0,
            y = -1.0,
            z = 1.0,
            color = blue,
            rotation = -180.0

        )
        val expected = SpookyWall(
            x = -2.0,
            duration = -4.0,
            width = 1.0,
            height = 1.0,
            y = -2.0,
            z = 1.0,
            color = blue,
            rotation = -180.0 % 360

        )
        val actual = w.toValidWall()
        assertEquals(expected,actual)
    }
    @Test
    fun weirdToValidWall(){
        val w = SpookyWall(
            x = 0.0,
            duration = 0.0,
            width = 0.0,
            height = 0.0,
            y = 0.0,
            z = -1.0,
            color = blue
        )
        val expected = SpookyWall(
            x = 0.0,
            duration = 0.0001,
            width = minValue,
            height = minValue,
            y = 0.0,
            z = minValue,
            color = blue
        )
        val actual = w.toValidWall()
        assertEquals(expected,actual)
    }

    @Test
    fun to_NeObstacle() {
        GlobalConfig.neValues = true
        val w = SpookyWall(
            x = 0.0,
            duration = 0.0,
            width = 0.0,
            height = 0.0,
            y = 0.0,
            z = 1.0,
            color = null

        )
        val actual = w.to_obstacle()
        val expected = _obstacles(
            _time = 1.0F,
            _lineIndex =  3000,
            _type = 5001,
            _duration = 1.0E-4F,
            _width = 1005,
            _obstacleCustomData = _obstacleCustomData(
                _position = listOf(0.0, 0.0),
                _scale = listOf(0.005, 0.005),
                _color = null,
                _localRotation = null,
                _rotation = null,
                track = null
            )
        )
        assertEquals(expected, actual)
    }
    @Test
    fun to_MeObstacle() {
        GlobalConfig.neValues = false
        val w = SpookyWall(
            x = 0.0,
            duration = 0.0,
            width = 0.0,
            height = 0.0,
            y = 0.0,
            z = 1.0,
            color = null

        )
        val actual = w.to_obstacle()
        val expected = _obstacles(
            _time = 1.0F,
            _lineIndex =  3000,
            _type = 5001,
            _duration = 1.0E-4F,
            _width = 1005,
            _obstacleCustomData = null
        )
        assertEquals(expected, actual)
    }
    @Test
    fun to_MeObstacleButColor() {
        GlobalConfig.neValues = false
        val w = SpookyWall(
            x = 0.0,
            duration = 0.0,
            width = 0.0,
            height = 0.0,
            y = 0.0,
            z = 1.0,
            color = red

        )
        val actual = w.to_obstacle()
        val expected = _obstacles(
            _time = 1.0F,
            _lineIndex =  3000,
            _type = 5001,
            _duration = 1.0E-4F,
            _width = 1005,
            _obstacleCustomData = _obstacleCustomData(_color = listOf(1.0,0.0,0.0))
        )
        assertEquals(expected, actual)
    }
}