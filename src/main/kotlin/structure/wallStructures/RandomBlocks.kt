package structure.wallStructures

import structure.specialStrucures.run

/**
 * place random blocks around the player
 */
class RandomBlocks: WallStructure(){

    var duration = 4

    var amount= 8

    var wallDuration = 1.0

    /**
     * generating the Walls
     */
     override fun generate()  = run()
}