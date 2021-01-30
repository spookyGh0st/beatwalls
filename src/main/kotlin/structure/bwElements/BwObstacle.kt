package structure.bwElements

import structure.math.Vec3

data class BwObstacle(
    // Beatwizard differents between duration an the scale
    override var beat: Double = 0.0,
    override var duration: Double? = null,
    override var rotation: Vec3 = Vec3(0, 0, 0),
    override var scale: Vec3 = Vec3(0, 0, 0),
    override var translation: Vec3 = Vec3(0, 0, 0),
    override var color: Color? = null,
    override var globalRotation: Vec3 = Vec3(0, 0, 0),
    override var noteJumpMovementSpeed: Double? = null,
    override var noteJumpStartBeatOffset: Double? = null,
    override var fake: Boolean = false,
    override var interactable: Boolean = true,
    override var gravity: Boolean = true,
    override var track: String? = null,
): BwObject



