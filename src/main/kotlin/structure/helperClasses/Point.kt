package structure.helperClasses

import java.io.Serializable

data class Point(val x:Double, val y:Double, val z:Double):Serializable {
    constructor(x:Number,y:Number,z:Number):this(x.toDouble(),y.toDouble(),z.toDouble())
    fun mirrored(other: Point): Point {
        return Point(
            this.x - (other.x - this.x),
            this.y - (other.y - this.y),
            this.z - (other.z - this.z)
        )
    }
    fun mirroredNoZ(other: Point): Point {
        return Point(
            this.x - (other.x - this.x),
            this.y - (other.y - this.y),
            other.z
        )
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun toString(): String {
        return "Point(x=$x, y=$y, z=$z)"
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }
}

