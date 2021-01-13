package structure.wallStructures

import structure.helperClasses.PointConnectionType
import types.BwInt

abstract class Wallpath(): WallStructure() {
    open var amount: BwInt = { (4 * scale()).toInt() }

    /**
     * TODO Beschreibung
     */
    var type: PointConnectionType = PointConnectionType.Rectangle


}