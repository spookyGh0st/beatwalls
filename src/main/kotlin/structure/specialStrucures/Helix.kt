package structure.specialStrucures

import structure.Helix
import structure.add
import structure.circle

fun Helix.run(){
    add(circle(count = count, fineTuning = amount, heightOffset = center.y, radius = radius,startRotation = startRotation,rotationCount = rotationAmount,helix = true))
}