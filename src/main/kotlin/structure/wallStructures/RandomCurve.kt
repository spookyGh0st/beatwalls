package structure.wallStructures

import structure.bwElements.BwObstacle
import structure.math.Vec3
import types.BwInt
import kotlin.math.abs

/**
 * random curves in a given cubic. Always starts at p1 and ends at p2.
 */
class RandomCurve: WallPath(){
    /**
     * first Point that controls the cubic, in which section walls are created. defaults to a random side
     */
    var p0: Vec3 = Vec3()

    /**
     * second Point that crontrols in which section walls are created. z must be higher than p1
     */
    var p1: Vec3 = Vec3()

    /**
     * The amount of single curve nesting together.
     * Defaults to abs(p1.z-p0.z)
     */
    var splineAmount: BwInt = { 8* abs(p1.z-p0.z).toInt() }

    /**
     * generating the Walls
     */
     override fun createWalls(): List<BwObstacle> {
        TODO()
    }
}
