package types

import structure.bwElements.Color
import java.awt.Color.*

internal val black      = Color(BLACK)
internal val blue       = Color(BLUE)
internal val cyan       = Color(CYAN)
internal val darkgray   = Color(DARK_GRAY)
internal val gray       = Color(GRAY)
internal val green      = Color(GREEN)
internal val lightgray  = Color(LIGHT_GRAY)
internal val magenta    = Color(MAGENTA)
internal val orange     = Color(ORANGE)
internal val pink       = Color(PINK)
internal val red        = Color(RED)
internal val white      = Color(WHITE)
internal val yellow     = Color(YELLOW)

fun baseColors() = mutableMapOf(
    "black"      to black    ,
    "blue"       to blue     ,
    "cyan"       to cyan     ,
    "darkgray"   to darkgray ,
    "gray"       to gray     ,
    "green"      to green    ,
    "lightgray"  to lightgray,
    "magenta"    to magenta  ,
    "orange"     to orange   ,
    "pink"       to pink     ,
    "red"        to red      ,
    "white"      to white    ,
    "yellow"     to yellow   ,
)