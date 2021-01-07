package structure.wallStructures

import structure.helperClasses.SpookyWall

/**
 * create a single Wall
 */
class Wall: WallStructure() {
    /**
     * The StartTime of the wall, relative to the beat count.
     * Should be left at 0 most of the time.
     * Default: 0
     */
    var startTime = 0.0

    /**
     * Duration of the Wall in beats
     */
    var duration = 1.0

    /**
     * The startHeight of the wall.
     */
    var startHeight = 0.0

    /**
     * The Height of the wall.
     */
    var height = 0.0

    /**
     * The startRow of the Wall
     * 0 equals center. 1 equals 1 block size
     */
    var startRow = 0.0

    /**
     * The width of the Wall
     */
    var width = 0.0

    /**
     * generating the Walls
     */
     override fun create()  = listOf<SpookyWall>(
         SpookyWall(
             startRow = this.startRow,
             duration = this.duration,
             width = this.width,
             height = this.height,
             startHeight = this.startHeight,
             startTime = this.startTime
         )
     )
}