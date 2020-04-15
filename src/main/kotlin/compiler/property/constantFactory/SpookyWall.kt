package compiler.property.constantFactory

import org.mariuszgromada.math.mxparser.Constant
import structure.helperClasses.SpookyWall

fun getSwConstants(sw: SpookyWall) =
    listOf(
        Constant("wallx=${sw.startRow}"),
        Constant("wally=${sw.startHeight}"),
        Constant("wallz=${sw.startTime}"),
        Constant("wallwidth=${sw.width}"),
        Constant("wallheight=${sw.height}"),
        Constant("wallduration=${sw.duration}"),
        Constant("wallrotation=${sw.rotation}"),
        Constant("walllocalrotx=${sw.localRotation[0]}"),
        Constant("walllocalroty=${sw.localRotation[1]}"),
        Constant("walllocalrotz=${sw.localRotation[2]}"),
        Constant("colorr=${sw.color?.red}"),
        Constant("colorg=${sw.color?.green}"),
        Constant("colorb=${sw.color?.blue}")
    )
