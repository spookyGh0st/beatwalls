package beatwalls

import chart.info.Info
import interpreter.MapLoader
import java.io.File
import java.lang.StringBuilder


fun initialize(wd: File){
    val ml = try { MapLoader(wd) }catch (e:Exception){ return }
    val info = ml.loadInfo()

    val diff = pickDifficulty(info)
    val hjd = pickHjd()

    val modType = if (pickNe()) "NE" else "ME"
    val diffName = diff.first.removeSuffix(".dat")
    val text = exampleMainFile(diffName,hjd,modType)

    val mainFile = File(wd, mainFileSuffix)
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
# Guide: https://spooky.moe/beatwalls

# Lines starting with an # are Comments and will get ignored

# -----------------------------------
# INTRO
# ----------------------------------

# We only use NE
# so we can use the Rectangletype for all curves and helix
interface Curve:
    type: Rectangle

RandomCurve:
    beat: 1
    duration: 16
    amount: 12*d
    p0: -8,6
    p1: -2,0
    mirror: 2
    type: Rectangle
    color: gradient(red,blue,red,cyan)
    scaleWidth: linear(4,1)


Curve:
    beat: 1
    scaleWidth: linear(8,0.5)
    duration: 16
    p0: 2,0
    p1: 0.5,0,
    p2: 4,0
    p3: 2,0
    mirror: 2
    color: gradient(blue,red,blue, cyan)


# -----------------------------------
#  First Chorus
# ----------------------------------

Line:
    beat: 17 
    p0: 0,0
    p1: 0,0
    duration: 48
    rotationY: random(-10,10) * (1-p)
    changeX: random(-0.2,0.2)* (1.5-p)
    changeWidth: random(0.5,2)* (1.2-p)
    color: orange


curve:
    beat: 17 + r*d
    repeat: 8
    duration: 4
    p0: 3,0
    p1: 4,2 
    p2: 3,0
    mirror: 8
    color: gradient(cyan, blue, white, blue, cyan)


# -----------------------------------
# Now we start Swinging!
# ----------------------------------

# Time for a more advanced Part
# We create 4 helix each with mirror 6
# and let them join in each other

interface helix:
    mirror: 6
    type: Rectangle
    beat: 49 + r*d
    repeat: 4
    duration: 4
    addZ: random(0, 0.01) # To avoid lag, we offset the spawn a bit

helix:
    radius: easeInOutQuad(3,9)
    color: gradient(white,cyan)
    rotationZ: easeInOutQuad(0,180)

helix:
    radius: easeInOutQuad(9,3)
    color: gradient(cyan,white)
    rotationZ: easeInOutQuad(180,0)

helix:
    radius: easeInOutQuad(6,12)
    color: gradient(cyan, blue)
    rotationamount: -360
    rotationZ: easeInOutQuad(0,-180)

helix:
    radius: easeInOutQuad(12,6)
    color: gradient(blue,cyan)
    rotationamount: -360
    rotationZ: easeInOutQuad(-180,0)

# Resetting the helix interface
interface helix:

    """