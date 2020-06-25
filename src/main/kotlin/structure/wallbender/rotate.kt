package structure.wallbender

import chart.difficulty._objectAnimation
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
        if (wall.animation == null && ( this.animationPos != null || this.animationRotation != null || this.animationScale != null || this.animationLocalRot != null))
            wall.animation = _objectAnimation()
        if (this.animationPos!= null) wall.animation?._position = animationPos
        if (this.animationRotation!= null) wall.animation?._rotation = animationRotation
        if (this.animationLocalRot!= null) wall.animation?._localRotation = animationLocalRot
        if (this.animationDefPos!= null) wall.animation?._definitePosition = animationDefPos
        if (this.animationScale!= null) wall.animation?._scale = animationScale
        if (this.animationDissolve!= null) wall.animation?._dissolve = animationDissolve
        if (this.animationDissolveArrow!= null) wall.animation?._dissolve = animationDissolveArrow
    }
}
