package types

import structure.StructureState

data class BwPoint(
    val x: BwDouble,
    val y: BwDouble,
    val z: BwDouble,
)

fun bwPoint(s: String, ss: StructureState): BwPoint {
    val values = s.split(",")
        .map { s.trim() }
        .map { bwDouble(it, ss) }
    return BwPoint(
        x = values.getOrNull(0) ?: bwDouble(0),
        y = values.getOrNull(1) ?: bwDouble(0),
        z = values.getOrNull(2) ?: bwDouble(0)
    )
}

fun bwPoint(x:Number = 0.0, y: Number = 0.0, z: Number = 0.0) =
    BwPoint({x.toDouble()}, {y.toDouble()}, {z.toDouble()})


