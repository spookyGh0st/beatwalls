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
        if (!track.isNullOrBlank()) wall.track = track
        if (noteJumpMovementSpeed != null) wall.noteJumpStartBeat = noteJumpMovementSpeed
        if (noteJumpMovementSpeedOffset != null) wall.noteJumpStartBeatOffset = noteJumpMovementSpeedOffset
        if (bombs) wall.bomb = bombs
        if (wall.animation == null && ( this.animationPos != null || this.animationRotation != null || this.animationScale != null || this.animationLocalRot != null))
            wall.animation = _objectAnimation()
        if (!this.animationPos.isNullOrEmpty()) wall.animation?._position = animationPos
        if (!this.animationRotation.isNullOrEmpty()) wall.animation?._rotation = animationRotation
        if (!this.animationLocalRot.isNullOrEmpty()) wall.animation?._localRotation = animationLocalRot
        if (!this.animationDefPos.isNullOrEmpty()) wall.animation?._definitePosition = animationDefPos
        if (!this.animationScale.isNullOrEmpty()) wall.animation?._scale = animationScale
        if (!this.animationDissolve.isNullOrEmpty()) wall.animation?._dissolve = animationDissolve
        if (!this.animationDissolveArrow.isNullOrEmpty()) wall.animation?._dissolve = animationDissolveArrow
    }
}
