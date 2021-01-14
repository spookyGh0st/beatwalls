package structure.wallStructures

import structure.math.PointConnectionType
import types.BwInt

abstract class WallPath: WallStructure() {
    open var amount: BwInt = { (4 * scale()).toInt() }

    /**
     * TODO Beschreibung
     */
    var type: PointConnectionType = PointConnectionType.Rectangle


}