package structures

class Parameters(string: String)
{
    val name:String
    var customParameters = arrayListOf<String>()
    var scale:Double
    var repeatCount: Int
    var repeatGap: Double
    var duration: Double
    var wallHeight:Double
    var wallStartHeight:Double
    var startRow:Double
    var width: Double
    var startTime: Double

    private val stringArray:List<String> = string.split(" ")

    init {
        counterReset()
        name = stringArray.first()
        if(name == "")
            throw (Exception("Failed to read Name, pls use /bw name"))

        countUp()

        /**custom Parameters */
        if(getElement() == "--"){
            countUp()
            while (getElement() != "--"){
                customParameters.add(stringArray[counter])
                countUp()
            }
            countUp()
        }

        /**default Parameters */
        scale = getOr1()
        repeatCount = getOr0().toInt()
        repeatGap = getOr1()
        duration = getOr0()
        wallHeight = getOr0()
        wallStartHeight = getOr0()
        startRow = getOr0()
        width = getOr0()
        startTime = getOr0()
    }



    /**handles the counting*/
    companion object Counter{
        var counter:Int = -1
        fun counterReset(){
            counter = 0
        }
        fun countUp(){
            counter++
            if (counter > 500)
                throw Exception("Encountered an infinity loop when trying to get Parameters. Did you forgot the second -- ?")
        }
    }

    private fun getElement() :String {
        return stringArray.getOrElse(counter) { "" }
    }

    private fun getOrNull(): Double?{
        val i =  stringArray.getOrNull(counter)?.toDoubleOrNull()
        countUp()
        return i
    }
    private fun getOr1():Double{
        return getOrNull()?:1.0
    }
    private  fun getOr0(): Double{
        return getOrNull()?:0.0
    }

    override fun hashCode(): Int =
        (scale + repeatGap + repeatCount + wallStartHeight +wallHeight +duration +startRow + width).toInt()
}




