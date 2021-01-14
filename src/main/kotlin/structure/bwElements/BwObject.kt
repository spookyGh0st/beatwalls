package structure.bwElements

import structure.math.Vec3

interface BwObject: BwElement {
    var position: Vec3
    var rotation: Vec3
    var localRotation: Vec3
    var color: Color?
    var noteJumpMovementSpeed: Double?
    var noteJumpStartBeatOffset: Double?
    var fake: Boolean
    var interactable: Boolean
    var gravity: Boolean
    var track: String?
}
