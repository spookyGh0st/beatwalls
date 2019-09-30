package structures

import kotlin.reflect.KProperty

sealed class WallStructure(){

    /**saves the name of the class directly to the name property */
    open val name = this::class.simpleName?: String()
    val wallList = arrayListOf<Wall>()
    abstract fun getWalls()

    private fun remove(wall: Wall) {
        wallList.remove(wall)
    }
    private fun remove(wall: Collection<Wall>) {
        wallList.removeAll(wall)
    }
    private fun add(wall: Wall) {
        wallList.add(wall)
    }
    private fun add(wall: Collection<Wall>) {
        wallList.addAll(wall)
    }

    /** Options are used to set the Parameters */
    val options = arrayListOf<String>()

    /** a Flag, can be true of false */
    inner class Boolean(private val default:kotlin.Boolean= false) {
        operator fun getValue(thisRef: WallStructure, property: KProperty<*>): kotlin.Boolean {
            val ref = getOptionList(property.name)
            return if (options.any{ ref.contains(it)})
                !default
            else
                default
        }
    }

    /** Options hold value of different type */
    inner class Int(private val default: kotlin.Int?= null){
        operator fun getValue(thisRef: WallStructure, property: KProperty<*>): kotlin.Int?{
            val ref = getOptionList(property.name)
            if(options.none{ ref.contains(it)})
                return default
            else
                for (r in ref){
                    val index = options.indexOf(r)
                    val result = options.getOrNull(index+1)?.toIntOrNull()
                    if(result !=null)
                        return result
                }
            return null
        }
    }
    /** Options hold value of different type */
    inner class Double(private val default: kotlin.Double?= null){
        operator fun getValue(thisRef: WallStructure, property: KProperty<*>): kotlin.Double?{
            val ref = getOptionList(property.name)
            if(options.none{ ref.contains(it)})
                return default
            else
                for (r in ref){
                    val index = options.indexOf(r)
                    val result = options.getOrNull(index+1)?.toDoubleOrNull()
                    if(result !=null)
                        return result
                }
            return default
        }
    }

    /** gets the String list of --sfa -s for the sfa */
    fun getOptionList(string: String): ArrayList<String>{
        val ref = arrayListOf<String>()
        ref.add(string.toLowerCase().prependIndent("--"))
        ref.add(string
            .toLowerCase()
            .prependIndent("-")
            .removeRange(2,string.lastIndex+2)
        )
        return ref
    }
}



class TestStructure:WallStructure(){
    val mirror by Boolean(true)
    val amount by Int(12)
    override fun getWalls() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
fun main(){
    val a = TestStructure()
    a.options.addAll(listOf("bla","-m","--amount","123"))
    println(a.mirror)
    println(a.amount)

}

