package structure.specialStrucures

import assetFile.propOfName
import assetFile.readProperty
import structure.*
import structure.helperClasses.Point
import structure.helperClasses.SpookyWall

fun ContinuousCurve.run(): List<SpookyWall> {
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
    this.readProperty(propOfName(this, name)) as Point?

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