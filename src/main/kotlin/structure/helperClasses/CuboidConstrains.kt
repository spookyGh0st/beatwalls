package structure.helperClasses

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class CuboidConstrains(val p0: Vec3, val p1: Vec3,var r: Random) {
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

    fun random(avoidCenter: Boolean, z: Double): Vec3 {
        if (!avoidCenter){
            return Vec3(
                r.nextDouble(xMinBlue, xMaxBlue),
                r.nextDouble(yMinBlue, yMaxBlue),
                z
            )
        } else{
            val x = if(r.nextDouble(1.0) < getRatio(xMinBlue-xMinRed, xMaxRed-xMaxBlue))
                r.nextDouble(xMinBlue, xMinRed)
            else
                r.nextDouble(xMaxRed, xMaxBlue)

            val y = if(r.nextDouble(1.0) < getRatio(yMinBlue-yMinRed, yMaxRed-yMaxBlue))
                r.nextDouble(yMinBlue, yMinRed)
            else
                r.nextDouble(yMaxRed, yMaxBlue)

            return Vec3(x,y,z)
        }
    }
    private fun getRatio(d1: Double, d2: Double) =
        d1/(d1+d2)
}