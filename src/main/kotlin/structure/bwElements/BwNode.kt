package structure.bwElements

import math.Quaternion
import math.Vec3

interface BwNode {
    var rotation: Quaternion
    var scale: Vec3
    var translation: Vec3

}