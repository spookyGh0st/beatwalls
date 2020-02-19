package structure.specialStrucures

import structure.FurryGrid
import structure.helperClasses.SpookyWall
import structure.add

// for now this is sufficiant, might want to add more in the future
fun FurryGrid.run(){
    var x = p1.x
    var y = p1.y
    var z = p1.z
    repeat(gridX) { itX ->
        repeat(gridY) { itY ->
            repeat(gridZ) { itZ ->
                val w =SpookyWall(startRow = x, duration = panelZ, width = panelX, height = panelY, startHeight = y, startTime = z)
                if((itZ+itY+itX)%2==0 || mode == 0)
                    add(w)
                z += panelZ
            }
            z = p1.z
            y += panelY
        }
        y = p1.y
        x += panelX
    }
    x = p1.x
}
