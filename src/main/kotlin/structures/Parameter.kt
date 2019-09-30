package structures

import com.google.gson.annotations.SerializedName
import java.io.File

class Parameter :CommandParser() {
    val beat by Double(0.0)
    val fast by Boolean()
    val hyper by Boolean()
    val mirror by Boolean()
    val verticalMirror by Boolean(short = false)
    val horizontalMirror by Boolean(short = false)
    val scale by Double(1.0)
    val verticalScale by Double(1.0)
    val grounder by Double(0.0)
    val extender by Double(1.0)
    val structureList by WallStructureList()

    inner class Command(
        @SerializedName("beat")
        val beatStartTime:kotlin.Double = beat?:0.0,
        @SerializedName("command")
        val command:kotlin.String = options.joinToString(" " ).prependIndent("/bw ")
    )
}

