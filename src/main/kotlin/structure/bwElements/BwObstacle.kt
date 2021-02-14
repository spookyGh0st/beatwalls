package structure.bwElements

import math.PointConnectionType
import math.Quaternion
import math.Vec3

data class BwObstacle(
    // Beatwizard differents between duration an the scale
    override var beat: Double                       = 0.0,
    override var duration: Double?                  = null,
    override var rotation: Quaternion               = Quaternion(),
    override var scale: Vec3                        = Vec3(),
    override var translation: Vec3                  = Vec3(),
    override var color: Color?                      = null,
    override var globalRotation: Quaternion         = Quaternion(),
    override var noteJumpMovementSpeed: Double?     = null,
    override var noteJumpStartBeatOffset: Double?   = null,
    override var fake: Boolean                      = false,
    override var interactable: Boolean              = true,
    override var gravity: Boolean                   = true,
    override var track: String?                     = null,
    override var animation: BwAnimation?            = null,
): BwObject{
    constructor(v0: Vec3, v1: Vec3, type: PointConnectionType): this(){
        setTo(v0,v1,type)
    }
}



