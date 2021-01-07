package structure.wallStructures

import assetFile.findProperty
import assetFile.readProperty
import structure.helperClasses.Point
import structure.helperClasses.SpookyWall

/**
 * Creates a continues line with up to 31 Points. this one does not have a typo in the name :)
 */
class ContinuousCurve : WallStructure(){
    /**
     * dont touch
     */
    internal val creationAmount = 32
    /**
     * The duration of each wall
     */
    var duration: Double = -3.0
    /**
     * The amount of Walls per beat
     */
    var amount: Int = 8

    /**
     * The 1 Point. use this to set an exact Point the wall will go through
     */
    var p1: Point? = null

    /**
     * The ControllPoint for the 1 Point. Use this to guide the curve to his direction
     */
    var c1: Point? = null

    /**
     * The 2 Point. use this to set an exact Point the wall will go through
     */
    var p2: Point? = null

    /**
     * The ControllPoint for the 2 Point. Use this to guide the curve to his direction
     */
    var c2: Point? = null

    /**
     * The 3 Point. use this to set an exact Point the wall will go through
     */
    var p3: Point? = null

    /**
     * The ControllPoint for the 3 Point. Use this to guide the curve to his direction
     */
    var c3: Point? = null

    /**
     * The 4 Point. use this to set an exact Point the wall will go through
     */
    var p4: Point? = null

    /**
     * The ControllPoint for the 4 Point. Use this to guide the curve to his direction
     */
    var c4: Point? = null

    /**
     * The 5 Point. use this to set an exact Point the wall will go through
     */
    var p5: Point? = null

    /**
     * The ControllPoint for the 5 Point. Use this to guide the curve to his direction
     */
    var c5: Point? = null

    /**
     * The 6 Point. use this to set an exact Point the wall will go through
     */
    var p6: Point? = null

    /**
     * The ControllPoint for the 6 Point. Use this to guide the curve to his direction
     */
    var c6: Point? = null

    /**
     * The 7 Point. use this to set an exact Point the wall will go through
     */
    var p7: Point? = null

    /**
     * The ControllPoint for the 7 Point. Use this to guide the curve to his direction
     */
    var c7: Point? = null

    /**
     * The 8 Point. use this to set an exact Point the wall will go through
     */
    var p8: Point? = null

    /**
     * The ControllPoint for the 8 Point. Use this to guide the curve to his direction
     */
    var c8: Point? = null

    /**
     * The 9 Point. use this to set an exact Point the wall will go through
     */
    var p9: Point? = null

    /**
     * The ControllPoint for the 9 Point. Use this to guide the curve to his direction
     */
    var c9: Point? = null

    /**
     * The 10 Point. use this to set an exact Point the wall will go through
     */
    var p10: Point? = null

    /**
     * The ControllPoint for the 10 Point. Use this to guide the curve to his direction
     */
    var c10: Point? = null

    /**
     * The 11 Point. use this to set an exact Point the wall will go through
     */
    var p11: Point? = null

    /**
     * The ControllPoint for the 11 Point. Use this to guide the curve to his direction
     */
    var c11: Point? = null

    /**
     * The 12 Point. use this to set an exact Point the wall will go through
     */
    var p12: Point? = null

    /**
     * The ControllPoint for the 12 Point. Use this to guide the curve to his direction
     */
    var c12: Point? = null

    /**
     * The 13 Point. use this to set an exact Point the wall will go through
     */
    var p13: Point? = null

    /**
     * The ControllPoint for the 13 Point. Use this to guide the curve to his direction
     */
    var c13: Point? = null

    /**
     * The 14 Point. use this to set an exact Point the wall will go through
     */
    var p14: Point? = null

    /**
     * The ControllPoint for the 14 Point. Use this to guide the curve to his direction
     */
    var c14: Point? = null

    /**
     * The 15 Point. use this to set an exact Point the wall will go through
     */
    var p15: Point? = null

    /**
     * The ControllPoint for the 15 Point. Use this to guide the curve to his direction
     */
    var c15: Point? = null

    /**
     * The 16 Point. use this to set an exact Point the wall will go through
     */
    var p16: Point? = null

    /**
     * The ControllPoint for the 16 Point. Use this to guide the curve to his direction
     */
    var c16: Point? = null

    /**
     * The 17 Point. use this to set an exact Point the wall will go through
     */
    var p17: Point? = null

    /**
     * The ControllPoint for the 17 Point. Use this to guide the curve to his direction
     */
    var c17: Point? = null

    /**
     * The 18 Point. use this to set an exact Point the wall will go through
     */
    var p18: Point? = null

    /**
     * The ControllPoint for the 18 Point. Use this to guide the curve to his direction
     */
    var c18: Point? = null

    /**
     * The 19 Point. use this to set an exact Point the wall will go through
     */
    var p19: Point? = null

    /**
     * The ControllPoint for the 19 Point. Use this to guide the curve to his direction
     */
    var c19: Point? = null

    /**
     * The 20 Point. use this to set an exact Point the wall will go through
     */
    var p20: Point? = null

    /**
     * The ControllPoint for the 20 Point. Use this to guide the curve to his direction
     */
    var c20: Point? = null

    /**
     * The 21 Point. use this to set an exact Point the wall will go through
     */
    var p21: Point? = null

    /**
     * The ControllPoint for the 21 Point. Use this to guide the curve to his direction
     */
    var c21: Point? = null

    /**
     * The 22 Point. use this to set an exact Point the wall will go through
     */
    var p22: Point? = null

    /**
     * The ControllPoint for the 22 Point. Use this to guide the curve to his direction
     */
    var c22: Point? = null

    /**
     * The 23 Point. use this to set an exact Point the wall will go through
     */
    var p23: Point? = null

    /**
     * The ControllPoint for the 23 Point. Use this to guide the curve to his direction
     */
    var c23: Point? = null

    /**
     * The 24 Point. use this to set an exact Point the wall will go through
     */
    var p24: Point? = null

    /**
     * The ControllPoint for the 24 Point. Use this to guide the curve to his direction
     */
    var c24: Point? = null

    /**
     * The 25 Point. use this to set an exact Point the wall will go through
     */
    var p25: Point? = null

    /**
     * The ControllPoint for the 25 Point. Use this to guide the curve to his direction
     */
    var c25: Point? = null

    /**
     * The 26 Point. use this to set an exact Point the wall will go through
     */
    var p26: Point? = null

    /**
     * The ControllPoint for the 26 Point. Use this to guide the curve to his direction
     */
    var c26: Point? = null

    /**
     * The 27 Point. use this to set an exact Point the wall will go through
     */
    var p27: Point? = null

    /**
     * The ControllPoint for the 27 Point. Use this to guide the curve to his direction
     */
    var c27: Point? = null

    /**
     * The 28 Point. use this to set an exact Point the wall will go through
     */
    var p28: Point? = null

    /**
     * The ControllPoint for the 28 Point. Use this to guide the curve to his direction
     */
    var c28: Point? = null

    /**
     * The 29 Point. use this to set an exact Point the wall will go through
     */
    var p29: Point? = null

    /**
     * The ControllPoint for the 29 Point. Use this to guide the curve to his direction
     */
    var c29: Point? = null

    /**
     * The 30 Point. use this to set an exact Point the wall will go through
     */
    var p30: Point? = null

    /**
     * The ControllPoint for the 30 Point. Use this to guide the curve to his direction
     */
    var c30: Point? = null

    /**
     * The 31 Point. use this to set an exact Point the wall will go through
     */
    var p31: Point? = null

    /**
     * The ControllPoint for the 31 Point. Use this to guide the curve to his direction
     */
    var c31: Point? = null

    /**
     * The 32 Point. use this to set an exact Point the wall will go through
     */
    var p32: Point? = null

    /**
     * The ControllPoint for the 32 Point. Use this to guide the curve to his direction
     */
    var c32: Point? = null

    /**
     * generating the Walls
     */
     override fun create(): List<SpookyWall> {
        val l= mutableListOf<SpookyWall>()
        for(i in 1 until creationAmount){
            val point = readPoint("p$i")
            val controlPoint = readPoint("c$i")
            val nextPoint = readPoint("p${i +1}")
            val nextControlPoint = readPoint("c${i+1}")
            val nextNextPoint = try { readPoint("p${i+2}") } catch (e:Exception){ null }
            if(point!=null && nextPoint!=null && controlPoint!=null && nextControlPoint!= null) {
                val tempP1 = point
                val tempP2 = controlPoint.copy(z = point.z + (1/3.0) * (nextPoint.z - point.z))
                val tempP3 =
                    calcP3(point, nextPoint, nextControlPoint, nextNextPoint)
                val tempP4 = nextPoint
                val amount = ((tempP4.z - tempP1.z) * amount).toInt()
                l.addAll(curve(tempP1, tempP2, tempP3, tempP4, amount))
            }
        }
        return l.toList()
    }
}

fun calcP3(point: Point, nextPoint: Point, nextControlPoint: Point, nextNextPoint: Point?): Point {
    //todo something is still not right
    val defaultOffset = point.z + (nextPoint.z-point.z)
    val nextControlPointZ = nextPoint.z + (1/3.0) * ((nextNextPoint?.z ?: defaultOffset) - nextPoint.z)
    val nextCp = nextControlPoint.copy(z = nextControlPointZ)
    val cp = nextPoint.mirrored(nextCp)
    val z = point.z+0.66*(nextPoint.z-point.z)
    val m = z-cp.z
    val x = cp.x + m*(point.x-cp.x)
    val y = cp.y + m*(point.y-cp.y)
    return Point(x, y, z)
}

fun WallStructure.readPoint(name:String): Point? =
    this.readProperty(findProperty(this, name)) as Point?

@Suppress("unused")
fun ContinuousCurve.generateProperties(): String {
    var s = ""
    for(it in 1 .. creationAmount){
        s+="""
            
            
            /**
            * The $it Point. use this to set an exact Point the wall will go through
            */
            var p$it: Point? = null
            
            /** 
            * The ControllPoint for the $it Point. Use this to guide the curve to his direction
            */
            var c$it: Point? = null
        """.trimIndent()
    }
    return s
}