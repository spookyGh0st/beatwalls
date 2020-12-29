package structure

import java.io.Serializable
import kotlin.random.Random

// Each Structure owns one StructureState
// It is the part the Structure shares with it's Expressions
// Functions can access it's elements
// It gets adjusted when looping through it's generated Elements
data class StructureState(
    var progress: Double = 0.0,
    var count: Int = 0,
    var R:Random = Random
)
