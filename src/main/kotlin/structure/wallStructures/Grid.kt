package structure.wallStructures

import structure.bwElements.BwObstacle
import structure.math.Vec3

/**
 * A 3d Grid
 */
class Grid : WallStructure() {
    /**
     * the X-Size of one panel in the grid
     */
    var panelX = 1.0

    /**
     * the Y-Size of one panel in the grid
     */
    var panelY = 0.0

    /**
     * the Z-Size of one panel in the grid
     */
    var panelZ = 1.0

    /**
     * the X-Size of the whole grid, aka how often it will repeat in the X-direction
     */
    var gridX = 8

    /**
     * the Y-Size of the whole grid, aka how often it will repeat in the Y-direction
     */
    var gridY = 1

    /**
     * the Z-Size of the whole grid aka how often it will repeat in the Z-direction
     */
    var gridZ = 8

    /**
     * different modes of walls
     *
     * 0 = create every wall in the pattern
     *
     * 1 = chess-pattern
     *
     * want more? write me
     */
    var mode = 0

    /**
     * the start Point of the grid
     */
    var p0: Vec3 =
        Vec3(-4, 0, 0)

    /**
     * generating the Walls
     */
    override fun createWalls(): List<BwObstacle> {
        val l = mutableListOf<BwObstacle>()
        var x = p0.x
        var y = p0.y
        var z = p0.z
        repeat(gridX) { itX ->
            repeat(gridY) { itY ->
                repeat(gridZ) { itZ ->
                    val w =BwObstacle(scale = Vec3(panelX,panelY,panelZ), translation = Vec3(x,y,z))
                    if((itZ+itY+itX)%2==0 || mode == 0)
                        l.add(w)
                    z += panelZ
                }
                z = p0.z
                y += panelY
            }
            y = p0.y
            x += panelX
        }
        x = p0.x
        return l.toList()
    }
}