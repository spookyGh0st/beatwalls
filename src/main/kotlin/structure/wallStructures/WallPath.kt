package structure.wallStructures

import structure.math.PointConnectionType
import types.BwInt

/**
 * @suppress
 * All wallstructures creating Walls along a path
 */
abstract class WallPath: WallStructure() {
    /**
     * TODO find good default
     */
    open var amount: BwInt = { (4 * scale()).toInt() }

    /**
     * TODO Beschreibung
     */
    var type: PointConnectionType = PointConnectionType.Rectangle


}