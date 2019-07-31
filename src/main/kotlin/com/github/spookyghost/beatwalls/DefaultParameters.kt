package com.github.spookyghost.beatwalls

class DefaultParameters(string: String)
{
    val name:String
    var customParameters = arrayListOf<String>()
    var scale:Double
    var repeat:Double
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

        /**custom Parameters */
        if(getElement() == "--"){
            while (getElement() != "--"){
                customParameters.add(stringArray[counter])
            }
        }

        /**default Parameters */
        scale = getOr1()
        repeat = getOr0()
        duration = getOr0()
        wallHeight = getOr0()
        wallStartHeight = getOr0()
        startRow = getOr0()
        width = getOr0()
        startTime = getOr0()
    }



    /**handles the counting*/
    companion object Counter{
        var counter:Int = 0
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
        countUp()
        return stringArray.getOrElse(counter) { "" }
    }
    private fun getOrNull(): Double?{
        countUp()
        return stringArray.getOrNull(counter)?.toDoubleOrNull()
    }
    private fun getOr1():Double{
        return getOrNull()?:1.0
    }
    private  fun getOr0(): Double{
        return getOrNull()?:0.0
    }
}




