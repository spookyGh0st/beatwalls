package structure.helperClasses

data class Vec3(val x: Double, val y: Double, val z: Double){
    constructor(x: Number, y: Number, z: Number): this(x.toDouble(), y.toDouble(), z.toDouble())
}

interface BwElement

interface BwObject: BwElement{
    var beat: Double
    var position: Vec3?
    var rotation: Vec3?
    var localRotation: Vec3?
    var color: Color
    var noteJumpMovementSpeed: Double?
    var noteJumpStartBeatOffset: Double?
    var fake: Boolean
    var interactable: Boolean
    var gravity: Boolean
}

data class BwObstacle(
    var scale: Vec3,
    override var beat: Double,
    override var position: Vec3?,
    override var rotation: Vec3?,
    override var color: Color,
    override var localRotation: Vec3?,
    override var noteJumpMovementSpeed: Double?,
    override var noteJumpStartBeatOffset: Double?,
    override var fake: Boolean,
    override var interactable: Boolean,
    override var gravity: Boolean,
): BwObject

data class BwNote(
    var cutDirection: Double,
    var type: NoteType,
    var flipLineIndex: Double,
    var flipJump: Double,
    override var color: Color,
    override var beat: Double,
    override var position: Vec3?,
    override var rotation: Vec3?,
    override var localRotation: Vec3?,
    override var noteJumpMovementSpeed: Double?,
    override var noteJumpStartBeatOffset: Double?,
    override var fake: Boolean,
    override var interactable: Boolean,
    override var gravity: Boolean,
): BwObject

enum class NoteType(type: Int){
    Red(1),
    Blue(2),
    Bomb(3),
}

data class BwEvent(
    var beat: Double,
    var type: Int,
    var value: Int,
    var propID: Int,
    var lightID: Int
): BwElement
