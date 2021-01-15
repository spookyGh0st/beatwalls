package structure.wallStructures

import beatwalls.logError
import structure.ObjectStructure
import structure.bwElements.BwObject
import structure.math.CuboidConstrains
import structure.math.Vec3
import types.BwInt
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Define your own WallStructure from existing WallStructures.
 */
class WallStructureField: ObjectStructure() {
    /**
     * The name of the defined Structure you want to use Structure
     */
    var structure: ObjectStructure = EmptyWallStructure()

    /**
     * The first point of the area which your structures get placed into
     */
    var p0 = Vec3(-8, 0, 0)

    /**
     * The first point of the area which your structures get placed into
     */
    var p1 = Vec3(8, 0, 8)

    /**
     * How many structures you want to place. default: 4* p1.z-p0.z
     */
    var amount: BwInt = { (4* abs(p1.z -p0.z)).roundToInt() }

    /**
     * avoids spawning structures in the playspace. default: true
     */
    var avoidCenter: Boolean = true


    override fun createObjects(): List<BwObject> {
        val l= mutableListOf<BwObject>()
        val cc = CuboidConstrains(p0, p1, structureState.R)
        for(i in 0 until amount()) {
            val t = structure.run()
            val vec3 = cc.random(avoidCenter,0.0)
            if (t.any{ it !is BwObject }){
                logError("A Wallstructurefield can only hold Structures that produce Walls or Notes")
                return emptyList()
            }
            val objects = t.filterIsInstance<BwObject>()
            objects.forEach {
                it.position.x += vec3.x
                it.position.y += vec3.y
                it.position.z += p0.z + i.toDouble() / amount() * (p1.z-p0.z)
            }
            l.addAll(objects)
        }
        return l.toList()
    }
}