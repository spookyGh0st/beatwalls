package structures

import kotlin.math.*


/** A function to getWallList a circle of walls or a helix, probably should have splitted those up */
fun circle(
    count:Int = 1, //how many spirals
    radius:Double = 1.9, //how big
    fineTuning:Int = 10, //how many walls
    startRotation:Double = 0.0, //startRotation offset
    rotationCount:Double = 1.0, //how many rotations
    heightOffset:Double = 2.0, //height of the center
    speedChange: Double? = null, //speedChange, speed up or slowDown
    wallDuration:Double? = null, //the default duration
    helix:Boolean = false, //if its a helix or a circle
    reverse:Boolean = false //if its reversed
):ArrayList<Wall>{
    val list = arrayListOf<Wall>()
    val max = 2.0* PI *fineTuning*rotationCount

    var x: Double
    var y: Double
    var nX:Double
    var nY:Double

    var width: Double
    var height: Double
    var startRow: Double
    var startHeight: Double

    var startTime: Double
    var duration:Double

    for(o in 0 .. count){
        //the offset controls the starting point
        val offset = round((o*2.0* PI *fineTuning) /count) + startRotation/360*(2* PI)
        var lastStartTime = 0.0
        for (j in 0 until round(max).toInt()){
            val i = if(!reverse) j else (max-j).toInt()
            x = radius * cos((i+offset)/fineTuning)
            y = radius * sin((i+offset)/fineTuning)

            nX = radius * cos(((i+offset)+1)/fineTuning)
            nY = radius * sin(((i+offset)+1)/fineTuning)

            startRow = x + (nX - x)
            width = abs(nX -x ).coerceAtLeast(0.001)
            startHeight = y + heightOffset
            height = abs(nY-y).coerceAtLeast(0.001)

            //sets the duration to, 1: the given duration, 2: if its a helix the duration to the next wall 3: the defaultDuration: 1.0

            duration = wallDuration?: if(helix){
                if (speedChange==null){
                    1.0/max
                }else{
                    ((j+1)/max).pow(1.0/speedChange) - ((j)/max).pow(1.0/speedChange)
                }
            }else{
                1.0
            }
            val tempDuration =
                if (speedChange==null){
                    1.0/max
                }else{
                    ((j+1)/max).pow(1.0/speedChange) - ((j)/max).pow(1.0/speedChange)
                }

            //changes the startTime, and then saves it to lastStartTime
            startTime = if(helix) lastStartTime+tempDuration else 0.0
            lastStartTime = startTime

            //adds the Obstacle
            list.add(Wall(startRow, duration, width, height, startHeight, startTime))
        }
    }
    return list
}

/** Draws a line between 2 coordinates */
fun line(px1:Double, px2: Double, py1:Double, py2: Double, pz1: Double= 0.0, pz2: Double=0.0, defaultAmount: Int? = null, defaultDuration: Double? = null): ArrayList<Wall>{

    //swap values if y2 < y1  - this functions goes from bottom to top
    var x1 = px1
    var x2 = px2
    var y1 = py1
    var y2 = py2
    var z1 = pz1
    var z2 = pz2

    val a= abs(y2-y1)
    val c = sqrt(abs(x2-x1).pow(2) + abs(z2-z1).pow(2))
    val b = sqrt(a.pow(2) + c.pow(2))
    val dgr = asin(a/b)



    val amount = defaultAmount?:((cos(dgr)*sin(dgr)).pow(2)*200 +1).toInt()

    val list = arrayListOf<Wall>()

    if(z2<z1){
        x1 = x2.also { x2 = x1 }
        y1 = y2.also { y2 = y1 }
        z1 = z2.also { z2 = z1 }
    }

    //setting the solid values
    val w = (abs(x2-x1)/amount)
    val width = w
    val h = (abs(y2-y1)/amount)
    val height = h
    val d = (abs(z2-z1)/amount)
    val duration = d

    for(i in 0 until amount){
        //setting the dynamic values
        val startHeight =
            if(y2 > y1)
                y1 + i* h
            else
                y1 - (i+1) * h
        val startRow =
            if(x2 > x1)
                x1 + i * w
            else
                x1 - (i+1) * w
        val startTime = z1 + i*d

        //adding the obstacle
        val myD = defaultDuration ?: duration
        list.add(Wall(startRow, myD, width, height, startHeight, startTime))
    }
    return list
}


fun ArrayList<String>.getIntOrElse(index: Int, defaultValue: Int):Int =
    try { this[index].toInt() } catch (e:Exception){ defaultValue }

fun ArrayList<String>.getDoubleOrElse(index: Int, defaultValue: Double): Double =
    try { this[index].toDouble() } catch (e:Exception){ defaultValue }

fun ArrayList<String>.getBooleanOrElse(index: Int, defaultValue: Boolean): Boolean =
    try {
        this[index].toInt() == 1
    } catch (e:Exception){ defaultValue }

