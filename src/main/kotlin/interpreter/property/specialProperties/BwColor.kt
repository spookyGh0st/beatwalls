package interpreter.property.specialProperties

import interpreter.property.BwProperty
import interpreter.property.strPlusExprStr
import interpreter.property.strPowExprStr
import interpreter.property.strTimesExprStr
import net.objecthunter.exp4j.function.Function
import structure.WallStructure
import structure.helperClasses.Color
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.reflect.KProperty

class BwColor(var red: String = "0.0", var green: String="0.0", var blue: String="0.0", var isNull: Boolean = true): BwProperty() {
    constructor(x:Number,y:Number,z:Number): this(x.toString(),y.toString(),z.toString())
    val redExpr by lazy { buildColorExpr(BaseColor.RED,red) }
    val greenExpr by lazy { buildColorExpr(BaseColor.GREEN,green) }
    val blueExpr by lazy { buildColorExpr(BaseColor.BLUE,blue) }

    // adds the custom color Functions to the function list and then build the new Expression
    fun buildColorExpr(color: BaseColor, default: String) =
        prepareExpression(default)
            .function(Gradient(color){wsRef?.i?:0.0})
            .function(Rainbow(color){wsRef?.i?:0.0})
            .functions(flashFunctions(color))
            .build()

    fun flashFunctions(baseColor: BaseColor): MutableList<Function> =
        (0..10).map { Flash(baseColor,it) }.toMutableList()

    override fun getValue(thisRef: WallStructure, property: KProperty<*>): Color? {
        if(isNull) return null
        // throws an exception if one Expression is not valid
        wsRef = thisRef // this is needed so we pass the right ws in the functions
        return Color(
            setVarsAndCalcExprForWs(redExpr,thisRef).roundToInt(),
            setVarsAndCalcExprForWs(greenExpr,thisRef).roundToInt(),
            setVarsAndCalcExprForWs(blueExpr,thisRef).roundToInt()
        )
    }

    override fun setExpr(e: String) {
        isNull = false
        // replaces the colors and splits at ','
        val el = e
            .replaceColors()
            .split(Regex(",(?![^(]*\\))"))

        red = el.getOrNull(0)?:red
        green = el.getOrNull(1)?:green
        blue = el.getOrNull(2)?:blue
    }

    fun parseExpression(e: String,f: (String,String)->String) {
        isNull = false
        val el = e.split(Regex(",(?![^(]*\\))"))
        when(el.size){
            1 -> {
                red = f(red,el[0])
                green = f(green, el[0])
                blue = f(blue, el[0])
            }
            3 -> {
                red = f(red,el[0])
                green = f(green, el[1])
                blue = f(blue, el[2])
            }
            else -> throw Exception("Expression $e is invalid, should have one or three parameter")
        }
    }


    override fun plusExpr(e: String) {
        parseExpression(e.replaceColors()) { s: String, s2: String -> strPlusExprStr(s,s2) }
    }
    override fun timesExpr(e: String) {
        parseExpression(e.replaceColors()) { s: String, s2: String -> strTimesExprStr(s,s2) }
    }

    override fun powExpr(e: String) {
        parseExpression(e.replaceColors()) { s: String, s2: String -> strPowExprStr(s,s2) }
    }
}


enum class BaseColor(name: String){
    RED("red"),
    GREEN("green"),
    BLUE("blue")
}

class Gradient(val baseColor: BaseColor, val i: ()-> Double): Function("gradient",6) {
    override fun apply(vararg args: Double): Double =
        when(baseColor) {
            BaseColor.RED -> calc(args,0)
            BaseColor.GREEN -> calc(args,1)
            BaseColor.BLUE -> calc(args,2)
        }
    fun calc(args: DoubleArray, index: Int)=
        args[index]+i()*(args[index+3]-args[index])
}

class Rainbow(val baseColor: BaseColor, val i: () -> Double): Function("rainbow",1){
    override fun apply(vararg args: Double): Double {
        val i = i() * 2 * PI * args[0]
        return when (baseColor) {
            BaseColor.RED -> sin(i + 0.0 / 3.0 * PI) / 2 + 0.5
            BaseColor.GREEN -> sin(i + 2.0 / 3.0 * PI) / 2 + 0.5
            BaseColor.BLUE -> sin(i + 4.0 / 3.0 * PI) / 2 + 0.5
        }
    }
}

class Flash(val baseColor: BaseColor, val colorAmount: Int): Function("flash$colorAmount",colorAmount*3){
    var counter = 0
    override fun apply(vararg args: Double): Double =
        when(baseColor) {
            BaseColor.RED -> calc(args,0)
            BaseColor.GREEN -> calc(args,1)
            BaseColor.BLUE -> calc(args,2)
        }
    fun calc(args: DoubleArray, index: Int): Double {
        val a = (counter + index)%(colorAmount*3)
        counter +=3
        return args[a]
    }
}

internal fun String.replaceColors(): String =
    this.replace("black", Color(java.awt.Color.BLACK).toString())
        .replace("blue", Color(java.awt.Color.blue).toString())
        .replace("cyan", Color(java.awt.Color.cyan).toString())
        .replace("darkgray", Color(java.awt.Color.darkGray).toString())
        .replace("gray", Color(java.awt.Color.gray).toString())
        .replace("green", Color(java.awt.Color.green).toString())
        .replace("lightgray", Color(java.awt.Color.lightGray).toString())
        .replace("magenta", Color(java.awt.Color.magenta).toString())
        .replace("orange", Color(java.awt.Color.orange).toString())
        .replace("pink", Color(java.awt.Color.pink).toString())
        .replace("red", Color(java.awt.Color.red).toString())
        .replace("white", Color(java.awt.Color.white).toString())
        .replace("yellow"  , Color(java.awt.Color.yellow ).toString())
