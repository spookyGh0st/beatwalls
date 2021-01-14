package structure.helperClasses

import structure.math.Vec3

data class BwObstacle(
    var scale: Vec3 = Vec3(0,0,0),
    override var beat: Double = 0.0,
    override var position: Vec3 = Vec3(0,0,0),
    override var rotation: Vec3 = Vec3(0,0,0),
    override var color: Color? = null,
    override var localRotation: Vec3 = Vec3(0,0,0),
    override var noteJumpMovementSpeed: Double? = null,
    override var noteJumpStartBeatOffset: Double? = null,
    override var fake: Boolean = false,
    override var interactable: Boolean = true,
    override var gravity: Boolean = true,
    override var track: String? = null,
): BwObject



