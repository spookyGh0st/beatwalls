package structure.wallbender

import structure.WallStructure
import structure.helperClasses.SpookyWall

fun WallStructure.rotate(l: List<SpookyWall>) {
    for ((i, wall) in l.withIndex()) {
        this.activeWall = wall
        this.i = i.toDouble() / l.size
        wall.rotationX = this.rotationX
        wall.rotationY = this.rotationY
        wall.rotationZ = this.rotationZ
        wall.localRotX = this.localRotX
        wall.localRotY = this.localRotY
        wall.localRotZ = this.localRotZ
    }
}

fun WallStructure.extraStuff(l: List<SpookyWall>) {
    for ((i, wall) in l.withIndex()) {
        this.activeWall = wall
        this.i = i.toDouble() / l.size
        if (color != null) wall.color = color
        if (track != null) wall.track = track
        if (noteJumpMovementSpeed != null) wall.noteJumpStartBeat = noteJumpMovementSpeed
        if (noteJumpMovementSpeedOffset != null) wall.noteJumpStartBeatOffset = noteJumpMovementSpeedOffset
        if (bombs) wall.bomb = bombs
    }
}
