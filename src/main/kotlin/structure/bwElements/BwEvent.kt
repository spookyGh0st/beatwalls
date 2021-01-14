package structure.bwElements

data class BwEvent(
    var type: Int,
    var value: Int,
    var propID: Int,
    var lightID: Int,
    override var beat: Double,
): BwElement
