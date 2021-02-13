package interpreter

import org.junit.Test

import structure.bwElements.BwObstacle
import math.Quaternion
import math.Vec3
import java.io.File
import kotlin.math.PI
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TranslatorTest {
    private val bw = Beatwalls(File(""), listOf())
    private val tr = Translator(listOf(),bw)

    // todo
    //@Test
    fun `no Rotation, positive values`() {
        val bwObs = BwObstacle(
            translation = Vec3(1,2,3),
            scale = Vec3(2,2,2)
        )
        val neObs = tr.neObstacle(bwObs)
        val pos = listOf(0.0, 1.0)
        val scale = listOf(2.04,2.0)
        assertNotNull(neObs._obstacleCustomData)
        assertEquals(pos, neObs._obstacleCustomData!!._position)
        assertEquals(scale, neObs._obstacleCustomData!!._scale)
    }

    //@Test
    fun `no Rotation, negative values`() {
        val bwObs = BwObstacle(
            translation = Vec3(1,-2,3),
            scale = Vec3(-2,-2,-2)
        )
        val neObs = tr.neObstacle(bwObs)
        val pos = listOf(0.0, -3.0)
        val scale = listOf(2.0,2.0)
        assertNotNull(neObs._obstacleCustomData)
        assertEquals(pos, neObs._obstacleCustomData!!._position)
        assertEquals(scale, neObs._obstacleCustomData!!._scale)
    }

    //@Test
    fun `single Rotation, negative values`() {
        val euler = Vec3(0.0,0.0, 0.5*PI)
        val bwObs = BwObstacle(
            translation = Vec3(1,2,3),
            scale = Vec3(2,0,2),
            rotation = Quaternion(euler)
        )
        val neObs = tr.neObstacle(bwObs)
        val pos = listOf(0.0, 2.0)
        val scale = listOf(2.0,0.0)
        val rot = listOf(0.0,0.0,45.0)
        assertNotNull(neObs._obstacleCustomData)
        assertEquals(pos, neObs._obstacleCustomData!!._position,"Position does not match")
        assertEquals(scale, neObs._obstacleCustomData!!._scale, "scale does not match")
        assertEquals(rot, neObs._obstacleCustomData!!._localRotation, "rotation does not match")
    }
}