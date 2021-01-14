package interpreter

import java.io.File

data class Options(
    val workingDir: File,
    var modType: ModType = ModType.NE,
    var difficulty: DifficultyType = DifficultyType.Easy,
    var Characteristic: String = "Standard",
    var halfJumpDuration: Double = 2.0,
    var offset: Double = 0.0,
    var bpm: Double = 0.0,
){
    enum class ModType{
        ME,
        NE,
    }

    enum class DifficultyType(val rank: Int) {
        Easy(1),
        Normal(3),
        Hard(5),
        Expert(7),
        ExpertPlus(9)
    }
}
