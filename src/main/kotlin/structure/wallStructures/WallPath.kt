package structure.wallStructures

import structure.bwElements.BwObstacle
import math.PointConnectionType
import math.Vec2
import math.Vec3
import types.BwInt

/**
 * @suppress
 * All wallstructures creating Walls along a path
 */
interface WallPath {

    var amount: BwInt
    var duration: Double
    var type: PointConnectionType

    fun vec3PointList(vararg points: Vec2?): List<Vec3> {
        val l = mutableListOf<Vec3>()
        val pl = points.filterNotNull()
        for ((i, p) in pl.withIndex()){
            val z = i.toDouble()/(pl.size-1)
            l.add(p.toVec3(z))
        }
        return l.toList()
    }

    fun createFromPointList(points: List<Vec3>): List<BwObstacle> {
        val l = mutableListOf<BwObstacle>()
        points[0].z *= duration
        for (i in 0 until points.size-1){
            val p0 = points[i]
            val p1 = points[i+1]
            p1.z *= duration
            val obst = BwObstacle(p0,p1,type)
            l.add(obst)
        }
        return l.toList()
    }
}