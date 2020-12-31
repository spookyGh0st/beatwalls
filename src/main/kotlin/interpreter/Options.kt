package interpreter

data class Options(
    val modType: ModType = ModType.NE,
    val difficulty: DifficultyType = DifficultyType.Easy,
    val Characteristic: String = "Lightshow",
    val includes: List<String> = listOf(),
    val clearWalls: Boolean = true,
    val clearNotes: Boolean = false,
    val clearBombs: Boolean = false,
    val halfJumpDuration: Int = 2,
)

enum class ModType{
    ME,
    NE,
}

enum class DifficultyType(rank: Int) {
    Easy(1),
    Normal(3),
    Hard(5),
    Expert(7),
    ExpertPlus(9),
}

