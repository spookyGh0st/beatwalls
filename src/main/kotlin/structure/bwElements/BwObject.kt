package structure.bwElements

import structure.math.Vec3

interface BwObject: BwElement {
    var rotation: Vec3
    var scale: Vec3
    var translation: Vec3
    var globalRotation: Vec3
    var color: Color?
    var noteJumpMovementSpeed: Double?
    var noteJumpStartBeatOffset: Double?
    var fake: Boolean
    var interactable: Boolean
    var gravity: Boolean
    var track: String?
}
