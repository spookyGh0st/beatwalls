package structure.specialStrucures

import structure.FurryGrid
import structure.helperClasses.SpookyWall

// for now this is sufficiant, might want to add more in the future
fun FurryGrid.run(): List<SpookyWall> {
    val l = mutableListOf<SpookyWall>()
    var x = p1.x
    var y = p1.y
    var z = p1.z
    repeat(gridX) { itX ->
        repeat(gridY) { itY ->
            repeat(gridZ) { itZ ->
                val w =SpookyWall(x = x, duration = panelZ, width = panelX, height = panelY, y = y, z = z)
                if((itZ+itY+itX)%2==0 || mode == 0)
                    l.add(w)
                z += panelZ
            }
            z = p1.z
            y += panelY
        }
        y = p1.y
        x += panelX
    }
    x = p1.x
    return l.toList()
}
