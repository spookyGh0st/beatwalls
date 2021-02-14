package structure.bwElements

import math.Quaternion
import math.Vec3

data class BwAnimation(
        var position:           List<Waypoint<Vec3>>?           = null,
        var definitePosition:   List<Waypoint<Vec3>>?           = null,
        var rotation:           List<Waypoint<Quaternion>>?     = null,
        var globalRotation:     List<Waypoint<Quaternion>>?     = null,
        var scale:              List<Waypoint<Vec3>>?           = null,
        var dissolve:           List<Waypoint<Double>>?         = null,
        var dissolveArrow:      List<Waypoint<Double>>?         = null,
        var color:              List<Waypoint<Color>>?          = null,
        var interactable:       List<Waypoint<Boolean>>?        = null,
)

