package structure.bwElements

import structure.math.Vec3

data class BwNote(
    var cutDirection: Double,
    var type: NoteType,
    var flipLineIndex: Double,
    var flipJump: Double,
    override var color: Color?,
    override var beat: Double,
    override var translation: Vec3,
    override var globalRotation: Vec3,
    override var rotation: Vec3,
    override var noteJumpMovementSpeed: Double?,
    override var noteJumpStartBeatOffset: Double?,
    override var fake: Boolean,
    override var interactable: Boolean,
    override var gravity: Boolean,
    override var track: String?,
    override var scale: Vec3,
    override var duration: Double? = null
): BwObject
