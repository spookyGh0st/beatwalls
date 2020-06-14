package interpreter.property.variables

import structure.helperClasses.SpookyWall

val wallVariables: HashMap<String,(wall: SpookyWall)->Double?> = hashMapOf(
    "wallx" to { ws: SpookyWall -> ws.x },
    "wally" to { ws: SpookyWall -> ws.y },
    "wallz" to { ws: SpookyWall -> ws.z },
    "wallwidth" to { ws: SpookyWall -> ws.width },
    "wallheight" to { ws: SpookyWall -> ws.height },
    "wallduration" to { ws: SpookyWall -> ws.duration },
    "wallcolorr" to { ws: SpookyWall -> ws.color?.red },
    "wallcolorg" to { ws: SpookyWall -> ws.color?.green },
    "wallcolorb" to { ws: SpookyWall -> ws.color?.red },
    "wallrotationx" to { ws: SpookyWall -> ws.rotationX },
    "wallrotationy" to { ws: SpookyWall -> ws.rotationY },
    "wallrotationz" to { ws: SpookyWall -> ws.rotationZ },
    "walllocalrotx" to { ws: SpookyWall -> ws.localRotX },
    "walllocalroty" to { ws: SpookyWall -> ws.localRotY },
    "walllocalrotz" to { ws: SpookyWall -> ws.localRotZ }
)
