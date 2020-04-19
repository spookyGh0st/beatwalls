package compiler.property.constantFactory

import org.mariuszgromada.math.mxparser.Constant
import structure.helperClasses.SpookyWall

class ConstantController{
    var progress: Double  = Double.NaN
    set(value) {
        progressConstant.constantValue = progress
        field = value
    }

    var wall: SpookyWall? =null
    set(value) {
        if(wall == null)
            field = value
        else{
            wallConstants[0].constantValue = value?.startRow ?: 0.0
        }

    }



    private var progressConstant = Constant("progress",progress)

    private var wallConstants =     listOf(
        Constant("wallx=${wall?.startRow?: Double.NaN}"),
        Constant("wally=${wall?.startHeight?: Double.NaN}"),
        Constant("wallz=${wall?.startTime?: Double.NaN}"),
        Constant("wallwidth=${wall?.width?: Double.NaN}"),
        Constant("wallheight=${wall?.height?: Double.NaN}"),
        Constant("wallduration=${wall?.duration?: Double.NaN}"),
        Constant("wallrotation=${wall?.rotation?: Double.NaN}"),
        Constant("walllocalrotx=${wall?.localRotX?: Double.NaN}"),
        Constant("walllocalroty=${wall?.localRotY?: Double.NaN}"),
        Constant("walllocalrotz=${wall?.localRotZ?: Double.NaN}"),
        Constant("colorr=${wall?.color?.red?: Double.NaN}"),
        Constant("colorg=${wall?.color?.green?: Double.NaN}"),
        Constant("colorb=${wall?.color?.blue?: Double.NaN}")
    )



}
