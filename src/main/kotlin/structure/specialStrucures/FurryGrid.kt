package structure.specialStrucures

import structure.FurryGrid
import structure.helperClasses.SpookyWall
import structure.add

fun FurryGrid.run(){
    var x = p1.x
    var y = p1.y
    var z = p1.z
    repeat(gridX) {
        repeat(gridY) {
            repeat(gridZ) {
                add(
                    SpookyWall(
                        startRow = x,
                        duration = panelZ,
                        width = panelX,
                        height = panelY,
                        startHeight = y,
                        startTime = z
                    )
                )
                z += panelZ
            }
            z = p1.z
            add(
                SpookyWall(
                    startRow = x,
                    duration = panelZ,
                    width = panelX,
                    height = panelY,
                    startHeight = y,
                    startTime = z
                )
            )
            y += panelY
        }
        y = p1.y
        add(
            SpookyWall(
                startRow = x,
                duration = panelZ,
                width = panelX,
                height = panelY,
                startHeight = y,
                startTime = z
            )
        )
        x += panelX
    }
    x = p1.x
}
