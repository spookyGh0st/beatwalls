package structure.math

import beatwalls.logError
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

data class CuboidConstrains(val p0: Vec3, val p1: Vec3, var r: Random) {
    // The player space
    private val redP0 = Vec3(-2,0,0)
    private val redP1 = Vec3(2,3,0)

    val xMinBlue = minOf(p0.x, p1.x)
    val xMinRed = minOf(redP0.x, redP1.x)
    val xMaxBlue = maxOf(p0.x, p1.x)
    val xMaxRed = maxOf(redP0.x, redP1.x)

    val yMinBlue = minOf(p0.y, p1.y)
    val yMinRed = minOf(redP0.y, redP0.y)
    val yMaxBlue = maxOf(p0.y, p1.y)
    val yMaxRed = maxOf(redP0.y, redP1.y)

    fun randomVec3(avoidCenter: Boolean, z: Double): Vec3 {
        return try {
            val vec2 = random(avoidCenter)
            vec2.toVec3(z)
        } catch (e: Exception){
            logError("Could not retrieve a random Vec3, continuing with Vec3(0,0,0)")
            logError("Please check if your Cuboid is tall enough and consider turning off avoidCenter")
            logError(e.message.toString())
            Vec3()

        }
    }

    private fun random(avoidCenter: Boolean): Vec2 {
        val minAdd = 0.00001
        if (!avoidCenter || xMinBlue >= xMaxRed || yMinBlue >= yMaxRed || xMaxBlue <= xMinRed || yMaxBlue <= yMinRed){
            return Vec2(
                r.nextDouble(xMinBlue, xMaxBlue + minAdd),
                r.nextDouble(yMinBlue, yMaxBlue + minAdd),
            )
        } else{
            val x = if(r.nextDouble(1.0) < getRatio(xMinBlue-xMinRed, xMaxRed-xMaxBlue))
                r.nextDouble(xMinBlue, xMinRed + minAdd)
            else
                r.nextDouble(xMaxRed, xMaxBlue + minAdd)

            val y = if(r.nextDouble(1.0) < getRatio(yMinBlue-yMinRed, yMaxRed-yMaxBlue))
                r.nextDouble(yMinBlue, yMinRed + minAdd)
            else
                r.nextDouble(yMaxRed, yMaxBlue + minAdd)

            return Vec2(x,y)
        }
    }
    private fun getRatio(d1: Double, d2: Double) =
        abs(d1/(d1+d2)) // abs required for - infinity
}