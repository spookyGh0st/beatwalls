package compiler.property.constantFactory

import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.WallStructure
import structure.helperClasses.SpookyWall

class ConstantController(private val ws :WallStructure) {

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
                wallConstants[0].constantValue = value?.x ?: 0.0
                wallConstants[1].constantValue = value?.y ?: 0.0
                wallConstants[2].constantValue = value?.y ?: 0.0
                wallConstants[3].constantValue = value?.width ?: 0.0
                wallConstants[4].constantValue = value?.height ?: 0.0
                wallConstants[5].constantValue = value?.duration ?: 0.0
                wallConstants[6].constantValue = value?.rotation ?: 0.0
                wallConstants[7].constantValue = value?.localRotX ?: 0.0
                wallConstants[8].constantValue = value?.localRotY ?: 0.0
                wallConstants[9].constantValue = value?.localRotZ ?: 0.0
                wallConstants[10].constantValue = value?.color?.red ?: 0.0
                wallConstants[11].constantValue = value?.color?.green ?: 0.0
                wallConstants[12].constantValue = value?.color?.blue ?: 0.0
            }
        }



    val structureConstants: List<Constant>
        get() {
            return getWsConstants(ws)
        }

    val customConstants = mutableListOf<Constant>()

    val easingFunctions by lazy {  getEasingFunctions(this) }

    val customFunctions = mutableListOf<Function>()

    val progressConstant = Constant("progress",progress)

    val wallConstants = listOf(
        Constant("wallx=${wall?.x?: Double.NaN}"),
        Constant("wally=${wall?.y?: Double.NaN}"),
        Constant("wallz=${wall?.z?: Double.NaN}"),
        Constant("wallwidth=${wall?.width?: Double.NaN}"),
        Constant("wallheight=${wall?.height?: Double.NaN}"),
        Constant("wallduration=${wall?.duration?: Double.NaN}"),
        Constant("wallrotation=${wall?.rotation?: Double.NaN}"),
        Constant("walllocalrotx=${wall?.localRotX?: Double.NaN}"),
        Constant("walllocalroty=${wall?.localRotY?: Double.NaN}"),
        Constant("walllocalrotz=${wall?.localRotZ?: Double.NaN}"),
        Constant("wallcolorr=${wall?.color?.red?: Double.NaN}"),
        Constant("wallcolorg=${wall?.color?.green?: Double.NaN}"),
        Constant("wallcolorb=${wall?.color?.blue?: Double.NaN}")
    )
}



