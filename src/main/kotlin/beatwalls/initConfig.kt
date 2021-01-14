package beatwalls

import chart.song.Info
import interpreter.MapLoader
import java.io.File
import java.lang.StringBuilder


fun initialize(wd: File){
    val ml = try { MapLoader(wd) }catch (e:Exception){ return }
    val info = ml.loadInfo()

    val diff = pickDifficulty(info)
    val hjd = pickHjd()

    val modType = if (pickNe()) "NE" else "ME"
    val text = exampleMainFile(diff.first,hjd,modType)

    val mainFile = File(wd, "main.bw")
    logInfo("Creating example File under $mainFile")
    mainFile.writeText(text)
}

fun pickNe(): Boolean {
    println("Do you want to use Noodle Instead of Mapping Extensions? Noodle Extensions allows for way cooler stuff, but is not supported on Quest")
    print("(y/n) input: ")
    return (readLine()?:"y") in listOf("","y","Y","Yes","yes","true","True")
}

fun pickDifficulty(info: Info): Pair<String, Int> {
    val diffSet = info._difficultyBeatmapSets.map { it._beatmapCharacteristicName to it._difficultyBeatmaps }
    val names = diffSet.flatMap { it.second.map {  l -> l._beatmapFilename to (l._customData?._editorOffset ?: 0) } }
    for ((index, it) in names.withIndex()) {
        println("$index -> ${it.first}")
    }
    print("\nEnter the Number of the Difficult you want to work with:\ninput: ")
    val index = readLine()?.toIntOrNull()
    if (index == null || index !in 0..names.size)
        return pickDifficulty(info)
    return names[index]
}

fun pickHjd(): Double {
    println("Enter Half jump Duration (usually 2)")
    print("input: ")
    return readLine()?.toDoubleOrNull() ?: pickHjd()
}

private fun exampleMainFile(difficulty: String, hjd: Double, Modtype: String): String {
    val s = StringBuilder()
    s.appendLine("# The first Section are global Options.\n")
    s.appendLine("# This sets the Mod Support. Remember to also set the Requirements in the Info.dat")
    s.appendLine("modtype: $Modtype")
    s.appendLine("# This sets the target Difficulty.")
    s.appendLine("Difficulty: $difficulty")
    s.appendLine("# This sets the characteristic (The Tabs in a Map)")
    s.appendLine("Difficulty: Lightshow")
    s.appendLine("# This will tell Beatwalls to remove all existing Walls in the difficulty before creating new ones")
    s.appendLine("ClearWalls: true")
    s.appendLine("# This is the HJD. It allows Beatwalls to automatically time walls to the Beat")
    s.appendLine("halfJumpDuration: $hjd")
    s.append(standardText)
    return s.toString()
}

const val standardText = """
# This is an example File of a .bw file. 
# General Guide: https://github.com/spookyGh0st/beatwalls
# documentation: https://spookygh0st.github.io/beatwalls/structure/-wall-structure/index.html

# Lines starting with an # are Comments and will get ignored
# Syntax Beat:Name
# simple WallStructures of a single Wall
10: Wall
    StartRow: 2
    height: 4
    width: 2
    duration: 8
    mirror: 2
    
# Random Noise creates small particels in the given region
# docu: https://spookygh0st.github.io/beatwalls/structure/-random-noise/index.html
20.0: RandomNoise
    p1: -4,0,0
    p2: 4,4,8
    # we dont want to many, how about 40 walls
    amount: 40
    
#How about a curve at the same time as our randomNoise
# docu: https://spookygh0st.github.io/beatwalls/structure/-steady-curve/index.html
20.0: SteadyCurve
    p1: 2,0
    p2: 4,4
    p3: 2,4
    p4: 4,3
    
    # Stretch it out to 10 beats
    scale: 10
    # mirror it to the other side and to the top
    mirror: 8
    # we stretch the curve out over 8 beats, lets go with 6*8 walls
    amount: 48
    
    
# defining a wallstructures for multiple use
define: upDownCurve
    # what structure we are basing of
    structures: curve
    
    # the 4 controlPoints we are basing of. These are Points in 3d-space. 
    # Imagine a line between p1 and p4. The other 2 Points,  p2 and p3 are "pulling" on it
    p1: 0,4,0
    p2: 2,4,0.33
    p3: 3,2,0.66
    p4: 2,0,1
    
    # fit the startRow to 5. All Walls in the curve will get stretched in width until they reach that startRow
    fitStartRow: 10
    # same for startHeight. All walls will get stretched in height until they reach that startheight
    # we also Want to change the height to something random
    changeHeight: random(0,4)
    
    # mirrors the whole structure to the left side
    mirror: 2
    
30: upDownCurve
    # we repeat our structure 5 times, but only every second beat
    repeat: 5
    repeatAddZ: 2
    
31: upDownCurve
    # we reverse the structure, the rest is same as 30
    reverse: true
    repeat: 5
    repeatAddZ: 2
    
# Every Wallstructure below this will have changeDuration set to -3
0.0: default
    changeDuration: -3
    
# like in 30/31 but mirrored horizontally and with hyper walls
40: upDownCurve
    mirror: 3
    changeDuration: -3
    repeat: 5
    repeatAddZ: 2
    
41: upDownCurve
    mirror: 3
    changeDuration: -3
    reverse: true
    repeat: 5
    repeatAddZ: 2
    
# remember to reset changeDuration. This one is null, but the default for most is 0.0 or whatever makes sense
# consult the documentation for this.
0.0: default
    changeDuration: null
    """