package structures

import parameter.CommandParser

@Suppress("unused")
sealed class WallStructure(): CommandParser() {

    /**saves the name of the class directly to the name property */
    open val name = this::class.simpleName ?: String()

    abstract fun getWalls()

    fun remove(wall: Wall) {
        wallList.remove(wall)
    }

    fun remove(wall: Collection<Wall>) {
        wallList.removeAll(wall)
    }

    fun add(wall: Wall) {
        wallList.add(wall)
    }

    fun add(wall: Collection<Wall>) {
        wallList.addAll(wall)
    }


    open val wallList = arrayListOf<Wall>()


}


class TestStructure:WallStructure(){
    val mirror by Boolean(true)
    val amount by Int(12)
    val text by String("abc")
    override fun getWalls() {
        add(Wall(0.0,1.0,0.0,0.0,0.0,0.0))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
class CustomWallStructure(
): WallStructure(){

    override fun getWalls() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
fun main(){
    val a = TestStructure()
    a.options.addAll(listOf("bla","-m","--amount","123","hallowelt","--tet","'123'"))
    println(a.mirror)
    println(a.amount)
    println(a.text)
    val b = a.wallList
    b.add(Wall(0.0,1.0,0.0,0.0,0.0,0.0))
    println(a.wallList.size)
}

