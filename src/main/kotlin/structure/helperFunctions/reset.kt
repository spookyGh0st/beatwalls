@file:Suppress("DuplicatedCode")

package structure.helperFunctions

import structure.WallStructure

fun WallStructure.reset(){

    mirror= WallStructure.mirror
    mirrorX= WallStructure.mirrorX
    mirrorY= WallStructure.mirrorY

    /**
     * times the SpookyWall by adding the njsOffset, default: true
     */
    time= WallStructure.time

    /**
     * change The StartTime of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    // x= WallStructure.changeStartTime

    /**
     * change The Duration of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    // changeDuration= WallStructure.changeDuration

    /**
     * change The Height of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    // changeHeight= WallStructure.changeHeight

    /**
     * change The StartHeight of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    //y= WallStructure.changeStartHeight

    /**
     * change The StartRow of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    //x= WallStructure.changeStartRow

    /**
     * change the Width of all Walls in the structure to the given Value. Random possible with random(min,max). Default: null
     */
    // changeWidth= WallStructure.changeWidth

    /**
     * multiplies the StartTime of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //scaleStartTime= WallStructure.scaleStartTime

    /**
     * multiplies the Duration of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //scaleDuration= WallStructure.scaleDuration

    /**
     * multiplies the Height of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //scaleHeight= WallStructure.scaleHeight

    /**
     * multiplies the StartHeight of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //scaleStartHeight= WallStructure.scaleStartHeight

    /**
     * multiplies the StartRow of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //scaleStartRow= WallStructure.scaleStartRow

    /**
     * multiplies the Width of all Walls in the structure by the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //scaleWidth= WallStructure.scaleWidth

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //addStartTime= WallStructure.addStartTime

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //addDuration= WallStructure.addDuration

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //addHeight= WallStructure.addHeight

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //addStartHeight= WallStructure.addStartHeight

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //addStartRow= WallStructure.addStartRow

    /**
     * adds the given Value. Random possible with random(min,max). Default: null (does nothing)
     */
    //addWidth= WallStructure.addWidth

    /**
     * increases or decreases the duration of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    fitStartTime= WallStructure.fitStartTime

    /**
     * increases or decreases the StartTime of all walls until they have the the specific duration. Random possible with random(min,max). default: null (does nothing)
     */
    fitDuration= WallStructure.fitDuration

    /**
     * increases or decreases the StartHeight of all walls until they have the the specific Height. Random possible with random(min,max). default: null (does nothing)
     */
    fitHeight= WallStructure.fitHeight

    /**
     * increases or decreases the height of all walls until they have the the specific startHeight. Random possible with random(min,max). default: null (does nothing)
     */
    fitStartHeight= WallStructure.fitStartHeight

    /**
     * increases or decreases the width of all walls until they have the the specific startTime. Random possible with random(min,max). default: null (does nothing)
     */
    fitStartRow= WallStructure.fitStartRow

    /**
     * increases or decreases the StartRow of all walls until they have the the specific Width. Random possible with random(min,max). default: null (does nothing)
     */
    fitWidth= WallStructure.fitWidth

    /**
     * scales the Duration and startTime, (duration only for positive duration).
     * This is useful for making a structure, that is one beat long longer or shorter
     */
    scale= WallStructure.scale

    /**
     * reverses the WallStructure on the Starttime/duration
     */
    reverse= WallStructure.reverse

    /**
     * basically mirrors the Wallstructure in itself on the x-Achsis if set to true
     */
    reverseX= WallStructure.reverseX

    /**
     * basically mirrors the Wallstructure in itself on the x-Achsis if set to true
     */
    reverseY= WallStructure.reverseY

    /**
     * speeds up the wallstructure over time. the duration of the whole structure. Remains. \n
     *
     * value 0-1 start is slower, speed up over time, \n
     *
     * 1-100 start is faster, slow down over time \n
     *
     * The closer the value is to 1, the more stale it gets.
     */
    speeder= WallStructure.speeder

    /**
     * how often you want to repeat the Structure.
     */
    repeat= WallStructure.repeat

    /**
     * how often you want to repeat the walls of the Structure. This copy pastes the walls, while (repeat) generates a new one.
     */
    repeatWalls= WallStructure.repeatWalls

    /**
     * The Gap between each Repeat. Default: 0
     */
    repeatAddZ= WallStructure.repeatAddZ

    /**
     * shifts each repeat in x. Default: 0
     */
    repeatAddX= WallStructure.repeatAddX

    /**
     * shifts each repeated Structure in y. Default: 0
     */
    repeatAddY= WallStructure.repeatAddY

    /**
     * adds this value to the Duration to each repeated Structure
     */
    repeatAddDuration= WallStructure.repeatAddDuration

    /**
     * adds this value to the Height to each repeated Structure
     */
    repeatAddHeight= WallStructure.repeatAddHeight

    /**
     * adds this value to the Width to each repeated Structure
     */
    repeatAddWidth= WallStructure.repeatAddWidth

    /**
     * adds this value to the StartRow to each repeated Structure
     */
    repeatAddStartRow= WallStructure.repeatAddStartRow

    /**
     * adds this value to the StartHeight to each repeated Structure
     */
    repeatAddStartHeight= WallStructure.repeatAddStartHeight
    /**
     * adds this value to the StartTime to each repeated Structure
     */
    repeatAddStartTime= WallStructure.repeatAddStartTime

    /**
     * The Color of the Wallstructure. Click me to see examples
     *
     * color: red
     * # turns the entire Wallstructure red.
     * All available colors are here: https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
     *
     * color: 255,0,0
     * # also turns the entire WallStructure red, but uses rgb values.
     *
     * color: rainbow
     * # creates a rainbow :)
     *
     * color: rainbow(1.5)
     * # also creates a rainbow :), but this one changes 1.5 times as fast as the default one
     *
     * color: flash(green)
     * # Flashes between green and the default color
     *
     * color: flash(128,128,128,Green)
     * # Flashes between Dark Gray (128,128,128) and Green.
     *
     * color: flash(red, green, blue, yellow)
     * # changes color in the order red, green, blue, yellow.
     *
     * color: gradient(Red,Cyan)
     * # gradient from Red to Furry (Cyan))
     *
     */
    color = WallStructure.color

    /**
     * some Wallstructures use Random walls. This is the seed for them
     */
    seed= WallStructure.seed

    /**
     * used for some internal stuff, dont touch
     */
    track= WallStructure.track

}