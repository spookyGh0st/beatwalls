package structure.wallStructures

/**
 * Define your own WallStructure from existing WallStructures.
 */
@Deprecated("No longer used, use Custom Wall Structure instead")
class Define: WallStructure() {
    /**
     * the name the structure gets saved to
     */
    var name: String = "customStructure"
    /**
     * The name of Different Structures. Separated by comma (example: structures: Floor, Ceiling)
     * You can also define Parameters of the first Structure
     * These get loaded in Order, So if your reference defined Structures, those must be listed before that
     * The Beat Value gets every time, so it should be 0 most of the time
     */
    var structures: List<WallStructure> = listOf()

    /**
     * dont touch
     */
    var isTopLevel = false

    /**
     * generating the Walls
     */
    override fun createWalls() = TODO("Remove")

    override fun name(): String {
        if (isTopLevel)
            return structures.first().name()
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Define

        if (name != other.name) return false
        if (structures != other.structures) return false
        if (isTopLevel != other.isTopLevel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + structures.hashCode()
        result = 31 * result + isTopLevel.hashCode()
        return result
    }


}