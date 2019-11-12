package structure

//import com.graphbuilder.curve.*
import java.io.Serializable
import kotlin.math.*
import kotlin.random.Random


/** A function to getSpookyWallList a circle of walls or a helix, probably should have splitted those up */
fun circle(
    count:Int = 1, //how many spirals
    radius:Double = 1.9, //how big
    fineTuning:Int = 10, //how many walls
    startRotation:Double = 0.0, //startRotation offset
    rotationCount:Double = 1.0, //how many rotations
    heightOffset:Double = 2.0, //height of the center
    startRowOffset: Double = 0.0,
    speedChange: Double? = null, //speedChange, speed up or slowDown
    wallDuration:Double? = null, //the default duration
    helix:Boolean = false, //if its a helix or a circle
    reverse:Boolean = false //if its reversed
):ArrayList<SpookyWall>{
    val list = arrayListOf<SpookyWall>()
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

            startRow = x + (nX - x) + startRowOffset
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
            list.add(SpookyWall(startRow, duration, width, height, startHeight, startTime))
        }
    }
    return list
}

/** Draws a line between 2 coordinates */
fun line(px1:Double, py1:Double, pz1: Double= 0.0, px2: Double, py2: Double, pz2: Double=0.0, defaultAmount: Int? = null, defaultDuration: Double? = null): ArrayList<SpookyWall>{
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



    val amount = defaultAmount?:((cos(dgr)*sin(dgr)).pow(1.5)*50 +1).toInt()

    val list = arrayListOf<SpookyWall>()

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
        list.add(SpookyWall(startRow, myD, width, height, startHeight, startTime))
    }
    return list
}
fun line(p0:Point, p1: Point, amount: Int?): ArrayList<SpookyWall> {
    return line(p0.x,p0.y,p0.z,p1.x,p1.y,p1.z,amount)
}
fun line(p0:Triple<Double,Double,Double>, p1: Triple<Double,Double,Double>,amount: Int?): ArrayList<SpookyWall> {
    return line(p0.first,p0.second,p0.third,p1.first,p1.first,p1.first,amount)
}

fun curve(startPoint: Point,p1: Point,p2: Point, endPoint: Point, amount: Int):ArrayList<SpookyWall>{
    val list = arrayListOf<SpookyWall>()
    repeat(amount){
        val currentPoint = quadraticBezier(startPoint, p1, p2, endPoint, it.toDouble() / amount)
        val nextPoint = quadraticBezier(startPoint, p1, p2, endPoint, (it + 1.0) / amount)
        val startRow = currentPoint.x
        val startHeight = currentPoint.y
        val startTime = min(currentPoint.z, nextPoint.z)
        val width = nextPoint.x - currentPoint.x
        val height = nextPoint.y - currentPoint.y
        val duration = abs(nextPoint.z -currentPoint.z)
        list.add(SpookyWall(startRow, duration, width, height, startHeight, startTime))
    }
    return list
}

fun getBoxList(wallAmountPerWall: Int): ArrayList<SpookyWall> {
    val allWalls= arrayListOf<SpookyWall>()
    for ( i in 0 until wallAmountPerWall*2){
        val startX = 8 * i.toDouble() / (wallAmountPerWall) /2 - 4
        //bottom
        allWalls.add(SpookyWall(startX, 1.0 / wallAmountPerWall, 4.0 / wallAmountPerWall, 0.0, 0.0, 0.0))
        //top
        allWalls.add(
            SpookyWall(
                startX,
                1.0 / wallAmountPerWall,
                4.0 / wallAmountPerWall,
                4.0 / wallAmountPerWall,
                4.0,
                0.0
            )
        )
    }
    for ( i in 0 until wallAmountPerWall){
        val startY = 4 * i.toDouble() / (wallAmountPerWall)
        //left
        allWalls.add(
            SpookyWall(
                4.0,
                1.0 / wallAmountPerWall,
                4.0 / wallAmountPerWall,
                4.0 / wallAmountPerWall,
                startY,
                0.0
            )
        )
        //right
        allWalls.add(
            SpookyWall(
                -4.0,
                1.0 / wallAmountPerWall,
                -4.0 / wallAmountPerWall,
                4.0 / wallAmountPerWall,
                startY,
                0.0
            )
        )
    }
    return allWalls

}
fun randomPoint() =
    Point(Random.nextDouble(-4.0, 4.0), Random.nextDouble(-2.0, 2.0), Random.nextDouble())

fun quadraticBezier(p0: Point, p1: Point, p2: Point, p3: Point, t:Double): Point {
    val x =(1-t).pow(3)*p0.x +
            (1-t).pow(2)*3*t*p1.x +
            (1-t)*3*t*t*p2.x +
            t*t*t*p3.x
    val y =(1-t).pow(3)*p0.y +
            (1-t).pow(2)*3*t*p1.y +
            (1-t)*3*t*t*p2.y +
            t*t*t*p3.y
    val z =(1-t).pow(3)*p0.z +
            (1-t).pow(2)*3*t*p1.z +
            (1-t)*3*t*t*p2.z +
            t*t*t*p3.z
    return Point(x, y, z)
}

data class Point(val x:Double, val y:Double, val z:Double):Serializable {
    constructor(x:Int,y:Int,z:Int):this(x.toDouble(),y.toDouble(),z.toDouble())
    fun mirrored(other: Point): Point {
        return Point(this.x-(other.x-this.x),this.y-(other.y-this.y),this.z-(other.z-this.z))
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    override fun toString(): String {
        return "Point(x=$x, y=$y, z=$z)"
    }
}

fun ArrayList<String>.getIntOrElse(index: Int, defaultValue: Int):Int =
    try { this[index].toInt() } catch (e:Exception){ defaultValue }

fun ArrayList<String>.getDoubleOrElse(index: Int, defaultValue: Double): Double =
    try { this[index].toDouble() } catch (e:Exception){ defaultValue }

fun ArrayList<String>.getBooleanOrElse(index: Int, defaultValue: Boolean): Boolean =
    try {
        this[index].toInt() == 1
    } catch (e:Exception){ defaultValue }

fun Triple<Double,Double,Double>.toPoint() = Point(first,second,third)