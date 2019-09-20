package structures

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class Parameters() {
    var name:String=""
    var customParameters: ArrayList<String> = arrayListOf()
    var scale:Double = 1.0
    var repeatCount: Int=0
    var repeatGap: Double= 1.0
    var startRow:Double=0.0
    var duration: Double=0.0
    var width: Double= 0.0
    var wallHeight:Double=0.0
    var wallStartHeight:Double=0.0
    var startTime: Double= 0.0
    private var stringArray = listOf<String>()
    var innerParameter:Parameters? = null

    /** constructor with a string, aka bookmark */
    constructor(commandText: String) : this() {
        stringArray = commandText.split(" ")
        val c = Counter(stringArray)
        name = c.getElement()
        if (name == "")
            throw (Exception("Failed to read Name, pls use /bw name"))
        c.countUp()
        if (c.getElement() == "--") {
            c.countUp()
            try {
                while (c.getElement() != "--") {
                    customParameters.add(c.getElement())
                    c.countUp()
                }
            } catch (e: Exception) {
                logger.error { "CLOSE YOUR FUCKING CUSTOM OPTION" }
            }
            c.countUp()
        }
        if ( stringArray.contains("..")){
            val subText=stringArray.slice(stringArray.indexOfFirst { it == ".." } +1 until stringArray.indexOfLast { it == ".." })
            if (stringArray.indexOfFirst { it == ".." } < stringArray.indexOfFirst { it == "--" })
                throw Exception("the syntax is /bw -- \$specialParameters -- .. \$innerParameters ..")
            if (subText.isNotEmpty()){
                innerParameter = Parameters(subText.joinToString( " " ))
                repeat(subText.size +3 ) { c.countUp() }
            }
        }
        scale = c.getOr1()
        repeatCount = c.getOr0().toInt()
        repeatGap = c.getOr1()
        startRow = c.getOr0()
        duration = c.getOr0()
        width = c.getOr0()
        wallHeight = c.getOr0()
        wallStartHeight = c.getOr0()
        startTime = c.getOr0()
    }

    constructor(
        name:String,
        customParameters: ArrayList<String> = arrayListOf(),
        scale:Double=1.0,
        repeatCount: Int=0,
        repeatGap: Double= 1.0,
        startRow:Double=0.0,
        duration: Double=0.0,
        width: Double= 0.0,
        wallHeight:Double=0.0,
        wallStartHeight:Double=0.0,
        startTime: Double= 0.0,
        innerParameter: Parameters? = null
    ): this(){
        this.name=name
        this.customParameters=customParameters
        this.scale=scale
        this.repeatCount=repeatCount
        this.repeatGap=repeatGap
        this.startRow=startRow
        this.duration=duration
        this.width=width
        this.wallHeight=wallHeight
        this.wallStartHeight=wallStartHeight
        this.startTime=startTime
        this.innerParameter=innerParameter
    }



    override fun hashCode(): Int =
        (scale + repeatGap + repeatCount + wallStartHeight +wallHeight +duration +startRow + width ).toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Parameters

        if (name != other.name) return false
        if (customParameters != other.customParameters) return false
        if (scale != other.scale) return false
        if (repeatCount != other.repeatCount) return false
        if (repeatGap != other.repeatGap) return false
        if (duration != other.duration) return false
        if (wallHeight != other.wallHeight) return false
        if (wallStartHeight != other.wallStartHeight) return false
        if (startRow != other.startRow) return false
        if (width != other.width) return false
        if (startTime != other.startTime) return false
        if(innerParameter != other.innerParameter) return false

        return true
    }

    override fun toString(): String {
        var text = name
        if(customParameters.isNotEmpty())
            text += """ -- ${customParameters.joinToString(" ")} -- """
        if(innerParameter!=null)
            text += """ .. $innerParameter .. """
        text+="$scale $repeatCount $repeatGap $startRow $duration $width $wallHeight $wallStartHeight $startTime"
        return text
    }


}

/**handles the counting*/
private class Counter(private val stringArray: List<String>){
    private var counter:Int = 0
    fun countUp(){
        counter++
        if (counter > 500)
            throw Exception("Encountered an infinity loop when trying to get Parameters. Did you forgot the second -- ?")
    }
    fun getElement() :String {
        return stringArray.getOrElse(counter) { "" }
    }

    fun getOrNull(): Double?{
        val i =  this.stringArray.getOrNull(counter)?.toDoubleOrNull()
        countUp()
        return i
    }
    fun getOr1():Double{
        return getOrNull()?:1.0
    }
    fun getOr0(): Double{
        return getOrNull()?:0.0
    }
}
