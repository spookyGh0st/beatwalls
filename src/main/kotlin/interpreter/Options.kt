package interpreter

import beatwalls.readWorkingDirectory
import java.io.File

data class Options(val workingDir: File,
    var modType: ModType = ModType.NE,
    var difficulty: DifficultyType = DifficultyType.Easy,
    var Characteristic: String = "Lightshow",
    var clearWalls: Boolean = true,
    var clearNotes: Boolean = false,
    var clearBombs: Boolean = false,
    var halfJumpDuration: Double = 2.0,
){
    enum class ModType{
        ME,
        NE,
    }

    enum class DifficultyType(rank: Int) {
        Easy(1),
        Normal(3),
        Hard(5),
        Expert(7),
        ExpertPlus(9)
    }
}
